package com.practice.rest;

/*
 * @Created 7/27/24
 * @Project passport-application
 * @User Kumar Padigeri
 */

import com.practice.domain.PassportForm;
import com.practice.domain.PassportRegistration;
import com.practice.domain.User;
import com.practice.repository.PassportRegistrationRepository;
import com.practice.repository.UserRepository;
import com.practice.service.StatesAndCitiesService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {
    //
//    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatesAndCitiesService statesAndCitiesService;
    private final PassportRegistrationRepository passportRegistrationRepository;


    @GetMapping("/login")
    public String login(Model model ) {

        model.addAttribute("user", new User());
        return "login";
    }


//    @GetMapping("/")

    @GetMapping("/")
    public String showFormStarts(@ModelAttribute("user") User user, Model model,HttpServletResponse res) {
   //     return "passportForm"; // This should match the Thymeleaf template name
        res.setHeader("accept","text/html");
        return "redirect:/showForm";
    }

    @GetMapping("/showForm")
    public String showForm(@ModelAttribute("user") User user, Model model) {
        PassportForm passportForm = new PassportForm();
        //   passportForm.setUserId("12345"); // Example user ID, set it as needed
        passportForm.setCountry("India"); // Example country, set it as needed
        passportForm.setApplicationType("New"); // Example application type, set it as needed

        // Add attributes to the model
        model.addAttribute("passportForm", passportForm);
        model.addAttribute("states", statesAndCitiesService.getAllStates());
        return "passportForm"; // This should match the Thymeleaf template name

    }


 /*   @GetMapping("/")
    public String showForm(@ModelAttribute("user") User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken))
        {


            PassportForm passportForm = new PassportForm();
            //   passportForm.setUserId("12345"); // Example user ID, set it as needed
            passportForm.setCountry("India"); // Example country, set it as needed
            passportForm.setApplicationType("New"); // Example application type, set it as needed

            // Add attributes to the model
            model.addAttribute("passportForm", passportForm);
            model.addAttribute("states", statesAndCitiesService.getAllStates());
            return "passportForm"; // This should match the Thymeleaf template name

        }else{
            return "login";
        }



    } */

    @PostMapping("/updateCities")
    public String updateCities(@ModelAttribute("passportForm") PassportForm passportForm,
                               @RequestParam String state, Model model) {
        List<String> cities = statesAndCitiesService.getCitiesByState(state);
        model.addAttribute("passportForm", passportForm);
        model.addAttribute("states", statesAndCitiesService.getAllStates());
        model.addAttribute("cities", cities);
        return "passportForm";
    }

    @PostMapping("/submitPassportApplication")
    public String submitForm(@ModelAttribute PassportForm passportForm, Model model) {
        String tempId = getTempId(passportForm.getBookletType());
        passportRegistrationRepository.save(PassportRegistration
                .builder()
                .name(passportForm.getUserId())
                .country(passportForm.getCountry())
                .state(passportForm.getState())
                .pincode(passportForm.getPin())
                .applicationType(passportForm.getApplicationType())
                .bookletType(passportForm.getBookletType())
                .issueDate(LocalDate.parse(passportForm.getIssueDate(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")))
                .expireDate(LocalDate.parse(passportForm.getIssueDate(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")).plusYears(10))
                .temporaryId(tempId)
                .build());
        model.addAttribute("temporaryId", tempId);
        return "success";

    }

    private String getTempId(String bookletType) {
        if ("60 Pages".equalsIgnoreCase(bookletType)) {
            return "FPS-60XXXX".replace("XXXX", String.valueOf(getTemporaryIdFromDatabase()));
        } else {
            return "FPS-30XXXX".replace("XXXX", String.valueOf(getTemporaryIdFromDatabase()));
        }
    }

    public Long getTemporaryIdFromDatabase() {
        var records = passportRegistrationRepository.countAllRecords();
        return (0 == records) ? 1000L : (records + 1000);
    }


//    @GetMapping("/states")
//    public List<String> getAllStates() {
//        return passportApp.getAllStates();
//    }
//
//    @GetMapping("/cities")
//    public List<String> getCitiesByState(@RequestParam String state) {
//        return passportApp.getCitiesByState(state);
//    }

    @GetMapping("/error")
    public String errorPage() {
        log.error("errorPage");
        return "error";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, BindingResult result, Model model) {
        if (Objects.nonNull(user) && !StringUtils.isAllBlank(user.getUsername()) && !userRepository.existsByUsername(user.getUsername())) {
            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            user.setRole("USER");
            userRepository.save(user);
            return "redirect:/login";
        } else {
            log.error("User ID already exists");
            result.rejectValue("username", "error.user", "User ID already exists");
            return "register";
        }

    }


}
