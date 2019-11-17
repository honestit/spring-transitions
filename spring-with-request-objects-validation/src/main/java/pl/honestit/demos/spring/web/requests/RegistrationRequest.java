package pl.honestit.demos.spring.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString(exclude = {"password", "rePassword"})
@NoArgsConstructor @AllArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Nazwa użytkownika musi być uzupełniona")
    private String username;
    @NotBlank(message = "Email musi być uzupełniony")
    @Email(message = "Email musi być poprawny")
    private String email;
    @NotBlank(message = "Hasło musi być uzupełnione")
    @Size(min = 8, message = "Hasło musi zawierać minimum 8 znaków")
    private String password;
    @NotBlank(message = "Powtórzone hasło musi być uzupełnione")
    @Size(min = 8, message = "Powtórzone hasło musi zawierać minimum 8 znaków")
    private String rePassword;
}
