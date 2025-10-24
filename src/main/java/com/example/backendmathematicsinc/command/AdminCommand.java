package com.example.backendmathematicsinc.command;

import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.model.UserRole;
import com.example.backendmathematicsinc.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AdminCommand {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminCommand(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ShellMethod(key = "create-admin", value = "Creates a new user with the admin role.")
    public String createAdmin(
            @ShellOption(help = "The username for the new admin.") String username,
            @ShellOption(help = "The password for the new admin. Be sure to use a strong one.") String password
    ) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Error: An admin with the username '" + username + "' already exists.";
        }

        User adminUser = new User();
        adminUser.setUsername(username);

        adminUser.setPassword(passwordEncoder.encode(password));

        adminUser.setRole(UserRole.ROLE_ADMIN);

        userRepository.save(adminUser);

        return "Admin user '" + username + "' created successfully!";
    }
}
