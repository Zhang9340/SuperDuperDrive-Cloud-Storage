package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete_note/{noteId}")
    public String delete_note(Authentication auth, @PathVariable Integer noteId, Model model){
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "The note has been successfully deleted!");
        }catch(Exception e) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "An error occurred when deleting the note, please try again");
        }
        return "result";
    }
    @PostMapping("/note_add_update")
    public String add_update(Authentication auth, @ModelAttribute("noteForm") NoteForm noteForm, Model model){
        String username = (String) auth.getPrincipal();
        User user = userService.getUser(username);
        String error_Msg = null;

        if (noteForm.getNoteId() != null) {
            noteService.editNote(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Note has been successfully updated");
        }else {
            try {
                noteService.createNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId()));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Note has been successfully created");
            }catch(Exception e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Something went wrong when creating the note, please try again");
            }
        }
       // model.addAttribute("notes", noteService.getAllNote(user.getUserId()));

        return "result";
    }

}
