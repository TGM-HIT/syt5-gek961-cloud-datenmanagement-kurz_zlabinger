package at.zk.Datenmanagement.user.auth;

import at.zk.Datenmanagement.user.UserServiceImpl;
import at.zk.Datenmanagement.user.auth.requests.AuthenticationRequest;
import at.zk.Datenmanagement.user.auth.requests.RegisterRequest;
import at.zk.Datenmanagement.user.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationServiceImpl authService;
    private final UserServiceImpl userService;

    @PostMapping("/auth/admin/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return authService.register(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody AuthenticationRequest request) {
        return authService.signin(request);
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<?> verify(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        if(user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok().body("User " + user.getEmail() + " with role: " + user.getRole() + " verified.");
    }

}
