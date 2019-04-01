package domain;

import java.util.List;

public class Factory {

    public static String bookToFile(Book m) {
        return "" + m.getId() + "," + m.getSerialNumber() + "," + m.getName() + "," + m.getAuthor() + "," + m.getPrice();
    }

    public static String clientToFile(Client c){
        return ""+c.getId()+","+c.getSerialNumber()+","+c.getName()+","+c.getSpent();
    }

    public static Book bookFromFile(List<String> data) {
        Long id = Long.valueOf(data.get(0).trim());
        String serialNumber = data.get(1).trim();
        String name = data.get(2).trim();
        String author = data.get(3).trim();
        int price = Integer.parseInt(data.get(4).trim());
        Book b = new Book(serialNumber,name,author,price);
        b.setId(id);
        return b;
    }

    public static Client clientFromFile(List<String> data) {
        Long id = Long.valueOf(data.get(0).trim());
        String serialNumber = data.get(1).trim();
        String name = data.get(2).trim();
        int spent = Integer.parseInt(data.get(3).trim());
        Client c = new Client(serialNumber,name,spent);
        c.setId(id);
        return c;
    }
}
