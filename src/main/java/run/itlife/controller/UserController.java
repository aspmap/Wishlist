package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.enums.FileExtensions;
import run.itlife.enums.Sex;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.Profile;

import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;
import static run.itlife.utils.Properties.ErrorMessages.ERROR;
import static run.itlife.utils.Properties.Files.IMAGE_HEIGHT;
import static run.itlife.utils.Properties.Files.IMAGE_WIDTH;
import static run.itlife.utils.Properties.Paths.*;
import static run.itlife.utils.SecurityUtils.hasRole;

//UserController, отвечающий за логин юзеров и т.д.
//Создаем в папке view страницу register.html. Далее необходимо сделать, чтобы мы пересылали данные в контроллер.
//У UserController будет страница по которой будет идти регистрация. Для этого нужно сделать форму и она уже будет
//идти на контроллер для регистрации
@Controller
public class UserController {
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    private final ServletContext context;
    @Autowired
    CommonsParams commonsParams;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, ServletContext context, SubscriptionsService subscriptionsService) {
        this.userService = userService;
        this.context = context;
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        commonsParams.setCommonConstParams(modelMap);
        return "register";
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        commonsParams.setCommonConstParams(model);
        return "login";
    }

    @GetMapping("/error")
    public String loginError(ModelMap modelMap) {
        commonsParams.setCommonConstParams(modelMap);
        return "messages-templates/loginError";
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String editProfile(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
        }
        commonsParams.setCommonParams(modelMap, user);
        modelMap.put("sex_male", Sex.MALE);
        modelMap.put("sex_female", Sex.FEMALE);
        return "profile/profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String editProfile(UserDto userDto, @RequestParam("file") String file, ModelMap modelMap) {
        if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                String filename = generateFileName() + ".jpg";
                // получение имени фото и сохранение имени фото и данных поста в БД
                userService.checkAuthority(userDto.getUserId());
                userDto.setPhoto(filename);
                userService.update(userDto);

                // сохранение самого файла в папку юзера
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();
                File dir = new File(context.getRealPath(PATH_IMAGE_USERS + username + "/profile/"));
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir + "/" + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));

                String base64Image = file.split(",")[1];
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                stream.write(imageBytes);

                //изменение размера до 500х500
                BufferedImage originalImage = ImageIO.read(uploadedFile);
                BufferedImage resizeImage = null;
                File newFileJPG = null;
                resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
                newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);

                //записываем файл
                ImageIO.write(resizeImage, FileExtensions.PNG.getExtension(), newFileJPG);
                stream.flush();
                stream.close();
                return "redirect:/";
            } catch (Exception e) {
                log.error(ERROR + e);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates/error";
            }
        } else {
            userService.checkAuthority(userDto.getUserId());
            userService.update(userDto);
            return "redirect:/";
        }
    }

    @GetMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteProfileGet(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
        }
        commonsParams.setCommonParams(modelMap, user);
        return "profile/profile-delete";
    }

    @PostMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteProfilePost(ModelMap modelMap, @PathVariable String user) {
        userService.deleteProfile(user);
        //удаляем папки и файлы пользователя
        File dir_img = new File(context.getRealPath(PATH_IMAGE_USERS + user));
        File dir_video = new File(context.getRealPath(PATH_VIDEO_USERS + user));
        Profile.recursiveFilesDelete(dir_img);
        Profile.recursiveFilesDelete(dir_video);
        return "redirect:/";
    }

    @GetMapping("/subscriptions")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribes(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribes(username));
        return "subs/subscriptions";
    }

    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribers(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribers(username));
        return "subs/subscribers";
    }

    @GetMapping("/subscriptions_their/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribesTheir(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribes(user));
        return "subs/subscriptions-their";
    }

    @GetMapping("/subscribers_their/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribersTheir(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribers(user));
        return "subs/subscribers-their";
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String search(ModelMap modelMap, @RequestParam(required = false) String search) {
        search = search.toLowerCase();
        commonsParams.setCommonParams(modelMap);
        modelMap.put("countSearchUsers", userService.countSearchUsers(search));
        modelMap.put("countSearchGoogleUsers", userService.countSearchGoogleUsers(search));
        modelMap.put("tagUserName", search);
        if (search != null && !search.equals("")) {
            modelMap.put("findUsers", userService.findUsers(search));
            modelMap.put("findGoogleUsers", userService.findGoogleUsers(search));
            return "search-results";
        } else if (hasRole("ADMIN")) {
            modelMap.put("findUsers", userService.findAll());
            return "search-results";
        } else if (search.equals("")) {
            return "redirect:/error_search";
        }
        return "search-results";
    }

    @GetMapping("/error_search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String errorSearch(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "messages-templates/error-search";
    }

    @GetMapping("/confidentiality")
    public String confidentiality(ModelMap modelMap) {
        return "info/confidentiality";
    }

    @PostMapping("/register")
    public String register(ModelMap modelMap, User user) {
        commonsParams.setCommonConstParams(modelMap);
        try {
            userService.create(user);
            return "messages-templates/registration-success";
        } catch (EntityExistsException e) {
            log.error(ERROR + e);
            return "messages-templates/exist";
        }
    }

    private void setCommonParamsSynchronized(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
    }
}