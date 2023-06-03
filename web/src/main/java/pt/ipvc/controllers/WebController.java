package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.bll.SessionBLL;

@Controller
public class WebController {

    @GetMapping(value="/")
    public String index(Model model) {
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";
        model.addAttribute("auth", SessionBLL.getAuthenticatedUser());

        return "index";
    }
}
