package model;

public class Song {

    private int id;
    private String name;
    private int track;
    private int albumId;

    public Song(){}

    public Song(int id, String name, int track, int albumId) {
        this.id = id;
        this.name = name;
        this.track = track;
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return id + ". Song: " + name + " Track:" + track + " AlbumID:" + albumId;
    }
}
