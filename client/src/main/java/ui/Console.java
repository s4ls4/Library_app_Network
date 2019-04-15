package ui;

import domain.Book;
import domain.Client;
import domain.Purchase;
import domain.validators.ValidatorException;
import service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Author: Stefi Nicoara
 */

@ComponentScan
@Component
public class Console {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private IBookService bookService;
//    private ClientService clientService;
//    private PurchaseService purchaseService;
//    private XMLBookService XMLBookService;
//    private XMLClientService XMLClientService;
//    private DBBookService DBBookService;
//    private DBClientService DBClientService;
//    private DBPurchaseService DBPurchaseService;



    @Autowired
    public Console(IBookService bookService, ExecutorService executorService) {
        this.bookService = bookService;
        this.executorService = executorService;
    }

    public Console() {
    }

    private void menuFormat() {
        System.out.println("___________________________");
        System.out.println(" ");
        System.out.println("  B O O K   L I B R A R Y");
        System.out.println("___________________________");
        System.out.println("1. File");
        System.out.println("2. XML");
        System.out.println("3. DB");

        System.out.println("0. Exit");

//        Scanner in = new Scanner(System.in);
//        return in.nextInt();
    }

    private void menu() {
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

    }

    private void menuBooks() {
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

        menuFormat();
        System.out.println("enter command: ");
        Scanner in = new Scanner(System.in);
        int format = in.nextInt();


        while (format > 0) {
            if (format == 1) {
//                System.out.println("enter cmd:");

                menu();
                System.out.println("enter command: ");
                Scanner in2 = new Scanner(System.in);
                int cmdMain = in2.nextInt();
                while (cmdMain > 0) {
                    if (cmdMain == 1) {
//                        System.out.println("enter cmd:");
                        menuBooks();
                        System.out.println("enter command: ");
                        Scanner in3 = new Scanner(System.in);
                        int cmdBooks = in3.nextInt();
                        while (cmdBooks > 0) {
                            if (cmdBooks == 1) {
                                this.printAllBooks();
                                System.out.println("enter cmd:");

                                cmdBooks = in3.nextInt();
                            }
                            if (cmdBooks == 2) {
                                this.addBooks();
                                System.out.println("enter cmd:");

                                cmdBooks = in3.nextInt();
                            }
                            if (cmdBooks == 3) {
                                this.deleteBooks();
                                System.out.println("enter cmd:");

                                cmdBooks = in3.nextInt();
                            }
                            if (cmdBooks == 4) {
                                this.updateBooks();
                                System.out.println("enter cmd:");

                                cmdBooks = in3.nextInt();
                            }
                            if (cmdBooks == 0) {
                                break;
                            }

                        }
                    }
                    if (cmdMain == 2) {
                        int cmdClients = menuClients();
                        while (cmdClients > 0) {
                            if (cmdClients == 1) {
                                //this.printClientsWithPaging();
                            }
                            if (cmdClients == 2) {
                                //this.addClients();
                            }
                            if (cmdClients == 3) {
                                //this.deleteClients();
                            }
                            if (cmdClients == 4) {
                                //this.updateClient();
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
                    System.out.println("enter cmd:");

                    cmdMain = in2.nextInt();
                }
            }
        }
    }
//            if (format == 2) {
//              int cmdMain = menu();
//                while (cmdMain > 0) {
//                    if (cmdMain == 1) {
//                        int cmdBooks = menuBooks();
//                        while (cmdBooks > 0) {
//                            if (cmdBooks == 1) {
//                                //this.printAllBooksXML();
//                                //      this.printBooksWithPagingXML();
//                            }
//                            if (cmdBooks == 2) {
//                                //    this.XMLAddBooks();
//                            }
//                            if (cmdBooks == 3) {
//                                //  this.XMLDeleteBooks();
//                            }
//                            if (cmdBooks == 4) {
//                                //this.updateBookXML();
//                            }
//                            if (cmdBooks == 5) {
//                                //this.updateBookXML();
//                            }
//                            cmdBooks = menuBooks();
//                        }
//                    }
//                }
//            }
//                    if (cmdMain == 2) {
//                        int cmdClients = menuClients();
//                        while (cmdClients > 0) {
//                            if (cmdClients == 1) {
//  //                              this.printClientsWithPagingXML();
//                            }
//                            if (cmdClients == 2) {
//    //                            this.XMLAddClients();
//                            }
//                            if (cmdClients == 3) {
//      //                          this.XMLDeleteClients();
//                            }
//                            if (cmdClients == 4) {
//        //                        this.XMLUpdateClient();
//                            }
//                            cmdClients = menuClients();
//                        }
//                    }
//                    if (cmdMain == 3) {
//          //              this.buyBookXML();
//                    }
//                    if (cmdMain == 4) {
//            //            this.filterClientsXML();
//                    }
//                    if (cmdMain == 5) {
//              //          this.sortClientsXML();
//                    }
//                    cmdMain = menu();
//                }
//
//            }
//            if (format == 3) {
//                int cmdMain = menu();
//                while (cmdMain > 0) {
//                    if (cmdMain == 1) {
//                        int cmdBooks = menuBooks();
//                        while (cmdBooks > 0) {
//                            if (cmdBooks == 1) {
//     //                           this.printBooksWithPagingDB();
//                            }
//                            if (cmdBooks == 2) {
//       //                         this.DBAddBooks();
//                            }
//                            if (cmdBooks == 3) {
//         //                       this.DBDeleteBooks();
//                            }
//                            if (cmdBooks == 4) {
//           //                     this.DBupdateBooks();
//                            }
//                            cmdBooks = menuBooks();
//                        }
//                    }
//                    if (cmdMain == 2) {
//                        int cmdClients = menuClients();
//                        while (cmdClients > 0) {
//                            if (cmdClients == 1) {
//             //                   this.printAllClientsDB();
//                            }
//                            if (cmdClients == 2) {
//               //                 this.DBAddClients();
//                            }
//                            if (cmdClients == 3) {
//                 //               this.DBDeleteClients();
//                            }
//                            if (cmdClients == 4) {
//                   //             this.DBupdateClient();
//                            }
//                            cmdClients = menuClients();
//                        }
//                    }
//                    if (cmdMain == 3) {
//                     //   this.DBbuyBook();
//                    }
//                    cmdMain = menu();
//                }




    /**
     * Prints all books from the repository
     */
    private void printAllBooks() {
        try {
        executorService.submit(() -> {
            Set<Book> books = bookService.getAllBooks();
            books.forEach(System.out::println);

        });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private void addBooks() {
        try {
            Optional<Book> book = readBook();
            book.orElseThrow(() -> new ValidatorException("Invalid input"));
//            executorService.submit(() -> {
                bookService.addBook(book.get());
                System.out.println("Book was added!");
//            });
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBooks() {
        System.out.println("Book id: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            Long id = Long.valueOf(bufferRead.readLine());
            executorService.submit(() -> {
                bookService.deleteBook(id);
                System.out.println("Book was deleted");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  /**
   * Updates a book from the repository
   */
  private void updateBooks() {

      try {
          Optional<Book> book = readBook();
          book.orElseThrow(() -> new ValidatorException("Invalid input"));
          executorService.submit(() -> {
              try {
                  bookService.updateBook(book.get());
                  System.out.println("Book updated");
              } catch (ValidatorException e) {
                  System.out.println(e.getMessage());
              }
          });
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }

    /**
     * Reads a book from the console
     *
     * @return the book object
     */
    private static Optional<Book> readBook(){
        System.out.println("Read book {id, serialNumber, name, author, price}");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long id = Long.valueOf(bufferRead.readLine());
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            String author = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());
            Book book = new Book(serialNumber, name, author, price);
            book.setId(id);
            return Optional.of(book);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
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
        while (true) {
            String cmd = scanner.next();
            if (cmd.equals("x")) {
                System.out.println("exit");
                break;
            }
            if (!cmd.equals("n")) {
                System.out.println("Invalid Option");
                continue;
            }
            Set<Book> books = bookService.getNextBook();
            if (books.size() == 0) {
                System.out.println("No more clients!");
                break;
            }
            books.forEach(System.out::println);

        }
    }


}
