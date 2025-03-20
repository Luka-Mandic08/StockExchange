package teletrader.stockexchange.dto;

import lombok.Getter;
import lombok.Setter;

public class NewUserDto {
    @Getter @Setter
    public String name;
    @Getter @Setter
    public String surname;
    @Getter @Setter
    public String email;
    @Getter @Setter
    public String password;
}
