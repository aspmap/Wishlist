package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.VersionProject;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;

@Controller
public class SubscriptionsController {
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    private final ServletContext context;
    private final VersionProject versionProject;
    @Autowired
    CommonsParams commonsParams;

    @Autowired
    public SubscriptionsController(UserService userService, SubscriptionsService subscriptionsService, ServletContext context, VersionProject versionProject) {
        this.userService = userService;
        this.subscriptionsService = subscriptionsService;
        this.context = context;
        this.versionProject = versionProject;
    }

    @GetMapping("/{user}")
    public String findPostsSub(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            commonsParams.setCommonSubParams(modelMap, user);
            commonsParams.setCommonParams(modelMap);
            modelMap.put("isSub", subscriptionsService.isSubscribe(username, user));
        } else {
            modelMap.put("userinfo_sub", userService.findByUsername(user));
            modelMap.put("user_sub", user);
            modelMap.put("countSubscribe", subscriptionsService.countSubscribe(user));
            modelMap.put("countSubscribers", subscriptionsService.countSubscribers(user));
            modelMap.put("users", userService.findAll());
            modelMap.put("userslist", userService.findAll());
            modelMap.put("userOnlyList", userService.findUsersOnly());
            modelMap.put("contextPath", context.getContextPath());
            modelMap.put("majorVersion", versionProject.getMajorVersion());
            modelMap.put("minorVersion", versionProject.getMinorVersion());
            modelMap.put("microVersion", versionProject.getMicroVersion());
            modelMap.put("stageVersion", versionProject.getStageVersion());
            modelMap.put("currentYear", LocalDateTime.now().getYear());
        }
        return "posts/view/subscriber-page";
    }

    /**
     * Подписка в рекомендациях
     */
    @GetMapping("wishlist_subscriber/subscription_from_recommendations/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createSubscribeFromRecommendations(ModelMap modelMap, @PathVariable String user){
        subscriptionsService.createSub(user);
        return "redirect:/";
    }

    @GetMapping("wishlist_subscriber/subscription/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createSubscribe(@PathVariable String user){
        subscriptionsService.createSub(user);
        return "redirect:/wishlist_subscriber/{user}";
    }

    @GetMapping("wishlist_subscriber/unsubscription/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String unsubscribe(@PathVariable String user){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        long subUserId = userService.findByUsername(user).getUserId().longValue();
        subscriptionsService.deleteSubscribeLong(currentUserId, subUserId);
        return "redirect:/wishlist_subscriber/{user}";
    }
}
