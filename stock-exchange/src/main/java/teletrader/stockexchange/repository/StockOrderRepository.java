package teletrader.stockexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teletrader.stockexchange.dto.StockOrderDto;
import teletrader.stockexchange.model.StockOrder;

import java.util.List;

public interface StockOrderRepository extends JpaRepository<StockOrder, Integer> {

    @Query("select new teletrader.stockexchange.dto.StockOrderDto(o.userEmail, o.amount, o.price) from StockOrder o where (o.stockOrderType = 0 and o.stockOrderStatus <= 1) order by o.price desc, o.submissionDate asc limit 10")
    public List<StockOrderDto> findTopTenBuyingStockOrders();

    @Query("select new teletrader.stockexchange.dto.StockOrderDto(o.userEmail, o.amount, o.price) from StockOrder o where (o.stockOrderType = 1 and o.stockOrderStatus <= 1) order by o.price asc, o.submissionDate asc limit 10")
    public List<StockOrderDto> findTopTenSellingStockOrders();

    @Query("select o from StockOrder o where (o.stockOrderType = 0 and o.stockOrderStatus <= 1 and o.price >= ?1 and o.userEmail != ?2) order by o.price desc, o.submissionDate asc")
    public List<StockOrder> findFittingBuyingStockOrders(double priceLimit, String email);

    @Query("select o from StockOrder o where (o.stockOrderType = 1 and o.stockOrderStatus <= 1 and o.price <= ?1 and o.userEmail != ?2) order by o.price asc, o.submissionDate asc")
    public List<StockOrder> findFittingSellingStockOrders(double priceLimit, String email);
}
