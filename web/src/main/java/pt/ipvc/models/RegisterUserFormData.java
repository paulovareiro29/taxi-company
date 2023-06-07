package pt.ipvc.models;
import javax.validation.constraints.*;

public class RegisterUserFormData {
    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have a minimum length of 6 characters")
    public String password;

    @NotBlank(message = "Confirm Password is required")
    public String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
