package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;

    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService,
                          UserService userService, EncryptionService encryptionService) {

        this.fileService = fileService;

        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomeView(Authentication auth, Model model) {
        User cur_User = userService.getCurrentUser(auth);
        Integer userId = cur_User.getUserId();
        model.addAttribute("files", fileService.getAllFiles(userId));


        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

}
