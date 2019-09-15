package pl.honestit.demos.spring.model.entities.projections.user;

import pl.honestit.demos.spring.model.entities.projections.Projection;

public interface UsernameWithEmail extends Projection {

    String getUsername();

    String getEmail();
}
