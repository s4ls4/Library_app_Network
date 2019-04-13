import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.IBookService;
import ui.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "config"
                );

        Console console = context
                .getBean(Console.class);
        console.runConsole();
        System.out.println("bye client");
    }
}
