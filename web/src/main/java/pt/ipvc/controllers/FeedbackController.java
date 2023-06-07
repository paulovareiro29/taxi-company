package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.bll.FeedbackBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.dal.User;

@Controller
public class FeedbackController {

    @GetMapping(value="/feedbacks")
    public String Index(Model model) {
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";

        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);

        if(auth.getRole().getName().equalsIgnoreCase("client")) {
            model.addAttribute("feedbacks", FeedbackBLL.getByClient(auth));
        }else if(auth.getRole().getName().equalsIgnoreCase("driver")) {
            model.addAttribute("feedbacks", FeedbackBLL.getByDriver(auth));
        }

        return "feedbacks";
    }
}
