package helmyfadlail.technicaltestbva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import helmyfadlail.technicaltestbva.dto.LoginUserRequest;
import helmyfadlail.technicaltestbva.dto.RegisterUserRequest;
import helmyfadlail.technicaltestbva.dto.TokenResponse;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.repository.UserRepository;
import helmyfadlail.technicaltestbva.security.BCrypt;
import helmyfadlail.technicaltestbva.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Long expiration_token = 24L * 60 * 60 * 1000;

    private Long next30Days() {
        return System.currentTimeMillis() + expiration_token;
    }

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        if (request.getPassword().equals(request.getConfirmPassword())) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password doesn't match");
        }

    }

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        log.info("Request" + request.getUsername());

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername(), expiration_token);
            user.setToken(token);
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder().token(user.getToken()).expiredAt(user.getTokenExpiredAt()).build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }
}
