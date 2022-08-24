package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private final NoteService noteService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService,
                          UserService userService, NoteService noteService, EncryptionService encryptionService, CredentialService credentialService) {

        this.fileService = fileService;
        this.noteService=noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String HomeView(Authentication auth, @ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model) {
        User cur_User = userService.getCurrentUser(auth);
        Integer userId = cur_User.getUserId();
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("notes",noteService.getAllNote(userId));
        model.addAttribute("credentials",credentialService.getAllEncrypted(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

}
