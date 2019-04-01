package service;

import domain.Purchase;
import domain.validators.ValidatorException;

import java.util.Set;
import java.util.concurrent.Future;

public interface IPurchaseService {

    String SERVER_HOST = "localhost";
    int SERVER_PORT = 1234;

    String SET_PAGE_SIZE = "setPageSize";
    void setPageSize(int size);

    String GET_NEXT_PURCHASE = "getNextPurchase";
    Future<Set<Purchase>> getNextPurchase();

    String ADD_PURCHASE = "addPurchase";
    void addPurchase(Purchase purchase) throws ValidatorException;

    String GET_ALL_Purchases = "getAllPurchases";
    Future<Set<Purchase>> getAllPurchases();
}
