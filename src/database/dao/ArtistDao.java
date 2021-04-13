package database.dao;

import model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDao {

    /**
     * Artists table info
     */
    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    /**
     * Query Strings
     */
    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String UPDATE_ARTIST_NAME = "UPDATE " + TABLE_ARTISTS +
            " SET " + COLUMN_ARTIST_NAME + " = ?" +
            "\nWHERE " + COLUMN_ARTIST_ID + " = ?";


    Connection connection;
    Statement statement;
    PreparedStatement updateArtistName;

    public ArtistDao(Connection connection) {
        this.connection = connection;
    }

    // Query all artists
    public List<Artist> queryAllArtists(int sortOrder) {

        try{
            statement = connection.createStatement();
        }catch (SQLException e){
            System.out.println("Error opening connection in ArtistDao");
        }

        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        // Ignore order if sortOrder is ORDER BY NONE
        if(sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder == ORDER_BY_ASC) {
                sb.append("ASC");
            } else {
                sb.append("DESC");
            }
        }

        // sb = SELECT * FROM artists ORDER BY name COLLATE NOCASE DESC

        // Try with resources
        try(ResultSet result = statement.executeQuery(sb.toString())) {

            // Artist list to be returned after storing artist
            List<Artist> artists = new ArrayList<>();

            while(result.next()){
                // Thread.sleep just to animate the progressBar
                try{
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    System.out.println("Interrupted: " + e.getMessage());
                }

                Artist artist = new Artist();
                artist.setId(result.getInt(INDEX_ARTIST_ID));
                artist.setName(result.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }

            return artists;
        }catch(SQLException e){
            System.out.println("Query failed:" + e.getMessage());
            return null;
        }
    }

    public boolean updateArtistName(String newValue, int id) {
        try{
            updateArtistName = connection.prepareStatement(UPDATE_ARTIST_NAME);
            updateArtistName.setString(1, newValue);
            updateArtistName.setInt(2, id );
            int affectedRecord = updateArtistName.executeUpdate();
            // Return true if only affected record is 1
            return affectedRecord == 1;
        }catch (SQLException e){
            System.out.println("Update failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
