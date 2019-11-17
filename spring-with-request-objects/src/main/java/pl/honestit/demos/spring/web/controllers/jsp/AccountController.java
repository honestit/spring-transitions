package pl.honestit.demos.spring.web.controllers.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.honestit.demos.spring.model.dal.repositories.UserDetailsRepository;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.files.FileEntity;
import pl.honestit.demos.spring.model.entities.user.UserDetailsEntity;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.web.utils.Pages;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public AccountController(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping
    public String prepareUserAccountPage(Model model, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Zalogowany użytkownik: {}", loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("details", loggedUser.getDetails());
        model.addAttribute("hasProfileFile", hasProfileFile(loggedUser));

        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"edit"})
    public String beginEditUserData(Model model, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Rozpoczęcie edycji danych użytkownika: {}", loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("details", loggedUser.getDetails());
        model.addAttribute("hasProfileFile", hasProfileFile(loggedUser));
        model.addAttribute("edit", true);

        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"save"})
    public String saveEditUserData(String firstName, String lastName, String pesel, String dateOfBirth, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Zmiana danych użytkownika: {}", loggedUser);
        log.debug("firstName = {}, lastName = {}, pesel = {}, dateOfBirt = {}",
                firstName, lastName, pesel, dateOfBirth);

        UserDetailsEntity details = loggedUser.getDetails();
        if (details == null) {
            details = new UserDetailsEntity();
            details.setOwner(loggedUser);
        }
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setPesel(pesel);
        details.setDateOfBirth(LocalDate.parse(dateOfBirth));

        userDetailsRepository.save(details);
        return "redirect:/account";
    }

    @PostMapping(params = {"cancel"})
    public String cancelEditUserData() {
        return "redirect:/account";
    }

    @PostMapping(params = {"upload"})
    public String uploadProfileFile(@RequestParam MultipartFile file, Principal principal) throws IOException {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Dodawanie zdjęcia profilowego dla użytkownika: {}", loggedUser);

        UserDetailsEntity details = loggedUser.getDetails();
        if (details == null) {
            details = new UserDetailsEntity();
            details.setOwner(loggedUser);
            loggedUser.setDetails(details);
        }

        FileEntity profileFile = new FileEntity();
        profileFile.setContentType(file.getContentType());
        profileFile.setFileName(file.getOriginalFilename());
        profileFile.setData(file.getBytes());

        if (isValidProfileFile(profileFile)) {
            details.setProfileFile(profileFile);
            userDetailsRepository.save(details);
        }
        return "redirect:/account";
    }

    @GetMapping("/profile-file")
    public ResponseEntity<Resource> getUserProfileFile(Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Pobieranie zdjęcia profilowego dla użytkownika: {}", loggedUser);

        if (hasProfileFile(loggedUser)) {
            log.debug("Zwrócono zdjęcie profilowe użytkownika: {}", loggedUser);
            return buildProfileFileResponse(loggedUser);
        }
        else {
            log.debug("Nie zwrócono zdjęcia profilowego użytkownika: {}", loggedUser);
            return buildNoProfileFileResponse();
        }
    }

    private ResponseEntity<Resource> buildNoProfileFileResponse() {
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Resource> buildProfileFileResponse(UserEntity loggedUser) {
        UserDetailsEntity details = userDetailsRepository.getWithProfileFileByOwnerUsername(loggedUser.getUsername());
        FileEntity profileFile = details.getProfileFile();
        ByteArrayResource data = getProfileFileData(profileFile);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(profileFile.getContentType()))
                .header("Content-Disposition", String.format("filename=%s", profileFile.getFileName()))
                .body(data);
    }

    private ByteArrayResource getProfileFileData(FileEntity profileFile) {
        return new ByteArrayResource(profileFile.getData());
    }

    private boolean hasProfileFile(UserEntity loggedUser) {
        if (loggedUser.getDetails() == null) return false;
        if (loggedUser.getDetails().getProfileFileId() == null) return false;
        return true;
    }

    private boolean isValidProfileFile(FileEntity profileFile) {
        if (profileFile.getContentType() == null) return false;
        if (profileFile.getFileName() == null || profileFile.getFileName().isBlank()) return false;
        if (profileFile.getData() == null) return false;
        return true;
    }
}
