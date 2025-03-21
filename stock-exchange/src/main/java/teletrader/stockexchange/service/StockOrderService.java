package teletrader.stockexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teletrader.stockexchange.dto.StockOrderDto;
import teletrader.stockexchange.model.Enums;
import teletrader.stockexchange.model.StockOrder;
import teletrader.stockexchange.repository.StockOrderRepository;
import teletrader.stockexchange.websocket.OrderMatchingHandler;

import java.util.List;

@Service
public class StockOrderService {

    @Autowired
    private StockOrderRepository stockOrderRepository;

    @Autowired
    OrderMatchingHandler orderMatchingHandler;

    public List<StockOrderDto> findTopTenBuyingStockOrders() {
        return stockOrderRepository.findTopTenBuyingStockOrders();
    }

    public List<StockOrderDto> findTopTenSellingStockOrders() {
        return stockOrderRepository.findTopTenSellingStockOrders();
    }

    public String createStockOrder(StockOrder stockOrder) {
        StockOrder persistedOrder = stockOrderRepository.save(stockOrder);
        String message = "Order created successfully. \n \n";
        message = tryMatching(persistedOrder,message);
        return message;
    }

    //Matches orders if possible
    private String tryMatching(StockOrder newStockOrder, String message) {
        List<StockOrder> matchingOrders;
        if(newStockOrder.getStockOrderType() == Enums.StockOrderType.BUY)
            matchingOrders = findSellingStockOrders(newStockOrder);
        else
            matchingOrders = findBuyingStockOrders(newStockOrder);

        if(matchingOrders.isEmpty())
            return cancelStockOrder(newStockOrder,message);

        //Matches with orders with the best price until reaching the end or an order that satisfies the needed amount
        for(StockOrder order : matchingOrders){
            if(newStockOrder.getAmount() <= order.getAmount())
                return endFilledMatching(newStockOrder,message,order);

            message = updateStockOrders(newStockOrder,message,order);
        }
        return endPartialMatching(newStockOrder,message);

    }

    //Finds suitable selling orders to match with newly created buy order
    private List<StockOrder> findSellingStockOrders(StockOrder newStockOrder) {
        double buyLimit = newStockOrder.getPrice();
        if(newStockOrder.getStockOrderMatchingType() == Enums.StockOrderMatchingType.MARKET){
            buyLimit = Integer.MAX_VALUE;
        }
        return stockOrderRepository.findFittingSellingStockOrders(buyLimit, newStockOrder.getUserEmail());
    }

    //Finds suitable buy orders to match with newly created sell order
    private List<StockOrder> findBuyingStockOrders(StockOrder newStockOrder) {
        double buyLimit = newStockOrder.getPrice();
        if(newStockOrder.getStockOrderMatchingType() == Enums.StockOrderMatchingType.MARKET){
            buyLimit = 0;
        }
        return stockOrderRepository.findFittingBuyingStockOrders(buyLimit, newStockOrder.getUserEmail());
    }

    //Finishes message for user and cancels orders that were MARKET type
    private String cancelStockOrder(StockOrder newStockOrder, String message) {
        message += "No matching orders found. ";
        if(newStockOrder.getStockOrderMatchingType() == Enums.StockOrderMatchingType.MARKET) {
            newStockOrder.setStockOrderStatus(Enums.StockOrderStatus.CANCELED);
            stockOrderRepository.save(newStockOrder);
            message += "Cancelling market order.";
        }
        return message;
    }

    //Finishes message for user, updates the newly created order to FILLED and notifies the user of the matched order that a match was made
    private String endFilledMatching(StockOrder newStockOrder, String message, StockOrder matchedStockOrder) {
        String helperString = getMessageHelperString(newStockOrder,false);
        message += newStockOrder.getAmount() + " stocks " + helperString + " at " + matchedStockOrder.getPrice() + ".\n";
        message += "Your order has been filled.";
        notifyMatchedOrderUser(matchedStockOrder, newStockOrder.getAmount(), helperString);

        matchedStockOrder.setAmount(matchedStockOrder.getAmount() - newStockOrder.getAmount());
        newStockOrder.setAmount(0);
        newStockOrder.setStockOrderStatus(Enums.StockOrderStatus.FILLED);
        stockOrderRepository.save(newStockOrder);

        if(matchedStockOrder.getAmount() == 0)
            matchedStockOrder.setStockOrderStatus(Enums.StockOrderStatus.FILLED);
        else
            matchedStockOrder.setStockOrderStatus(Enums.StockOrderStatus.PARTIALLY_FILLED);
        stockOrderRepository.save(matchedStockOrder);
        return message;
    }

    //Amends message for user, updates the amount on the newly created order and notifies the user of the matched order that a match was made
    private String updateStockOrders(StockOrder newStockOrder, String message, StockOrder matchedStockOrder) {
        String helperString = getMessageHelperString(newStockOrder,false);
        message += matchedStockOrder.getAmount() + " stocks " + helperString + " at " + matchedStockOrder.getPrice() + ".\n";
        notifyMatchedOrderUser(matchedStockOrder, matchedStockOrder.getAmount(), helperString);

        newStockOrder.setAmount(newStockOrder.getAmount() - matchedStockOrder.getAmount());
        matchedStockOrder.setAmount(0);
        matchedStockOrder.setStockOrderStatus(Enums.StockOrderStatus.FILLED);
        stockOrderRepository.save(matchedStockOrder);
        return message;
    }

    //Finishes message for user, updates the newly created order accordingly
    private String endPartialMatching(StockOrder newStockOrder, String message) {
        message += "Unable to " + getMessageHelperString(newStockOrder,true) + " remaining " + newStockOrder.getAmount() + " stocks";
        if(newStockOrder.getStockOrderMatchingType() == Enums.StockOrderMatchingType.MARKET){
            message += ".\nCancelling remaining market order.";
            newStockOrder.setStockOrderStatus(Enums.StockOrderStatus.CANCELED);
            stockOrderRepository.save(newStockOrder);
        }
        else {
            message += " at " + newStockOrder.getPrice() + ".\n";
            newStockOrder.setStockOrderStatus(Enums.StockOrderStatus.PARTIALLY_FILLED);
            stockOrderRepository.save(newStockOrder);
        }
        return message;
    }

    //Gets adequate word for message
    private String getMessageHelperString(StockOrder order, boolean isPartiallyFilled) {
        if(order.getStockOrderType() == Enums.StockOrderType.BUY){
            if(isPartiallyFilled)
                return "purchase";
            return "purchased";
        }
        if(isPartiallyFilled)
            return "sell";
        return "sold";
    }

    //Sends notifications to users of matched orders
    private void notifyMatchedOrderUser(StockOrder matchedStockOrder, int amount, String messageHelper) {
        if(messageHelper.equals("purchased"))
            messageHelper = "sold";
        else
            messageHelper = "purchased";
        String message = amount + " stocks " + messageHelper + " at " + matchedStockOrder.getPrice() + ".\n";;
        this.orderMatchingHandler.notifyUser(matchedStockOrder.getUserEmail(), message);
    }
}
