package database.command;

import database.DaoManager;

public interface DaoCommand {

    Object execute(DaoManager daoManager);
}
