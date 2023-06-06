package pt.ipvc.models;

import javax.validation.constraints.NotBlank;

public class PlateFormData {

    @NotBlank
    public String plate;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
