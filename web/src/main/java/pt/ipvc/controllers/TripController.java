package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.bll.*;
import pt.ipvc.dal.*;
import pt.ipvc.exceptions.FeedbackAlreadyExistsException;
import pt.ipvc.models.FeedbackTripFormData;
import pt.ipvc.models.RegisterUserFormData;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TripController {

    @GetMapping(value="/trips")
    public String Index(Model model) {
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
        Feedback feedback = null;

        if(trip != null) {
            payment = PaymentBLL.getByTrip(trip);
            feedback = FeedbackBLL.getByTrip(trip);
        }

        User auth = SessionBLL.getAuthenticatedUser();
        model.addAttribute("auth", auth);
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("payment", payment);
        model.addAttribute("feedback", feedback);
        model.addAttribute("sendFeedback", new FeedbackTripFormData());

        return "view_trip";
    }

    @GetMapping(value="/cancel-trip/{id}")
    public String CancelTrip(@PathVariable(value = "id", required = false) UUID id, Model model) {
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";


        booking.setState(BookingStateBLL.getByName("cancelled"));
        BookingBLL.update(booking);

        return "redirect:/trips";
    }

    @PostMapping(value = "/submit-feedback/{id}")
    public String SubmitFeedback(@PathVariable(value = "id", required = false) UUID id,
                                 @Valid @ModelAttribute("sendFeedback") FeedbackTripFormData feedback,
                                 BindingResult result,
                                 Model model) {
        if(!SessionBLL.isAuthenticated()) return "redirect:/login";

        Trip trip = TripBLL.get(id);
        if(trip == null) return "redirect:/trips";

        if (result.hasErrors()) {
            return "redirect:/view-trip?id=" + trip.getBooking().getId();
        }

        try {
            FeedbackBLL.create(trip, SessionBLL.getAuthenticatedUser(), feedback.getRating(), feedback.getReview());
        }catch(FeedbackAlreadyExistsException e) {
            result.rejectValue("review", "trip.feedback", e.getMessage());
        }

        return "redirect:/view-trip?id=" + trip.getBooking().getId();
    }
}
