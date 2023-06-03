package pt.ipvc.models;

import javax.validation.constraints.*;
import java.util.Date;

public class ScheduleTripFormData {

    @NotBlank(message = "Origin is required")
    public String origin;

    @NotBlank(message = "Destination is required")
    public String destination;

    @NotBlank(message = "Pickup date is required")
    public String pickupDate;

    @Min(value = 1, message = "Occupancy must be at least 1")
    public int occupancy;

    public String extra;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
