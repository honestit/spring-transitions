package pl.honestit.demos.spring.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class EditUserRequest {

    private String firstName;
    private String lastName;
    private String pesel;
    private String dateOfBirth;

    public LocalDate getDateOfBirth() {
        return LocalDate.parse(dateOfBirth);
    }

}
