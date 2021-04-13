package database;

import database.command.DaoCommand;
import database.dao.AlbumDao;
import database.dao.ArtistDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DaoManager is the class that manages Dao classes
 * It encapsulates DataSource and Connection objects which are used
 * to delegate database operation to specified Dao class
 *
 * Maintains the single connection. Hence can be used to perform
 * database transaction operations
 */
public class DaoManager {

    protected DataSource dataSource;
    protected Connection connection = null;

    protected ArtistDao artistDao = null;
    protected AlbumDao albumDao = null;


    public DaoManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArtistDao getArtistDao(){
        if(this.artistDao == null){
            this.artistDao = new ArtistDao(getConnection());
        }

        return this.artistDao;
    }

    public ArtistDao getArtistDaoTx(){
        if(this.artistDao == null){
            this.artistDao = new ArtistDao(getTxConnection());
        }

        return this.artistDao;
    }

    public AlbumDao getAlbumDao(){
        if(this.albumDao == null){
            this.albumDao = new AlbumDao(getConnection());
        }

        return this.albumDao;
    }

    public Connection getConnection(){
        if(this.connection == null){
            this.connection = dataSource.getConnection();
        }
        return this.connection;
    }

    public Connection getTxConnection(){

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error: Autocommit setup failed" + e);
        }

        return this.connection;
    }


    public Object executeAndClose(DaoCommand command){
        try{
            return command.execute(this);
        }finally {
            try{
                if(this.connection != null){
                    this.connection.close();
                }
            }catch (SQLException e) {
                System.out.println("Error: Problem closing connection in DAO Manager: " + e);
            }
        }
    }

    public Object transactionAndClose(DaoCommand command){
        return executeAndClose(new DaoCommand() {
            @Override
            public Object execute(DaoManager daoManager) {
                return daoManager.transaction(command);
            }
        });
    }

    private Object transaction(DaoCommand command){
        try{
            Object value = command.execute(this);
            this.connection.commit();
            return value;
        }catch(SQLException e){
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error: Transaction rollback failed: " + ex);
            }
            System.out.println("Error: Transaction failed: " + e);
            return null;
        }finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (SQLException exx) {
                System.out.println("Error: Set Autocommit failed: " + exx);
            }
        }
    }
}
