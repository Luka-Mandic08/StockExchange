package teletrader.stockexchange.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teletrader.stockexchange.dto.NewStockOrderDto;
import teletrader.stockexchange.dto.NewStockOrderResponseDto;
import teletrader.stockexchange.dto.StockOrderDto;
import teletrader.stockexchange.model.Enums;
import teletrader.stockexchange.model.StockOrder;
import teletrader.stockexchange.service.StockOrderService;
import teletrader.stockexchange.util.JwtService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stockorders")
public class StockOrderController {

    @Autowired
    private StockOrderService stockOrderService;
    @Autowired
    private JwtService jwtService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/create",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewStockOrderResponseDto> createStockOrder(@RequestBody NewStockOrderDto dto, @RequestHeader("Authorization") String token) {
        String userEmail = jwtService.extractUsername(token.substring(7));
        String message = stockOrderService.createStockOrder(
                new StockOrder(dto.getStockOrderMatchingType(),dto.getStockOrderType(),dto.getAmount(),dto.getPrice(), userEmail));
        return new ResponseEntity<>(new NewStockOrderResponseDto(message), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/gettopbuying")
    public ResponseEntity<List<StockOrderDto>> getTopBuyingStockOrders() {
        return new ResponseEntity<>(stockOrderService.findTopTenBuyingStockOrders(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/gettopselling")
    public ResponseEntity<List<StockOrderDto>> getTopSellingStockOrders() {
        return new ResponseEntity<>(stockOrderService.findTopTenSellingStockOrders(), HttpStatus.OK);
    }
}
