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

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class OIDCCustomUserService extends OidcUserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Logowanie Open ID Connect");
        OidcUser oidcUser = super.loadUser(userRequest);
        log.debug("OidcUser: {}", oidcUser);
        UserEntity userEntity = userRepository.findByUsername(oidcUser.getName())
                .orElseGet(() -> {
                    UserEntity s = userRepository.save(UserEntity.builder()
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
                    s.getDetails().setOwner(s);
                    return s;
                });
        return LoggedUser.builder()
                .username(userEntity.getUsername())
                .active(userEntity.getEnabled())
                .password(userEntity.getPassword())
                .attributes(oidcUser.getAttributes())
                .oidcUserInfo(oidcUser.getUserInfo())
                .oidcIdToken(oidcUser.getIdToken())
                .claims(oidcUser.getClaims())
                .authorities(userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
