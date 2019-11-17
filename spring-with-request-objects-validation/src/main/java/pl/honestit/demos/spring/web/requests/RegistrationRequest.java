package pl.honestit.demos.spring.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"password", "rePassword"})
@NoArgsConstructor @AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private String rePassword;
}
