package teletrader.stockexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import teletrader.stockexchange.model.User;
import teletrader.stockexchange.repository.UserRepository;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    public UserService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(email, password));
        return userRepository.findByEmail(email).orElseThrow();
    }
}
