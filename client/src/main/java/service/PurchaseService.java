package service;

import domain.Factory;
import domain.Purchase;
import domain.validators.ValidatorException;
import tcp.TCPClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PurchaseService implements IPurchaseService {

    private ExecutorService executorService;
    private TCPClient tcpClient;

    public PurchaseService(ExecutorService executorService, TCPClient tcpClient){
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }


    @Override
    public void setPageSize(int size) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IPurchaseService.SET_PAGE_SIZE)
                    .body(String.valueOf(size))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public Future<Set<Purchase>> getNextPurchase() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IPurchaseService.GET_NEXT_PURCHASE)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> purchases = Arrays.asList(response.getBody().split(";"));
            return purchases.stream().map((purchase) -> Factory.purchaseFromFile(Arrays.asList(purchase.split(",")))).collect(Collectors.toSet());
        });        }

    @Override
    public void addPurchase(Purchase purchase) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IPurchaseService.ADD_PURCHASE)
                    .body(Factory.purchaseToFile(purchase))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public Future<Set<Purchase>> getAllPurchases() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IPurchaseService.GET_ALL_Purchases)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> purchases = Arrays.asList(response.getBody().split(";"));
            return purchases.stream().map((purchase) -> Factory.purchaseFromFile(Arrays.asList(purchase.split(",")))).collect(Collectors.toSet());
        });    }
}
