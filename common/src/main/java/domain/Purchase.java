package domain;

public class Purchase extends BaseEntity<Long> {
    private Long idClient;
    private Long idBook;

    public Purchase() {}

    public Purchase(Long idClient, Long idBook) {
        this.idBook = idBook;
        this.idClient = idClient;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long id) {
        this.idClient = id;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long id) {
        this.idBook = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (!idClient.equals(purchase.idClient)) return false;
        if (!idBook.equals(purchase.idBook)) return false;
        return idClient.equals(purchase.idClient);
    }

    @Override
    public int hashCode() {
        int result = idClient.hashCode();
        result = 31 * result + idBook.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Purchase: " +
                "Client id: " + idBook + '\'' +
                "| Book id: " + idClient + '\'' +
                super.toString();
    }
}

