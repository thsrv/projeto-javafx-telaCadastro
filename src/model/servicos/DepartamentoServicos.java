package model.servicos;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

import java.util.List;

public class DepartamentoServicos {

    private DepartamentoDao dao = DaoFactory.createDepartmentDao();

    public List<Departamento> findAll() {
        return dao.findAll();
    }


}
