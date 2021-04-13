package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Album {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty artistId;


    public Album() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.artistId = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.getValue();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getArtistId() {
        return artistId.get();
    }

    public void setArtistId(int artistId) {
        this.artistId.set(artistId);
    }

    @Override
    public String toString() {
        return id + ". Album: " + name +  " ArtistID:" + artistId;
    }
}
