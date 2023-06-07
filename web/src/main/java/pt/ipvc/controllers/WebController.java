package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.dal.Taxi;
import pt.ipvc.dal.User;
import pt.ipvc.models.PlateFormData;
import pt.ipvc.models.ScheduleTripFormData;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class WebController {

    @GetMapping(value="/")
    public String Index(Model model) {
        SessionBLL.login("driver@ipvc.pt","driver");
        model.addAttribute("auth", SessionBLL.getAuthenticatedUser());
        model.addAttribute("booking", new ScheduleTripFormData());
        model.addAttribute("plate", new PlateFormData());

        return "index";
    }

    @GetMapping(value="/schedule")
    public String ScheduleTrip() {
        return "redirect:/";
    }

    @PostMapping(value="/schedule")
    public String ScheduleTripSubmit(@Valid @ModelAttribute("booking") ScheduleTripFormData booking,
                                     BindingResult result,
                                     Model model) {
        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);

        if (result.hasErrors()) {
            return "index";
        }

        // Format booking.pickupDate string to Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(booking.pickupDate, formatter);
        Date pickupDate = java.util.Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());

        if(!pickupDate.after(new Date())) {
            result.rejectValue("pickupDate", "error.booking", "Pickup date must be on the future");
            return "index";
        }

        BookingBLL.create(auth,booking.getOrigin(), booking.getDestination(), pickupDate, booking.getOccupancy(), booking.getExtra());
        return "redirect:/trips";
    }

    @PostMapping(value = "/select-taxi")
    public String SelectTaxi(@Valid @ModelAttribute("plate") PlateFormData plate,
                             BindingResult result,
                             Model model) {
        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);


        if (result.hasErrors()) {
            return "index";
        }

        Taxi taxi = TaxiBLL.getByPlate(plate.getPlate());

        if(taxi == null) {
            result.rejectValue("plate","taxi.plate", "Car does not exist");
            return "index";
        }

        return "redirect:/trips?taxi=" + taxi.getLicensePlate();
    }
}
