package pl.honestit.demos.spring.model.entities.projections.user;

import pl.honestit.demos.spring.model.entities.projections.Projection;

import java.util.Set;

public interface UsernameWithRoleNames extends Projection {

    String getUsername();

    Set<RoleName> getRoles();

    interface RoleName {

        String getRoleName();
    }
}
