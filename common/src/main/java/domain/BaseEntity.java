package domain;

import java.io.Serializable;

public class BaseEntity<ID> implements Serializable {
    private ID id;

    /**
     * Getter for the id
     * @return
     */
    public ID getId() {
        return id;
    }

    /**
     * Setter for the id
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}

