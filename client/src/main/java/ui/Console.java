package ui;

import domain.Book;
import domain.Client;
import domain.Purchase;
import domain.validators.ValidatorException;
import service.BookService;
import service.ClientService;
import service.PurchaseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Author: Stefi Nicoara
 */
public class Console {
    private BookService bookService;
    private ClientService clientService;
    private PurchaseService purchaseService;


    public Console(BookService bookService, ClientService clientService, PurchaseService purchaseService) {

        this.bookService = bookService;
        this.clientService = clientService;
        this.purchaseService = purchaseService;

    }

    private int menu() {
        System.out.println("___________________________");
        System.out.println(" ");
        System.out.println("  B O O K   L I B R A R Y");
        System.out.println("___________________________");
        System.out.println(" ");
        System.out.println("1. Books operations");
        System.out.println("2. Clients operations");
        System.out.println("3. Buy a book");
        System.out.println("4. Filter inactive");
        System.out.println("5. Sort clients");
        System.out.println("0. Exit");

        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private int menuBooks() {
        System.out.println("_________________________________________");
        System.out.println(" ");
        System.out.println("  B O O K   L I B R A R Y / B O O K S");
        System.out.println("_________________________________________");
        System.out.println(" ");
        System.out.println("1. Print all books");
        System.out.println("2. Add a book");
        System.out.println("3. Delete a book");
        System.out.println("4. Update a book");
        System.out.println("0. Exit");

        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private int menuClients() {
        System.out.println("____________________________________________");
        System.out.println(" ");
        System.out.println("  B O O K   L I B R A R Y / C L I E N T S");
        System.out.println("____________________________________________");
        System.out.println(" ");
        System.out.println("1. Print all clients");
        System.out.println("2. Add a client");
        System.out.println("3. Delete a client");
        System.out.println("4. Update a client");
        System.out.println("0. Exit");


        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }


    /**
     * Starts the application
     */
    public void runConsole() {
        int cmdMain = menu();
        while (cmdMain > 0) {
            if (cmdMain == 1) {
                int cmdBooks = menuBooks();
                while (cmdBooks > 0) {
                    if (cmdBooks == 1) {
                        this.printAllBooks();
                    }
                    if (cmdBooks == 2) {
                        this.addBooks();
                    }
                    if (cmdBooks == 3) {
                        this.deleteBooks();
                    }
                    if (cmdBooks == 4) {
                        this.updateBooks();
                    }
                    cmdBooks = menuBooks();
                }
            }
            if (cmdMain == 2) {
                int cmdClients = menuClients();
                while (cmdClients > 0) {
                    if (cmdClients == 1) {
                        this.printClientsWithPaging();
                    }
                    if (cmdClients == 2) {
                        this.addClients();
                    }
                    if (cmdClients == 3) {
                        this.deleteClients();
                    }
                    if (cmdClients == 4) {
                        this.updateClient();
                    }
                    cmdClients = menuClients();
                }
            }
            if (cmdMain == 3) {
               this.buyBook();
            }
            if (cmdMain == 4) {
                this.filterClients();
            }
            if (cmdMain == 5) {
                this.sortClients();
            }
            cmdMain = menu();
        }
    }


    private void printAllBooks() {
        try {
            Future<Set<Book>> books = this.bookService.getAllBooks();
            books.get().forEach((i) -> System.out.println(i.toString()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a book to the repository
     */
    private void addBooks() {

        try {
            Book book = this.readBook();
            this.bookService.addBook(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a client to the repository
     */
    private void addClients() {
        try {
            Client client = this.readClient();
            this.clientService.addclient(client);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Deletes a book from the repository
     */
    private void deleteBooks() {
        System.out.println("Book id: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            Long id = Long.valueOf(bufferRead.readLine());
            this.bookService.deleteBook(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes a client from the repository
     */
    private void deleteClients() {
        System.out.println("Client id: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            Long id = Long.valueOf(bufferRead.readLine());
            this.clientService.deleteClient(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  /**
   * Updates a book from the repository
   */
  private void updateBooks() {

      try {
          Book book = this.readBook();
          this.bookService.updateBook(book);
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }

    /**
     * Update a client from the repository
     */
    private void updateClient() {
        try {
            Client client = this.readClient();
            this.clientService.updateClient(client);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }


    private void buyBook() {
        Purchase purchase = this.readPurchase();
        Future<Set<Client>> clients = this.clientService.getAllClients();
        Future<Set<Book>> books = this.bookService.getAllBooks();

        buyOperation(purchase, clients, books);
    }

    private void buyOperation(Purchase purchase, Future<Set<Client>> clients, Future<Set<Book>> books) {
        try {
            final int[] price = new int[1];
            this.purchaseService.addPurchase(purchase);
            books.get().forEach(i -> {
                if (i.getId().equals(purchase.getIdBook())) {
                    price[0] = i.getPrice();
                }
            });

            clients.get().forEach((i) -> {
                if (i.getId().equals(purchase.getIdClient())) {
                    i.setSpent(i.getSpent() + price[0]);
                }
            });

        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void filterClients() {
        try {

            Future<Set<Client>> clients = this.clientService.getAllClients();
            Stream<Client> inactive = null;
            inactive = clients.get().stream().filter(c -> c.getSpent() == 0);
            inactive.forEach((i) -> System.out.println(i.toString()));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private void sortClients() {
        Future<Set<Client>> clients = this.clientService.getAllClients();

        try {
            clients.get().stream().sorted(Comparator.comparing(Client::getSpent))
                    .forEach(c -> System.out.println(c.toString()));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads a book from the console
     *
     * @return the book object
     */
    private Book readBook() throws Exception {
        System.out.println("Read book {id, serialNumber, name, author, price}");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = Long.valueOf(bufferRead.readLine());
        String serialNumber = bufferRead.readLine();
        String name = bufferRead.readLine();
        String author = bufferRead.readLine();
        int price = Integer.parseInt(bufferRead.readLine());
        Book book = new Book(serialNumber, name, author, price);
        book.setId(id);
        return book;
    }

    /**
     * Reads a client from the console
     *
     * @return the client as the object
     */
    private Client readClient() {
        System.out.println("Read client {id, serialNumber, name, spent}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            Long idd;
            idd = Long.valueOf(bufferedReader.readLine());
            String serialNumber = bufferedReader.readLine();
            String name = bufferedReader.readLine();
            int spent = Integer.parseInt(bufferedReader.readLine());
            Client client = new Client(serialNumber, name, spent);
            client.setId(idd);
            return client;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Purchase readPurchase() {
        System.out.println("Read purchase {id, ClientId, BookId}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            Long id;
            id = Long.valueOf(bufferedReader.readLine());
            Long idClient;
            idClient = Long.valueOf(bufferedReader.readLine());
            Long idBook;
            idBook = Long.valueOf(bufferedReader.readLine());

            Purchase p = new Purchase(idClient, idBook);
            p.setId(id);
            return p;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void printBooksWithPaging() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("page size: ");
        int size = scanner.nextInt();
        bookService.setPageSize(size);

        System.out.println("'n' - next | 'x' - exit: ");
        try{
        while (true) {
            String cmd = scanner.next();
            if (cmd.equals("x")) {
                System.out.println("exit");
                break;
            } else if (cmd.equals("n")) {
                Future<Set<Book>> books = bookService.getNextBook();
                if (books.get().size() == 0) {
                    System.out.println("That's all books!");
                    break;
                }
                books.get().forEach(System.out::println);
            } else {
                System.out.println("Invalid input!");
            }
        }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private void printClientsWithPaging() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("page size: ");
        int size = scanner.nextInt();
        clientService.setPageSize(size);

        System.out.println("'n' - next | 'x' - exit: ");
        try{
            while (true) {
                String cmd = scanner.next();
                if (cmd.equals("x")) {
                    System.out.println("exit");
                    break;
                } else if (cmd.equals("n")) {
                    Future<Set<Client>> clients = clientService.getNextClient();
                    if (clients.get().size() == 0) {
                        System.out.println("That's all clients!");
                        break;
                    }
                    clients.get().forEach(System.out::println);
                } else {
                    System.out.println("Invalid input!");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
