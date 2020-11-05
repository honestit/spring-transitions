package pl.honestit.demos.spring.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserDetailsEntity;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;
import pl.honestit.demos.spring.security.LoggedUser;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;
    private OAuth2AuthorizedClientService oAuth2ClientService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setoAuth2ClientService(OAuth2AuthorizedClientService oAuth2ClientService) {
        this.oAuth2ClientService = oAuth2ClientService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("oAuth2 Request: {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("oAuth2 User: {}", oAuth2User);
        Map<String, String> attributes = getUserAttributes(userRequest, oAuth2User);
        log.debug("Atrybuty użytkownika: {}", attributes);
        UserEntity userEntity = userRepository.findByUsername(oAuth2User.getAttribute("email"))
                .orElseGet(() -> {
                    UserEntity s = userRepository.save(UserEntity.builder()
                            .username(oAuth2User.getAttribute("email"))
                            .email(oAuth2User.getAttribute("email"))
                            .details(UserDetailsEntity.builder()
                                    .firstName(attributes.get("first_name"))
                                    .lastName(attributes.get("last_name"))
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
                .attributes(oAuth2User.getAttributes())
                .authorities(userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet()))
                .build();
    }

    private Map<String, String> getUserAttributes(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + userRequest.getAccessToken().getTokenValue());
        HttpEntity entity = new HttpEntity("", httpHeaders);
        String userInfoUri = "https://graph.facebook.com/" + oAuth2User.getName() + "?fields=id,name,first_name,last_name";
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);
        Map<String, String> attributes = response.getBody();
        return attributes;
    }
}
