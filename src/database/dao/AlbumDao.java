package database.dao;

import model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDao {

    /**
     * Albums table info
     */
    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";


    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "Select * FROM " + TABLE_ALBUMS +
            "\nWHERE " + COLUMN_ALBUM_ARTIST + " = ? ORDER BY " + COLUMN_ALBUM_NAME +
            "\nCOLLATE NOCASE";

    Connection connection;
    PreparedStatement queryAlbumsByArtistId;

    public AlbumDao(Connection connection) {
        this.connection = connection;
    }

    public List<Album> queryAlbumsForArtistId(int id) {
        try{
            queryAlbumsByArtistId = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            queryAlbumsByArtistId.setInt(1, id);
            ResultSet result = queryAlbumsByArtistId.executeQuery();

            List<Album> albums = new ArrayList<>();
            while(result.next()){
                Album album = new Album();
                album.setId(result.getInt(1));
                album.setName(result.getString(2));
                album.setArtistId(id);
                albums.add(album);
            }
            return albums;
        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
