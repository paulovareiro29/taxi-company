package pt.ipvc.models;
import javax.validation.constraints.*;

public class RegisterUserFormData {
    @NotBlank
    public String name;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min = 6, max = 20)
    public String password;

    @NotBlank
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
