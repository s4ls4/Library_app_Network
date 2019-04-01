package service;

import domain.Client;
import domain.validators.ValidatorException;

import java.util.Set;
import java.util.concurrent.Future;

public interface IClientService {

    String SERVER_HOST = "localhost";
    int SERVER_PORT = 1234;

    String SET_PAGE_SIZE = "setPageSize";
    void setPageSize(int size);

    String GET_NEXT_CLIENT = "getNextClient";
    Future<Set<Client>> getNextClient();

    String ADD_CLIENT = "addClient";
    void addclient(Client client) throws ValidatorException;

    String DELETE_CLIENT = "deleteClient";
    void deleteClient(Long id) throws ValidatorException;

    String GET_ALL_CLIENTS = "getAllClients";
    Future<Set<Client>> getAllClients();

    String UPDATE_CLIENT = "updateClient";
    void updateClient(Client client) throws ValidatorException;
}
