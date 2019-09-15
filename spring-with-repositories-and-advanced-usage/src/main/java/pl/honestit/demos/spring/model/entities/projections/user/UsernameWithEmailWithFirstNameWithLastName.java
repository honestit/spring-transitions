package pl.honestit.demos.spring.model.entities.projections.user;

import pl.honestit.demos.spring.model.entities.projections.Projection;

public interface UsernameWithEmailWithFirstNameWithLastName extends Projection {

    String getUsername();

    String getEmail();

    UserDetails getDetails();

    interface UserDetails {

        String getFirstName();

        String getLastName();
    }
}
