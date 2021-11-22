package by.bsuir.wt.lab.server.dao;

public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public StudentCaseDao getCaseDao() {
        return StudentCaseDao.getInstance();
    }

}