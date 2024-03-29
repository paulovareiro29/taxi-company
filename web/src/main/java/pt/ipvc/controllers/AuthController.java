package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.exceptions.EmailAlreadyInUseException;
import pt.ipvc.models.LoginUserFormData;
import pt.ipvc.models.RegisterUserFormData;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    @GetMapping(value="/register")
    public String Register(HttpSession session, Model model) {
        if(session.getAttribute("auth") != null) return "redirect:/";

        model.addAttribute("user", new RegisterUserFormData());
        return "register";
    }

    @PostMapping(value="/register")
    public String RegisterSubmit(@Valid @ModelAttribute("user") RegisterUserFormData user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "register";
        }

        try {
            SessionBLL.register(user.getName(), user.getEmail(), "", user.getPassword(), RoleBLL.getClientRole());
            return "redirect:/login";
        }catch(EmailAlreadyInUseException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "register";
        }
    }

    @GetMapping(value="/login")
    public String Login(HttpSession session, Model model) {
        if(session.getAttribute("auth") != null) return "redirect:/";

        model.addAttribute("user", new LoginUserFormData());
        return "login";
    }

    @PostMapping(value="/login")
    public String LoginSubmit(@Valid @ModelAttribute("user") LoginUserFormData user, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {
            return "login";
        }

        if(!SessionBLL.login(user.getEmail(), user.getPassword())) {
            result.rejectValue("email", "error.user", "Invalid credentials");
            return "login";
        }

        session.setAttribute("auth", UserBLL.getByEmail(user.getEmail()));

        return "redirect:/";
    }

    @GetMapping(value="/logout")
    public String Logout(HttpSession session) {
        session.removeAttribute("auth");
        return "redirect:/";
    }
}
