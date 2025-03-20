package teletrader.stockexchange.dto;

import lombok.Getter;
import lombok.Setter;

public class StockOrderDto {
    @Getter @Setter
    public String userEmail;
    @Getter @Setter
    public int amount;
    @Getter @Setter
    public double price;

    public StockOrderDto(String userEmail, int amount, double price) {
        this.userEmail = userEmail;
        this.amount = amount;
        this.price = price;
    }
}
