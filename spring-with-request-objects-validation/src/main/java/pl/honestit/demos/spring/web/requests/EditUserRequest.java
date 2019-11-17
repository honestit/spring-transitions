package pl.honestit.demos.spring.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class EditUserRequest {
    
    private String firstName;
    private String lastName;
    @PESEL
    private String pesel;
    private String dateOfBirth;

    public LocalDate getDateOfBirth() {
        return LocalDate.parse(dateOfBirth);
    }

}
