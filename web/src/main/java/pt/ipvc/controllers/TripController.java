package pt.ipvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pt.ipvc.bll.*;
import pt.ipvc.dal.*;
import pt.ipvc.exceptions.FeedbackAlreadyExistsException;
import pt.ipvc.models.FeedbackTripFormData;
import pt.ipvc.models.FinishTripFormData;
import pt.ipvc.models.PlateFormData;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TripController {

    @GetMapping(value="/trips")
    public String Index(@RequestParam(name="taxi", required = false) String taxiId, HttpSession session, Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        User auth = (User) session.getAttribute("auth");
        model.addAttribute("auth", auth);
        model.addAttribute("clientBookings", BookingBLL.index().stream().filter(booking -> booking.getClient().getId().compareTo(auth.getId()) == 0).collect(Collectors.toList()));
        model.addAttribute("plate", new PlateFormData());

        Taxi taxi = TaxiBLL.getByPlate(taxiId);
        model.addAttribute("taxi", taxi);
        model.addAttribute("completedTrips", TripBLL.index().stream().filter(t -> t.getEmployee().getEmail().equalsIgnoreCase(auth.getEmail())).collect(Collectors.toList()));

        if(taxi != null){
            List<Booking> bookings = BookingBLL.index().stream()
                    .filter(booking -> booking.getTaxi() != null && booking.getTaxi().getLicensePlate().equalsIgnoreCase(taxi.getLicensePlate())).sorted(Comparator.comparing(Booking::getPickupDate)).collect(Collectors.toList());

            model.addAttribute("ongoingBookings", bookings.stream().filter(b -> b.getState().getName().equalsIgnoreCase("ongoing")).collect(Collectors.toList()));
            model.addAttribute("confirmedBookings",  bookings.stream().filter(b -> b.getState().getName().equalsIgnoreCase("confirmed")).collect(Collectors.toList()));
        }


        return "trips";
    }

    @GetMapping(value="/view-trip")
    public String ViewTrip(@RequestParam(value = "id", required = false) UUID id, HttpSession session, Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";

        Trip trip = TripBLL.getByBooking(booking);
        Payment payment = null;
        Feedback feedback = null;

        if(trip != null) {
            payment = PaymentBLL.getByTrip(trip);
            feedback = FeedbackBLL.getByTrip(trip);
        }

        User auth = (User) session.getAttribute("auth");
        model.addAttribute("auth", auth);
        model.addAttribute("booking", booking);
        model.addAttribute("trip", trip);
        model.addAttribute("payment", payment);
        model.addAttribute("feedback", feedback);
        model.addAttribute("sendFeedback", new FeedbackTripFormData());
        model.addAttribute("finishTrip", new FinishTripFormData());
        model.addAttribute("methods", PaymentMethodBLL.index());

        return "view_trip";
    }

    @GetMapping(value="/cancel-trip/{id}")
    public String CancelTrip(@PathVariable(value = "id", required = false) UUID id, HttpSession session, Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";


        booking.setState(BookingStateBLL.getByName("cancelled"));
        BookingBLL.update(booking);

        return "redirect:/trips";
    }

    @GetMapping(value="/start-trip/{id}")
    public String StartTrip(@PathVariable(value = "id", required = false) UUID id, HttpSession session, Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";

        booking.setState(BookingStateBLL.getByName("ongoing"));
        BookingBLL.update(booking);

        return "redirect:/view-trip?id=" + booking.getId();
    }

    @PostMapping(value="/finish-trip/{id}")
    public String FinishTrip(@PathVariable(value = "id", required = false) UUID id,
                             @Valid @ModelAttribute("finishTrip") FinishTripFormData finishTripFormData,
                             HttpSession session,
                             Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        Booking booking = BookingBLL.get(id);
        if(booking == null) return "redirect:/trips";

        TripBLL.create(booking,
                (User) session.getAttribute("auth"),
                booking.getPickupDate(),
                Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),
                Float.parseFloat(finishTripFormData.getPrice()));

        Trip trip = TripBLL.getByBooking(booking);
        PaymentBLL.create(trip,
                PaymentMethodBLL.getByName(finishTripFormData.getPaymentMethod()),
                Float.parseFloat(finishTripFormData.getPrice()),
                finishTripFormData.getVatNumber().isBlank() ? null : Integer.parseInt(finishTripFormData.getVatNumber()));

        booking.setState(BookingStateBLL.getByName("completed"));
        BookingBLL.update(booking);

        return "redirect:/trips";
    }


    @PostMapping(value = "/submit-feedback/{id}")
    public String SubmitFeedback(@PathVariable(value = "id", required = false) UUID id,
                                 @Valid @ModelAttribute("sendFeedback") FeedbackTripFormData feedback,
                                 BindingResult result,
                                 HttpSession session,
                                 Model model) {
        if(session.getAttribute("auth") == null) return "redirect:/login";

        Trip trip = TripBLL.get(id);
        if(trip == null) return "redirect:/trips";

        if (result.hasErrors()) {
            return "redirect:/view-trip?id=" + trip.getBooking().getId();
        }

        try {
            FeedbackBLL.create(trip, (User) session.getAttribute("auth"), feedback.getRating(), feedback.getReview());
        }catch(FeedbackAlreadyExistsException e) {
            result.rejectValue("review", "trip.feedback", e.getMessage());
        }

        return "redirect:/view-trip?id=" + trip.getBooking().getId();
    }

    @PostMapping(value = "/trips-select-taxi")
    public String SelectTaxi(@Valid @ModelAttribute("plate") PlateFormData plate,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        User auth = (User) session.getAttribute("auth");
        model.addAttribute("auth", auth);


        if (result.hasErrors()) {
            return "trips";
        }

        Taxi taxi = TaxiBLL.getByPlate(plate.getPlate());

        if(taxi == null) {
            result.rejectValue("plate","taxi.plate", "Car does not exist");
            return "trips";
        }

        return "redirect:/trips?taxi=" + taxi.getLicensePlate();
    }
}
