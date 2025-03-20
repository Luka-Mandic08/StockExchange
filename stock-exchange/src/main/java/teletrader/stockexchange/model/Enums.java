package teletrader.stockexchange.model;

public class Enums {
    public enum StockOrderType {
        BUY, SELL
    }

    public enum StockOrderMatchingType {
        MARKET, LIMIT
    }

    public enum StockOrderStatus {
        OPEN, PARTIALLY_FILLED, FILLED, CANCELED
    }
}
