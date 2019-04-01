package service;

import domain.Client;
import domain.Factory;
import domain.validators.ValidatorException;
import tcp.TCPClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ClientService implements IClientService{

    private ExecutorService executorService;
    private TCPClient tcpClient;

    public ClientService(ExecutorService executorService, TCPClient tcpClient){
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public void setPageSize(int size) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IClientService.SET_PAGE_SIZE)
                    .body(String.valueOf(size))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });

    }

    @Override
    public Future<Set<Client>> getNextClient() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IClientService.GET_NEXT_CLIENT)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> clients = Arrays.asList(response.getBody().split(";"));
            return clients.stream().map((client) -> Factory.clientFromFile(Arrays.asList(client.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void addclient(Client client) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IClientService.ADD_CLIENT)
                    .body(Factory.clientToFile(client))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public void deleteClient(Long id) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IClientService.DELETE_CLIENT)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public Future<Set<Client>> getAllClients() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IClientService.GET_ALL_CLIENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> clients = Arrays.asList(response.getBody().split(";"));
            return clients.stream().map((client) -> Factory.clientFromFile(Arrays.asList(client.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void updateClient(Client client) throws ValidatorException {

        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IClientService.UPDATE_CLIENT)
                    .body(Factory.clientToFile(client))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }
}
