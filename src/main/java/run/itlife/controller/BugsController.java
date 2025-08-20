package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.dto.BugsDto;
import run.itlife.service.BugsService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;

@Controller
@RequestMapping("/bugs")
public class BugsController {
    private final BugsService bugsService;
    private final UserService userService;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(BugsController.class);

    @Autowired
    public BugsController(BugsService bugsService, UserService userService) {
        this.bugsService = bugsService;
        this.userService = userService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBug(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "bugs/create-bug";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBug(BugsDto bugsDto, ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        bugsService.createBugReport(bugsDto);
        return "messages-templates/message-send";
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewBugs(ModelMap modelMap) {
        modelMap.put("bugs", bugsService.findAllBugs());
        modelMap.put("userslist", userService.findAll());
        commonsParams.setCommonParams(modelMap);
        return "bugs/bugs";
    }
}