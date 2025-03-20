package teletrader.stockexchange.dto;

import lombok.Getter;
import lombok.Setter;
import teletrader.stockexchange.model.StockOrder;

public class NewStockOrderResponseDto {
    @Getter @Setter
    private String message;

    public NewStockOrderResponseDto(String message) {
        this.message = message;
    }
}
