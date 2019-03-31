package domain;

public class Book extends BaseEntity<Long>{
    private String serialNumber;
    private String name;
    private String author;
    private int price;

    public Book() {
    }

    /**
     * Constructor for a Book object
     * @param serialNumber must be a string
     * @param name must be a string
     * @param author must be a string
     * @param price must be an int
     */
    public Book(String serialNumber, String name, String author, int price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.author = author;
    }

    /**
     * Getter for the the serial number
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Setter for the serial number
     * @param serialNumber the new serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param a the name
     */
    public void setName(String a) {
        this.name = a;
    }

    /**
     * Getter for the author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter for the author
     * @param a the author
     */
    public void setAuthor(String a) {
        this.author = a;
    }

    /**
     * Getter for the price
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter for the price
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Utility function to compare objects
     * @param o the object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!name.equals(book.name)) return false;
        if (!serialNumber.equals(book.serialNumber)) return false;
        return name.equals(book.name);
    }

    /**
     * Makes a hashCode for the object
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Book: " +
                "serialNumber - " + serialNumber  +
                " | name - " + name  +
                " | author - " + author +
                " | price - " + price+
                "  " + super.toString();
    }
}
