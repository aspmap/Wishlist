package run.itlife.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import run.itlife.service.*;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;

@Component
public class CommonsParams {
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    private final ServletContext context;
    private final VersionProject versionProject;

    @Autowired
    public CommonsParams(UserService userService, SubscriptionsService subscriptionsService, ServletContext context, VersionProject versionProject) {
        this.userService = userService;
        this.subscriptionsService = subscriptionsService;
        this.context = context;
        this.versionProject = versionProject;
    }

    public void setCommonParams(long id, ModelMap modelMap) {
        this.setCommonParams(modelMap);
    }

    public void setCommonParams(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
        modelMap.put("contextPath", context.getContextPath());
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        this.setCommonConstParams(modelMap);
    }

    public void setCommonSubParams(ModelMap modelMap, String username) {
        modelMap.put("userinfo_sub", userService.findByUsername(username));
        modelMap.put("user_sub", username);
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
        this.setCommonConstParams(modelMap);
    }

    public void setCommonParams(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
        modelMap.put("userPhoto", userService.findByUsername(username).getPhoto());
        modelMap.put("contextPath", context.getContextPath());
        this.setCommonConstParams(modelMap);
    }

    public void setCommonConstParams(ModelMap modelMap) {
        modelMap.put("majorVersion", versionProject.getMajorVersion());
        modelMap.put("minorVersion", versionProject.getMinorVersion());
        modelMap.put("microVersion", versionProject.getMicroVersion());
        modelMap.put("stageVersion", versionProject.getStageVersion());
        modelMap.put("currentYear", LocalDateTime.now().getYear());
    }
}
