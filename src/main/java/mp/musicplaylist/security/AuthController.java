package mp.musicplaylist.security;

import mp.musicplaylist.security.user.UserAccount;
import mp.musicplaylist.security.user.UserAccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class AuthController {
    private final UserAccountRepository userAccountRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AuthController(UserAccountRepository userAccountRepository,
                          org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage() {
        return "access_denied";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           Model model) {
        if (!password.equals(repeatPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }
        if (userAccountRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        UserAccount user = new UserAccount();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setSurname(surname);
        user.setRoles(Set.of("ROLE_USER"));
        userAccountRepository.save(user);
        return "redirect:/login?registered=true";
    }
}
