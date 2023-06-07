package pt.ipvc.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FinishTripFormData {

    @NotBlank(message = "Price is required")
    @Min(value = 0, message = "Price has a minimum value of 0")
    public String price;

    @NotBlank(message = "Payment method is required")
    public String paymentMethod;

    @NotNull(message = "VAT Number cannot be null")
    public String vatNumber;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
