package pt.ipvc.models;

import javax.validation.constraints.NotBlank;

public class PlateFormData {

    @NotBlank(message = "License plate is required")
    public String plate;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
