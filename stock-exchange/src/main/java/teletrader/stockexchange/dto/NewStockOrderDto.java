package teletrader.stockexchange.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import teletrader.stockexchange.model.Enums;

import java.time.LocalDateTime;

public class NewStockOrderDto {
    @Getter @Setter
    private float price;

    @Getter @Setter
    private int amount;

    @Getter @Setter
    private Enums.StockOrderType stockOrderType;

    @Getter @Setter
    private Enums.StockOrderMatchingType stockOrderMatchingType;
}
