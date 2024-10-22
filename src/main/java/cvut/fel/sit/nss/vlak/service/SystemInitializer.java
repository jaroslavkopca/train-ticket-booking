package cvut.fel.sit.nss.vlak.service;


import cvut.fel.sit.nss.vlak.model.User;
import cvut.fel.sit.nss.vlak.model.enums.Role;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * Default admin username
     */
    private static final String ADMIN_USERNAME = "nss";

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        System.out.println("INITILIAZIER");
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            generateAdmin();
            startPipeAndFilters();
            return null;
        });
    }


    private void startPipeAndFilters() {
//        System.out.println("BUBUBU");
//        FormPipe formPipe = new FormPipe();
//        ConnectionPipe connectionPipe = new ConnectionPipe();
//        TrainPipe trainPipe = new TrainPipe();
//        SeatPipe seatPipe = new SeatPipe();
//        PricePipe pricePipe = new PricePipe();
//
//        //ALL FILTERS
//        ConnectionFilter connectionFilter = new ConnectionFilter(formPipe, connectionPipe,new ConnectionDao());
//        TrainFilter trainFilter = new TrainFilter(connectionPipe, trainPipe);
//        SeatFilter seatFilter = new SeatFilter(trainPipe,seatPipe);
//        PriceFilter priceFilter = new PriceFilter(seatPipe,pricePipe);
//
//        //SINK
//        TicketIssuance ticketIssuance = new TicketIssuance(pricePipe);
//
//        // Start threads for concurrent processing
//        ticketIssuance.start();
//        connectionFilter.start();
//        trainFilter.start();
//        seatFilter.start();
//        priceFilter.start();
    }

    /**
     * Generates an admin account if it does not already exist.
     */
    private void generateAdmin() {
        User admin =new User();
        admin.setName("nss");
        admin.setRole(Role.admin);
    }
}

