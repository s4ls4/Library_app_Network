import domain.Book;
import domain.Factory;
import domain.validators.BookValidator;
import domain.validators.ValidatorException;
import repository.BookDBRepo;
import repository.Paging.PagingRepository;
import service.IBookService;
import service.Message;
import tcp.TCPServer;
import tcp.TCPServerException;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApp {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "config"
                );

        System.out.println("server starting...");
    }

}
