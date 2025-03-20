package teletrader.stockexchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import teletrader.stockexchange.model.Enums;
import teletrader.stockexchange.model.StockOrder;
import teletrader.stockexchange.model.User;
import teletrader.stockexchange.repository.StockOrderRepository;
import teletrader.stockexchange.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockOrderRepository stockOrderRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        User user1 = new User("John","Doe","john.doe@gmail.com","sifra");
        User user2 = new User("Jane","Doe","jane.doe@gmail.com","sifra");
        User user3 = new User("Tom","Jones","tom.jones@gmail.com","sifra");
        User user4 = new User("Alice", "Smith", "alice.smith@gmail.com", "sifra");
        User user5 = new User("Bob", "Brown", "bob.brown@gmail.com", "sifra");
        User user6 = new User("Charlie", "Johnson", "charlie.johnson@gmail.com", "sifra");
        User user7 = new User("David", "Williams", "david.williams@gmail.com", "sifra");
        User user8 = new User("Emma", "Jones", "emma.jones@gmail.com", "sifra");
        User user9 = new User("Frank", "Miller", "frank.miller@gmail.com", "sifra");
        User user10 = new User("Grace", "Davis", "grace.davis@gmail.com", "sifra");
        User user11 = new User("Henry", "Martinez", "henry.martinez@gmail.com", "sifra");
        User user12 = new User("Isabella", "Garcia", "isabella.garcia@gmail.com", "sifra");
        User user13 = new User("Jack", "Taylor", "jack.taylor@gmail.com", "sifra");

        if(userRepository.count() == 0) {
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(user11);
            userRepository.save(user12);
            userRepository.save(user13);
        }

        StockOrder stockOrder1 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.BUY,50,450,"tom.jones@gmail.com");
        StockOrder stockOrder2 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.BUY,45,430,"alice.smith@gmail.com");
        StockOrder stockOrder3 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.BUY,20,470,"bob.brown@gmail.com");
        StockOrder stockOrder4 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.BUY,35,445,"charlie.johnson@gmail.com");
        StockOrder stockOrder5 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.BUY,5,490,"david.williams@gmail.com");
        StockOrder stockOrder6 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.SELL,25,550,"emma.jones@gmail.com");
        StockOrder stockOrder7 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.SELL,15,530,"frank.miller@gmail.com");
        StockOrder stockOrder8 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.SELL,10,525,"grace.davis@gmail.com");
        StockOrder stockOrder9 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.SELL,15,520,"isabella.garcia@gmail.com");
        StockOrder stockOrder10 = new StockOrder(Enums.StockOrderMatchingType.LIMIT, Enums.StockOrderType.SELL,30,535,"henry.martinez@gmail.com");

        if(stockOrderRepository.count() == 0) {
            stockOrderRepository.save(stockOrder1);
            stockOrderRepository.save(stockOrder2);
            stockOrderRepository.save(stockOrder3);
            stockOrderRepository.save(stockOrder4);
            stockOrderRepository.save(stockOrder5);
            stockOrderRepository.save(stockOrder6);
            stockOrderRepository.save(stockOrder7);
            stockOrderRepository.save(stockOrder8);
            stockOrderRepository.save(stockOrder9);
            stockOrderRepository.save(stockOrder10);
        }
    }
}
