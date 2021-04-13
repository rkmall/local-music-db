package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artist {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Artist() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();        // returning normal int
    }

    public void setId(int id) {
        this.id.set(id);        // getting normal int
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return id +". Artist: " + name;
    }
}
