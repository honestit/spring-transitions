package pl.honestit.demos.spring.security.oidc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserDetailsEntity;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;
import pl.honestit.demos.spring.security.LoggedUser;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CustomOIDCUserService extends OidcUserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Logowanie Open ID Connect");
        OidcUser oidcUser = super.loadUser(userRequest);
        log.debug("OidcUser: {}", oidcUser);
        UserEntity user = userRepository.findByUsername(oidcUser.getName())
                .orElseGet(() -> {
                    log.debug("Tworzenie nowego użytkownika w bazie na podstawie użytkownika OIDC");
                    UserEntity dbUser = userRepository.save(UserEntity.builder()
                            .username(oidcUser.getName())
                            .email(oidcUser.getEmail())
                            .details(UserDetailsEntity.builder()
                                    .firstName(oidcUser.getGivenName())
                                    .lastName(oidcUser.getFamilyName())
                                    .build())
                            .enabled(true)
                            .password(userRequest.getAccessToken().getTokenValue())
                            .roles(Set.of(UserRole.builder().roleName("ROLE_USER").build()))
                            .build());
                    dbUser.getDetails().setOwner(dbUser);
                    log.debug("Utworzony użytkownik w bazie: {}", dbUser);
                    return dbUser;
                });
        log.debug("Zwracam instancję zalogowanego użytkownika w oparciu o użytkownika z bazy {} i użytkownika z logowanie OIDC: {}",
                user, oidcUser);
        return LoggedUser.builder()
                .username(user.getUsername())
                .active(user.getEnabled())
                .password(user.getPassword())
                .attributes(oidcUser.getAttributes())
                .oidcUserInfo(oidcUser.getUserInfo())
                .oidcIdToken(oidcUser.getIdToken())
                .claims(oidcUser.getClaims())
                .authorities(user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
