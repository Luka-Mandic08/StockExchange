package teletrader.stockexchange.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginReponseDto {
    @Getter @Setter
    public String token;

    public LoginReponseDto(String token) {
        this.token = token;
    }
}
