package teletrader.stockexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teletrader.stockexchange.dto.LoginDto;
import teletrader.stockexchange.dto.LoginReponseDto;
import teletrader.stockexchange.dto.NewUserDto;
import teletrader.stockexchange.model.User;
import teletrader.stockexchange.service.UserService;
import teletrader.stockexchange.util.JwtService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody NewUserDto dto) {
        User user = new User(dto.name, dto.surname, dto.email, dto.password);
        return new ResponseEntity<>(userService.registerUser(user),HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginReponseDto> login(@RequestBody LoginDto dto) {
        User authenticatedUser = userService.authenticate(dto.email, dto.password);
        String jwt = jwtService.generateToken(authenticatedUser);
        return new ResponseEntity<>(new LoginReponseDto(jwt),HttpStatus.OK);
    }

}
