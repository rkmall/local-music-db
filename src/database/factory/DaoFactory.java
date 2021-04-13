package database.factory;

import database.DaoManager;
import database.DataSource;

public class DaoFactory {

    public DaoManager createDaoManager(){

        return new DaoManager(DataSource.getInstance());
    }
}
