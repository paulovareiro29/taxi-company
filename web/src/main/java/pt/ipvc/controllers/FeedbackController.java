package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipvc.bll.FeedbackBLL;
import pt.ipvc.dal.User;

import javax.servlet.http.HttpSession;

@Controller
public class FeedbackController {

    @GetMapping(value="/feedbacks")
    public String Index(HttpSession session, Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        User auth = (User) session.getAttribute("auth");
        model.addAttribute("auth", auth);

        if(auth.getRole().getName().equalsIgnoreCase("client")) {
            model.addAttribute("feedbacks", FeedbackBLL.getByClient(auth));
        }else if(auth.getRole().getName().equalsIgnoreCase("driver")) {
            model.addAttribute("feedbacks", FeedbackBLL.getByDriver(auth));
        }

        return "feedbacks";
    }
}
