package helmyfadlail.technicaltestbva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import helmyfadlail.technicaltestbva.dto.LoginUserRequest;
import helmyfadlail.technicaltestbva.dto.RegisterUserRequest;
import helmyfadlail.technicaltestbva.dto.TokenResponse;
import helmyfadlail.technicaltestbva.dto.WebResponse;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.service.AuthService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        authService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(path = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(path = "/api/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }
}
