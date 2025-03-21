package teletrader.stockexchange.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import teletrader.stockexchange.model.Enums;


public class NewStockOrderDto {
    @Getter @Setter
    @Min(value = 0)
    private float price;

    @Getter @Setter
    @Min(value = 0)
    private int amount;

    @Getter @Setter
    private Enums.StockOrderType stockOrderType;

    @Getter @Setter
    private Enums.StockOrderMatchingType stockOrderMatchingType;
}
