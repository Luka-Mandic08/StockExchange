package teletrader.stockexchange.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name="OrderBook")
public class StockOrder {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Column(name = "userId")
    @Getter @Setter
    private String userEmail;

    @Column(name = "price")
    @Min(value = 0)
    @Getter @Setter
    private double price;

    @Column(name = "amount")
    @Min(value = 0)
    @Getter @Setter
    private int amount;

    @Column(name = "submissionDate")
    @Getter @Setter
    private LocalDateTime submissionDate;

    @Column(name = "stockOrderType")
    @Getter @Setter
    private Enums.StockOrderType stockOrderType;

    @Column(name = "stockOrderMatchingType")
    @Getter @Setter
    private Enums.StockOrderMatchingType stockOrderMatchingType;

    @Column(name = "stockOrderStatus")
    @Getter @Setter
    private Enums.StockOrderStatus stockOrderStatus;

    public StockOrder(Enums.StockOrderMatchingType stockOrderMatchingType, Enums.StockOrderType stockOrderType,  int amount, double price, String userEmail) {
        this.stockOrderMatchingType = stockOrderMatchingType;
        this.stockOrderType = stockOrderType;
        this.submissionDate = LocalDateTime.now();
        this.amount = amount;
        this.price = price;
        this.userEmail = userEmail;
        this.stockOrderStatus = Enums.StockOrderStatus.OPEN;
    }

    public StockOrder() {

    }
}
