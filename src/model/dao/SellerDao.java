package model.dao;

import model.entidades.Departamento;
import model.entidades.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);//Inserir no banco de dados obj do paramentro de entrada.
    void update(Seller obj);//Atualizar no banco de dados.
    void deleteById(Integer id);
    Seller findById(Integer id);//Pegar o id e consultar no banco de dados.
    List<Seller> findAll();
    List<Seller> findByDepartment(Departamento department);
}
