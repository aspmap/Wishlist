package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.WishlistDto;
import run.itlife.dto.WishlistPrivateDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;
import run.itlife.repository.UserRepository;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import run.itlife.service.WishlistPrivateService;
import run.itlife.service.WishlistService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static run.itlife.utils.Properties.ErrorMessages.*;
import static run.itlife.utils.Properties.Paths.*;

@Controller
public class WishlistController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SubscriptionsService subscriptionsService;
    private final WishlistService wishlistService;
    private final WishlistPrivateService wishlistPrivateService;
    private final ServletContext context;
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);

    @Autowired
    public WishlistController(UserService userService, UserRepository userRepository, SubscriptionsService subscriptionsService, WishlistService wishlistService, WishlistPrivateService wishlistPrivateService, ServletContext context) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.subscriptionsService = subscriptionsService;
        this.wishlistService = wishlistService;
        this.wishlistPrivateService = wishlistPrivateService;
        this.context = context;
    }

    @GetMapping("/main")
    public String findLoginInfo(ModelMap modelMap, OAuth2AuthenticationToken authentication) {
        String username = authentication.getPrincipal().getAttribute("sub");
        userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(authentication.getPrincipal().getAttribute("sub"));
            newUser.setFirstname(authentication.getPrincipal().getAttribute("given_name"));
            newUser.setPassword(authentication.getPrincipal().getAttribute("at_hash"));
            newUser.setSurname(authentication.getPrincipal().getAttribute("family_name"));
            newUser.setEmail(authentication.getPrincipal().getAttribute("email"));
            newUser.setPhoto(authentication.getPrincipal().getAttribute("picture"));
            userService.createGoogleUser(newUser);
            return newUser;
        });
        Optional<User> user = userRepository.findByUsername(username);
        if (!StringUtils.isEmpty(user)) {
            if (user.get().getLastVisit() != null) {
                user.get().setPreviousVisit(user.get().getLastVisit());
            }
            user.get().setLastVisit(LocalDateTime.now());
            userRepository.save(user.get());
        }

        return "redirect:/";
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findWishlist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        modelMap.put("whoSeesSecretWishes", wishlistService.whoSeesSecretWishes(user.getUserId()));
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
        return "wishlist/wishlist";
    }

    @GetMapping("/wishlist_subscriber/{user_subscriber}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubWishlist(ModelMap modelMap, @PathVariable String user_subscriber) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("isSub", subscriptionsService.isSubscribe(modelMap.get("user").toString(), user_subscriber));
        modelMap.put("user_sub", user_subscriber);
        modelMap.put("googleUser_sub", userService.findByUsername(user_subscriber).getEmail());
        User user = userService.findByUsername(user_subscriber);
        modelMap.put("userSubInfo", user);
        modelMap.put("wishlistAll", wishlistService.findAllByUserAndSecretIsFalse(user));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userCurrent = userService.findByUsername(username);
        modelMap.put("wishlistPrivate", wishlistService.findPrivateWishesByUser(userCurrent.getUserId(), user.getUserId()));
        return "wishlist/wishlist-sub";
    }

    @GetMapping("/wishlist_my_booking")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findMyBookingWishlist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("bookingWishlists", wishlistService.findAllByBookingUser(user.getUserId()));
        return "wishlist/view-my-booking";
    }

    @GetMapping("/wishlist_of_subscriber/booking/{user_subscriber}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBookingWishlist(ModelMap modelMap, @PathVariable String user_subscriber, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.createBookingWishlist(user_subscriber, id);
        return "redirect:" + SEPARATOR + "wishlist_subscriber" + SEPARATOR + user_subscriber;
    }

    @GetMapping("/wishlist_of_subscriber/unbooking/{user_subscriber}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createUnBookingWishlist(ModelMap modelMap, @PathVariable String user_subscriber, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.createUnBookingWishlist(user_subscriber, id);
        return "redirect:" + SEPARATOR + "wishlist_subscriber" + SEPARATOR + user_subscriber;
    }

    @GetMapping("/booking_wishlist/booking/{user_subscriber}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createMyBookingWishlist(ModelMap modelMap, @PathVariable String user_subscriber, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.createBookingWishlist(user_subscriber, id);
        return "redirect:" + SEPARATOR + "wishlist_my_booking";
    }

    @GetMapping("/booking_wishlist/unbooking/{user_subscriber}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createMyUnBookingWishlist(ModelMap modelMap, @PathVariable String user_subscriber, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.createUnBookingWishlist(user_subscriber, id);
        return "redirect:" + SEPARATOR + "wishlist_my_booking";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createWishlistGet(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "wishlist/add-to-wishlist";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createWishlistPost(WishlistDto wishlistDto, @RequestParam("photo") String file, ModelMap modelMap, @RequestParam(name = "isSecret", defaultValue = "false", required = false) Boolean isSecret) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long wishlistId;
        SaveFile sf = new SaveFile();
        if (!file.isEmpty()) {
            try {
                String filename = sf.saveFileForWishlist(username, context, file);
                if (filename == null) {
                    commonsParams.setCommonParams(modelMap);
                    return "messages-templates" + SEPARATOR + "errorFileSizeWishlist";
                }
                wishlistDto.setPhoto(filename);
                wishlistDto.setSecret(isSecret);
                wishlistId = wishlistService.createElementOfWishlist(wishlistDto);
                return "redirect:" + SEPARATOR;
            } catch (Exception e) {
                log.error(ERROR + NOT_PUBLISH_WISHLIST);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates" + SEPARATOR + "errorFileSizeWishlist";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_WISHLIST);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates" + SEPARATOR + "errorFileSizeWishlist";
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWish(id);
    }

    @GetMapping("/done")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findWishlistIsDone(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistIsdone", wishlistService.findAllByUserAndIsDoneTrue(user));
        return "wishlist/wishlist-done";
    }

    @GetMapping("/complete/{wishId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findWishlistIsCompleted(ModelMap modelMap, @PathVariable Long wishId) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.checkCompleteWish(wishId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        modelMap.put("whoSeesSecretWishes", wishlistService.whoSeesSecretWishes(user.getUserId()));
        return "redirect:" + SEPARATOR;
    }

    @GetMapping("/permissions/add/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createPermissionsToWishlistGet(ModelMap modelMap, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        return "wishlist/add-permission";
    }

    @PostMapping("/permissions/add")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createPermissionsToWishlistGetPost(ModelMap modelMap, @RequestParam("userWishlistPrivate") String username, @RequestParam("wishlistIdWishlistPrivate") Long id) {
        WishlistPrivateDto  wishlistPrivateDto = new WishlistPrivateDto();
        Integer countAlreadyPermission;
        User user = new User();
        commonsParams.setCommonParams(modelMap);
        String usernameCurrent = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            user = userService.findByUsername(username);
            countAlreadyPermission = wishlistPrivateService.findAlreadyPermissions(id, user.getUserId());
        } catch (UsernameNotFoundException e) {
            log.error(ERROR + NOT_PUBLISH_WISHLIST);
            return "messages-templates" + SEPARATOR + "existPermission";
        }

        if (countAlreadyPermission == 0 && !username.equals(usernameCurrent)) {
            Wishlist wishlist = new Wishlist();
            wishlist.setWishlistId(id);
            wishlistPrivateDto.setWishlistIdWishlistPrivate(wishlist);
            wishlistPrivateDto.setUserWishlistPrivate(user);
            wishlistService.addPermission(wishlistPrivateDto);
            return "redirect:" + SEPARATOR;
        }
        return "messages-templates" + SEPARATOR + "existPermission";
    }

    @GetMapping("/permissions/delete/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deletePermissionsFromWishlist(ModelMap modelMap, @PathVariable Long id) {
        wishlistPrivateService.deletePermissions(id);
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        modelMap.put("whoSeesSecretWishes", wishlistService.whoSeesSecretWishes(user.getUserId()));
        return "wishlist/wishlist";
    }

    @PostMapping("/results/{username}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findUsersForPermissions(ModelMap modelMap, @PathVariable String username) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("findUsers", userService.findUsersForPermission(username));
        return "wishlist/results";
    }
}
