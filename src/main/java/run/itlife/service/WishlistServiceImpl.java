package run.itlife.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.WishlistDto;
import run.itlife.dto.WishlistPrivateDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;
import run.itlife.entity.WishlistPrivate;
import run.itlife.repository.UserRepository;
import run.itlife.repository.WishlistPrivateRepository;
import run.itlife.repository.WishlistRepository;
import org.springframework.security.access.AccessDeniedException;
import static run.itlife.utils.SecurityUtils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final WishlistPrivateRepository wishlistPrivateRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository, WishlistPrivateRepository wishlistPrivateRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.wishlistPrivateRepository = wishlistPrivateRepository;
    }

    @Override
    public ArrayList<Wishlist> findAllByUserOrderByCreatedAt(Long userId) {
        return wishlistRepository.findAllByUserOrderByCreatedAt(userId);
    }

    @Override
    public ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId) {
        return wishlistRepository.findAllByBookingUser(bookingUserId);
    }

    @Override
    public ArrayList<Wishlist> findAllByUserAndIsDoneTrue(User user) {
        return wishlistRepository.findAllByUserAndIsDoneTrue(user);
    }

    @Override
    public Long createElementOfWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setNameWish(wishlistDto.getNameWish());
        wishlist.setDescription(wishlistDto.getDescription());
        wishlist.setPrice(wishlistDto.getPrice());
        wishlist.setCreatedAt(LocalDateTime.now());
        wishlist.setLink(wishlistDto.getLink());
        wishlist.setPhoto(wishlistDto.getPhoto());
        wishlist.setSecret(wishlistDto.getSecret());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        wishlist.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        wishlistRepository.save(wishlist);
        return wishlist.getWishlistId();
    }

    @Override
    public ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user) {
        return wishlistRepository.findAllByUserAndSecretIsFalse(user);
    }

    @Override
    public void createBookingWishlist(String user_sub, Long id) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow();
        wishlist.setBooking(true);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        wishlist.setBookingUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)).getUserId());
        wishlistRepository.save(wishlist);
    }

    @Override
    public void createUnBookingWishlist(String user_sub, Long id) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow();
        wishlist.setBooking(false);
        wishlist.setBookingUser(null);
        wishlistRepository.save(wishlist);
    }

    @Override
    public Long countAllByUserAndIsBookingTrue(Long userId) {
        return wishlistRepository.countAllByUserAndIsBookingTrue(userId);
    }

    @Override
    public Long countAllByUser(Long userId) {
        return wishlistRepository.countAllByUser(userId);
    }

    @Override
    public void deleteWish(Long id) {
        String username = wishlistRepository.findById(id)
                .orElseThrow()
                .getUser().getUsername();
        if (!hasAuthority(username) && !hasRole("ADMIN")) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        wishlistRepository.deleteById(id);
    }

    @Override
    public void checkCompleteWish(Long wishId) {
        Wishlist wishlist = wishlistRepository.findById(wishId).orElseThrow();
        wishlist.setBooking(false);
        wishlist.setDone(true);
        wishlistRepository.save(wishlist);
    }

    @Override
    public ArrayList<Wishlist> findPrivateWishesByUser(Long userIdPrivate, Long userIdSub) {
        return wishlistRepository.findPrivateWishesByUser(userIdPrivate, userIdSub);
    }

    @Override
    public Map<Long, ArrayList<WishlistPrivate>> whoSeesSecretWishes(Long userId) {
        Map<Long, ArrayList<WishlistPrivate>> listOfPermissions = new HashMap<>();
        ArrayList<Wishlist> whoSeesSecretWishes = wishlistRepository.whoSeesSecretWishes(userId);
        for (int i = 0; i < whoSeesSecretWishes.size(); i++) {
            ArrayList<WishlistPrivate> setToList = new ArrayList<>();
            setToList.addAll(whoSeesSecretWishes.get(i).getWishlistPrivate());
            listOfPermissions.put(whoSeesSecretWishes.get(i).getWishlistId(), setToList);
        }
        return listOfPermissions;
    }

    @Override
    public void addPermission(WishlistPrivateDto wishlistPrivateDto) {
        WishlistPrivate wishlistPrivate = new WishlistPrivate();
        wishlistPrivate.setWishlistIdWishlistPrivate(wishlistPrivateDto.getWishlistIdWishlistPrivate());
        wishlistPrivate.setUserWishlistPrivate(wishlistPrivateDto.getUserWishlistPrivate());
        wishlistPrivateRepository.save(wishlistPrivate);
    }
}