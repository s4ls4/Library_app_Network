package service;

import domain.Book;
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

public class BookService implements IBookService{

    private ExecutorService executorService;
    private TCPClient tcpClient;

    public BookService(ExecutorService executorService, TCPClient tcpClient){
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public void setPageSize(int size) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IBookService.SET_PAGE_SIZE)
                    .body(String.valueOf(size))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });

        }

    @Override
    public Future<Set<Book>> getNextBook() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IBookService.GET_NEXT_BOOK)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> books = Arrays.asList(response.getBody().split(";"));
            return books.stream().map((book) -> Factory.bookFromFile(Arrays.asList(book.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void addBook(Book book) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IBookService.ADD_BOOK)
                    .body(Factory.bookToFile(book))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public void deleteBook(Long id) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IBookService.DELETE_BOOK)
                    .body(id.toString())
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }

    @Override
    public Future<Set<Book>> getAllBooks() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(IBookService.GET_ALL_BOOKS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
            if (response.getBody().length() == 0)
                return new HashSet<>();
            List<String> books = Arrays.asList(response.getBody().split(";"));
            return books.stream().map((book) -> Factory.bookFromFile(Arrays.asList(book.split(",")))).collect(Collectors.toSet());
        });
    }

    @Override
    public void updateBook(Book book) throws ValidatorException {
        executorService.submit(() ->{
            Message request = Message.builder()
                    .header(IBookService.UPDATE_BOOK)
                    .body(Factory.bookToFile(book))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR))
                throw new ServiceException(response.getBody());
        });
    }
}
