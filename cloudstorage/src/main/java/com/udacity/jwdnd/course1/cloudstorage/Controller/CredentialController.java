package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }
    @PostMapping("/create_credential")
    public String create_Edit(Authentication auth, @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model){

        Integer userId = userService.getCurrentUser(auth).getUserId();
        if (credentialForm.getCredentialId() == null) {
            try{
                credentialService.createCredential(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(),null, credentialForm.getPassword(), userId));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Successfully created the credential");
            }catch(Exception e){
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "An error occurred when creating the credential, please try again");
            }
        }else {
            try {
                String key = credentialService.getCredential(credentialForm.getCredentialId()).getKey();
                credentialService.editCredential(new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUsername(), key, credentialForm.getPassword(), userId));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Successfully updated the credential");
            }catch(Exception e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Something went wrong when updating the credential, please try again");
            }
        }

        return "result";
    }
    @GetMapping("/delete_credential/{credential_Id}")
    public String delete_credential(Authentication auth, @PathVariable Integer credential_Id, Model model) {
        try {
            credentialService.deleteCredential(credential_Id);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted the note");
        }catch(Exception e){
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "Something went wrong when deleting the note, please try again");
        }

        return "result";
    }
}
