import service.BookService;
import service.ClientService;
import service.IBookService;
import tcp.TCPClient;
import ui.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());
        TCPClient tcpClient = new TCPClient(IBookService.SERVER_HOST, IBookService.SERVER_PORT);
        BookService bookService = new BookService(executorService, tcpClient);

        ClientService clientService = new ClientService(executorService, tcpClient);
        Console clientConsole = new Console(bookService,clientService);

        clientConsole.runConsole();

        executorService.shutdownNow();

        System.out.println("client - bye");
    }
}
