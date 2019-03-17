package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.SellerDaoJDBC;


public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConect());
    }

    public static DepartamentoDao createDepartmentDao() {
        return new DepartamentoDaoJDBC(DB.getConect());
    }
}
