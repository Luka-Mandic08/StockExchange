package teletrader.stockexchange.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {
    @Getter @Setter
    public String email;
    @Getter @Setter
    public String password;
}
