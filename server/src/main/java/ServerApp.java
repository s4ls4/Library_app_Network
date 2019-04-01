import domain.Book;
import domain.Client;
import domain.Factory;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import domain.validators.ValidatorException;
import repository.BookDBRepo;
import repository.ClientDBRepo;
import repository.Paging.PagingRepository;
import service.*;
import tcp.TCPServer;
import tcp.TCPServerException;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

        TCPServer tcpServer = new TCPServer(executorService,
                IBookService.SERVER_PORT);
        PagingRepository<Long, Book> bookRepository = new BookDBRepo(new BookValidator());
        BookService bookService = new BookService(executorService, bookRepository);

        PagingRepository<Long, Client> clientRepository = new ClientDBRepo(new ClientValidator());
        ClientService clientService = new ClientService(executorService, clientRepository);


        tcpServer.addHandler(
                IBookService.GET_ALL_BOOKS, (request) -> {
                    try {
                        Future<Set<Book>> result =
                                bookService.getAllBooks();
                        //compute response
//                        return new Message(Message.OK, result.get());
                        String responseBody = result.get().stream().map(Factory::bookToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IClientService.GET_ALL_CLIENTS, (request) -> {
                    try {
                        Future<Set<Client>> result =
                                clientService.getAllClients();
                        //compute response
//                        return new Message(Message.OK, result.get());
                        String responseBody = result.get().stream().map(Factory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });


        tcpServer.addHandler(
                IBookService.ADD_BOOK, (request) -> {
                    try{
                        bookService.addBook(Factory.bookFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK, "The book was added successfully （＾ω＾）");

                    }catch (TCPServerException | ValidatorException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IClientService.ADD_CLIENT, (request) -> {
                    try{
                        clientService.addClient(Factory.clientFromFile(Arrays.asList(request.getBody().split(","))));
                        return getMessage(Message.OK, "The client was added successfully （＾ω＾）");

                    }catch (TCPServerException | ValidatorException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IBookService.DELETE_BOOK, (request) -> {
                    try {
                        bookService.deleteBook(Long.parseLong(request.getBody()));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "Book was deleted successfully （＾ω＾）");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });


        tcpServer.addHandler(
                IClientService.DELETE_CLIENT, (request) -> {
                    try {
                        clientService.deleteClient(Long.parseLong(request.getBody()));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "Book was deleted successfully （＾ω＾）");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });


        tcpServer.addHandler(
                IBookService.UPDATE_BOOK, (request) -> {
                    try {
                        bookService.updateBook(Factory.bookFromFile(Arrays.asList(request.getBody().split(","))));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "Book was updated successfully （＾ω＾）");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IClientService.UPDATE_CLIENT, (request) -> {
                    try {
                        clientService.updateClient(Factory.clientFromFile(Arrays.asList(request.getBody().split(","))));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "Client was updated successfully （＾ω＾）");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IBookService.GET_NEXT_BOOK, (request) -> {
                    try {
                        Future<Set<Book>> result =
                                bookService.getNextBook();
                        //compute response
//                        return new Message(Message.OK, result.get());
                        String responseBody = result.get().stream().map(Factory::bookToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });


        tcpServer.addHandler(
                IClientService.GET_NEXT_CLIENT, (request) -> {
                    try {
                        Future<Set<Client>> result =
                                clientService.getNextClient();
                        //compute response
//                        return new Message(Message.OK, result.get());
                        String responseBody = result.get().stream().map(Factory::clientToFile).collect(Collectors.joining(";"));
                        return getMessage(Message.OK, responseBody);
                    } catch (InterruptedException | ExecutionException | TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IBookService.SET_PAGE_SIZE, (request) -> {
                    try {
                        bookService.setPageSize(Integer.parseInt(request.getBody()));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.addHandler(
                IClientService.SET_PAGE_SIZE, (request) -> {
                    try {
                        clientService.setPageSize(Integer.parseInt(request.getBody()));
                        //compute response
//                        return new Message(Message.OK, result.get());
                        return getMessage(Message.OK, "");
                    } catch (TCPServerException e) {
                        e.printStackTrace();
//                        return new Message(Message.ERROR, e.getMessage());
                        return getMessage(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.startServer();
        System.out.println("server - bye (≧∀≦ゞ");

    }

    private static Message getMessage(String header, String body) {
        return Message.builder()
                .header(header)
                .body(body)
                .build();
    }
}
