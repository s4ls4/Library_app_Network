package ui;

import domain.Book;
import domain.Client;
import domain.Purchase;
import domain.validators.ValidatorException;
import service.BookService;
import service.ClientService;
import service.IBookService;

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
//    private PurchaseService purchaseService;
//    private XMLBookService XMLBookService;
//    private XMLClientService XMLClientService;
//    private DBBookService DBBookService;
//    private DBClientService DBClientService;
//    private DBPurchaseService DBPurchaseService;


    public Console(BookService bookService, ClientService clientService) {

        this.bookService = bookService;
        this.clientService = clientService;
//        this.purchaseService = purchaseService;
//        this.XMLBookService = XMLBookService;
//        this.XMLClientService = XMLClientService;
//        this.DBBookService = DBBookService;
//        this.DBClientService = DBClientService;
//        this.DBPurchaseService = DBPurchaseService;
    }

    private int menuFormat() {
        System.out.println("___________________________");
        System.out.println(" ");
        System.out.println("  B O O K   L I B R A R Y");
        System.out.println("___________________________");
        System.out.println("1. File");
        System.out.println("2. XML");
        System.out.println("3. DB");

        System.out.println("0. Exit");

        Scanner in = new Scanner(System.in);
        return in.nextInt();
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

        int format = menuFormat();
        while (format > 0) {
            if (format == 1) {
                int cmdMain = menu();
                while (cmdMain > 0) {
                    if (cmdMain == 1) {
                        int cmdBooks = menuBooks();
                        while (cmdBooks > 0) {
                            if (cmdBooks == 1) {
                                this.printBooksWithPaging();
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
                       // this.buyBook();
                    }
                    if (cmdMain == 4) {
                        //this.filterClients();
                    }
                    if (cmdMain == 5) {
                        //this.sortClients();
                    }
                    cmdMain = menu();
                }
            }
            if (format == 2) {
                int cmdMain = menu();
                while (cmdMain > 0) {
                    if (cmdMain == 1) {
                        int cmdBooks = menuBooks();
                        while (cmdBooks > 0) {
                            if (cmdBooks == 1) {
                                //this.printAllBooksXML();
                          //      this.printBooksWithPagingXML();
                            }
                            if (cmdBooks == 2) {
                            //    this.XMLAddBooks();
                            }
                            if (cmdBooks == 3) {
                              //  this.XMLDeleteBooks();
                            }
                            if (cmdBooks == 4) {
                                //this.updateBookXML();
                            }
                            if (cmdBooks == 5) {
                                //this.updateBookXML();
                            }
                            cmdBooks = menuBooks();
                        }
                    }
                    if (cmdMain == 2) {
                        int cmdClients = menuClients();
                        while (cmdClients > 0) {
                            if (cmdClients == 1) {
  //                              this.printClientsWithPagingXML();
                            }
                            if (cmdClients == 2) {
    //                            this.XMLAddClients();
                            }
                            if (cmdClients == 3) {
      //                          this.XMLDeleteClients();
                            }
                            if (cmdClients == 4) {
        //                        this.XMLUpdateClient();
                            }
                            cmdClients = menuClients();
                        }
                    }
                    if (cmdMain == 3) {
          //              this.buyBookXML();
                    }
                    if (cmdMain == 4) {
            //            this.filterClientsXML();
                    }
                    if (cmdMain == 5) {
              //          this.sortClientsXML();
                    }
                    cmdMain = menu();
                }

            }
            if (format == 3) {
                int cmdMain = menu();
                while (cmdMain > 0) {
                    if (cmdMain == 1) {
                        int cmdBooks = menuBooks();
                        while (cmdBooks > 0) {
                            if (cmdBooks == 1) {
     //                           this.printBooksWithPagingDB();
                            }
                            if (cmdBooks == 2) {
       //                         this.DBAddBooks();
                            }
                            if (cmdBooks == 3) {
         //                       this.DBDeleteBooks();
                            }
                            if (cmdBooks == 4) {
           //                     this.DBupdateBooks();
                            }
                            cmdBooks = menuBooks();
                        }
                    }
                    if (cmdMain == 2) {
                        int cmdClients = menuClients();
                        while (cmdClients > 0) {
                            if (cmdClients == 1) {
             //                   this.printAllClientsDB();
                            }
                            if (cmdClients == 2) {
               //                 this.DBAddClients();
                            }
                            if (cmdClients == 3) {
                 //               this.DBDeleteClients();
                            }
                            if (cmdClients == 4) {
                   //             this.DBupdateClient();
                            }
                            cmdClients = menuClients();
                        }
                    }
                    if (cmdMain == 3) {
                     //   this.DBbuyBook();
                    }
                    cmdMain = menu();
                }

            }

        }
    }

    /**
     * Prints all books from the repository
     */
    private void printAllBooks() {
        try {
            Future<Set<Book>> books = this.bookService.getAllBooks();
            books.get().forEach((i) -> System.out.println(i.toString()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints all clients from the repository
     */
    private void printAllClients() {
        try {
            Future<Set<Client>> client = this.clientService.getAllClients();
            client.get().forEach((i) -> System.out.println(i.toString()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
//
//    private void printAllBooksXML() {
//        Set<Book> book = this.XMLBookService.getAllBooks();
//        book.forEach((i) -> System.out.println(i.toString()));
//    }
//
//    private void printAllClientsXML() {
//        Set<Client> client = this.XMLClientService.getAllClients();
//        client.forEach((i) -> System.out.println(i.toString()));
//    }
//
//    private void printAllBooksDB() {
//        Set<Book> books = this.DBBookService.getAllBooks();
//        books.forEach((i) -> System.out.println((i.toString())));
//    }
//
//    private void printAllClientsDB() {
//        Set<Client> clients = this.DBClientService.getAllClients();
//        clients.forEach((i) -> System.out.println((i.toString())));
//    }
//
//    private void printAllPurchaseDB() {
//        Set<Purchase> purchases = this.DBPurchaseService.getAllPurchases();
//        purchases.forEach((i)->System.out.println((i.toString())));
//    }
//
//    /**
//     * Adds a book to the repository
//     */
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
//
//
//    private void XMLAddBooks() {
//
//        try {
//            Book book = this.readBook();
//            this.XMLBookService.addBook(book);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//
//
//    private void XMLAddClients() {
//        Client client = this.readClient();
//
//        try {
//            this.XMLClientService.addClient(client);
//        } catch (ValidatorException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//
//    private void DBAddBooks() {
//
//        try {
//            Book book = this.readBook();
//            this.DBBookService.addBook(book);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//
//
//    private void DBAddClients() {
//
//        try {
//            Client client = this.readClient();
//            this.DBClientService.addClient(client);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//
//
//    /**
//     * Deletes a book from the repository
//     */
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
//
//    private void XMLDeleteBooks() {
//        System.out.println("Book id: ");
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//
//        try {
//            Long id = Long.valueOf(bufferRead.readLine());
//            this.XMLBookService.deleteBook(id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void XMLDeleteClients() {
//        System.out.println("Client id: ");
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//
//        try {
//            Long id = Long.valueOf(bufferRead.readLine());
//            this.XMLClientService.deleteClient(id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void DBDeleteBooks() {
//
//        System.out.println("Book id: ");
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//
//        try {
//            Long id = Long.valueOf(bufferRead.readLine());
//            this.DBBookService.deleteBook(id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void DBDeleteClients() {
//        System.out.println("Client id: ");
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//
//        try {
//            Long id = Long.valueOf(bufferRead.readLine());
//            this.DBClientService.deleteClient(id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
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

//    private void updateBookXML() {
//
//        this.XMLDeleteBooks();
//        this.XMLAddBooks();
//    }
//
//    private void XMLUpdateClient() {
//
//        this.XMLDeleteClients();
//        this.XMLAddClients();
//    }
//
//    private void DBupdateBooks() {
//
//        try {
//            Book book = this.readBook();
//            this.DBBookService.updateBook(book);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void DBupdateClient() {
//        Client client = this.readClient();
//
//        try {
//            this.DBClientService.updateClient(client);
//        } catch (ValidatorException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void buyBook() {
//        Purchase purchase = this.readPurchase();
//        Set<Client> clients = this.clientService.getAllClients();
//        Set<Book> books = this.bookService.getAllBooks();
//
//        buyOperation(purchase, clients, books);
//    }
//
//    private void buyOperation(Purchase purchase, Set<Client> clients, Set<Book> books) {
//        try {
//            final int[] price = new int[1];
//            this.purchaseService.addPurchase(purchase);
//            books.forEach(i -> {
//                if (i.getId().equals(purchase.getIdBook())) {
//                    price[0] = i.getPrice();
//                }
//            });
//
//            clients.forEach((i) -> {
//                if (i.getId().equals(purchase.getIdClient())) {
//                    i.setSpent(i.getSpent() + price[0]);
//                }
//            });
//
//        } catch (ValidatorException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void buyBookXML() {
//        Purchase purchase = this.readPurchase();
//        Set<Client> clients = this.XMLClientService.getAllClients();
//        Set<Book> books = this.XMLBookService.getAllBooks();
//
//
//        buyOperation(purchase, clients, books);
//    }
//
//    private void DBbuyBook() {
//        Purchase purchase = this.readPurchase();
//        Set<Client> clients = this.DBClientService.getAllClients();
//        Set<Book> books = this.DBBookService.getAllBooks();
//
//        DBbuyOperation(purchase, clients, books);
//    }
//
//    private void DBbuyOperation(Purchase purchase, Set<Client> clients, Set<Book> books) {
//        try {
//            final int[] price = new int[1];
//            this.DBPurchaseService.addPurchase(purchase);
//            books.forEach(i -> {
//                if (i.getId().equals(purchase.getIdBook())) {
//                    price[0] = i.getPrice();
//                }
//            });
//
//            clients.forEach((i) -> {
//                if (i.getId().equals(purchase.getIdClient())) {
//                    i.setSpent(i.getSpent() + price[0]);
//                }
//            });
//
//        } catch (ValidatorException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void filterClients() {
//        Set<Client> clients = this.clientService.getAllClients();
//        Stream<Client> inactive = clients.stream().filter(c -> c.getSpent() == 0);
//        inactive.forEach((i) -> System.out.println(i.toString()));
//    }
//
//    private void filterClientsXML() {
//        Set<Client> clients = this.XMLClientService.getAllClients();
//        Stream<Client> inactive = clients.stream().filter(c -> c.getSpent() == 0);
//        inactive.forEach((i) -> System.out.println(i.toString()));
//    }
//
//    private void sortClients() {
//        Set<Client> clients = this.clientService.getAllClients();
//
//        clients.stream().sorted(Comparator.comparing(Client::getSpent))
//                .forEach(c -> System.out.println(c.toString()));
//    }
//
//    private void sortClientsXML() {
//        Set<Client> clients = this.XMLClientService.getAllClients();
//
//        clients.stream().sorted(Comparator.comparing(Client::getSpent))
//                .forEach(c -> System.out.println(c.toString()));
//    }

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

//    private void printBooksWithPagingDB() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("page size: ");
//        int size = scanner.nextInt();
//        XMLBookService.setPageSize(size);
//
//        System.out.println("'n' - next | 'x' - exit: ");
//
//        while (true) {
//            String cmd = scanner.next();
//            if (cmd.equals("x")) {
//                System.out.println("exit");
//                break;
//            } else if (cmd.equals("n")) {
//                Set<Book> books = DBBookService.getNextBook();
//                if (books.size() == 0) {
//                    System.out.println("That's all books!");
//                    break;
//                }
//                books.forEach(System.out::println);
//            } else {
//                System.out.println("Invalid input!");
//            }
//        }
//    }
//
//
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

//    private void printBooksWithPagingXML() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("page size: ");
//        int size = scanner.nextInt();
//        XMLBookService.setPageSize(size);
//
//        System.out.println("'n' - next | 'x' - exit: ");
//
//        while (true) {
//            String cmd = scanner.next();
//            if (cmd.equals("x")) {
//                System.out.println("exit");
//                break;
//            } else if (cmd.equals("n")) {
//                Set<Book> books = XMLBookService.getNextBook();
//                if (books.size() == 0) {
//                    System.out.println("That's all books!");
//                    break;
//                }
//                books.forEach(System.out::println);
//            } else {
//                System.out.println("Invalid input!");
//            }
//        }
//    }
//
//
//
//
//    private void printClientsWithPagingXML() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("page size: ");
//        int size = scanner.nextInt();
//        XMLClientService.setPageSize(size);
//
//        System.out.println("'n' - next | 'x' - exit: ");
//
//        while (true) {
//            String cmd = scanner.next();
//            if (cmd.equals("x")) {
//                System.out.println("exit");
//                break;
//            } else if (cmd.equals("n")) {
//                Set<Client> clients = XMLClientService.getNextClient();
//                if (clients.size() == 0) {
//                    System.out.println("That's all clients!");
//                    break;
//                }
//                clients.forEach(System.out::println);
//            } else {
//                System.out.println("Invalid input!");
//            }
//        }
//    }
}
