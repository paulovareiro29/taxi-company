package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.PaymentBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.bll.TripBLL;
import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Payment;
import pt.ipvc.dal.Trip;
import pt.ipvc.dal.User;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TripController {

    @GetMapping(value="/trips")
    public String Index(Model model) {
        SessionBLL.login("paulovareiro@ipvc.pt","paulovareiro");
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";

        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);
        model.addAttribute("bookings", BookingBLL.index().stream().filter(booking -> booking.getClient().getId().compareTo(auth.getId()) == 0).collect(Collectors.toList()));

        return "trips";
    }

    @GetMapping(value="/view-trip")
    public String ViewTrip(@RequestParam(value = "id", required = false) UUID id, Model model) {
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";

        Trip trip = TripBLL.getByBooking(booking);
        Payment payment = null;

        if(trip != null)
            payment = PaymentBLL.getByTrip(trip);

        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("payment", payment);

        return "view_trip";
    }
}
