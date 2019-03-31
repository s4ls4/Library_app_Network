package domain;


public class Client extends BaseEntity<Long>{
    private String serialNumber;
    private String name;
    private int spent;

    public Client() {
    }

    public Client(String serialNumber, String name, int spent) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.spent = spent;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String a) {
        this.name = a;
    }

    public int getSpent() {
        return spent;
    }

    public void setSpent(int group) {
        this.spent = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != client.name) return false;
        if (!serialNumber.equals(client.serialNumber)) return false;
        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + spent;
        return result;
    }

    @Override
    public String toString() {
        return "Client: " +
                "serialNumber - " + serialNumber +
                " | name - " + name  +
                " | spent - " + spent +
                " " +super.toString();
    }
}

