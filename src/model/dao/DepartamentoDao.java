package model.dao;

import model.entidades.Departamento;

import java.util.List;

public interface DepartamentoDao {

    void insert(Departamento obj);//Inserir no banco de dados obj do paramentro de entrada.
    void update(Departamento obj);//Atualizar no banco de dados.
    void deleteById(Integer id);
    Departamento findById(Integer id);//Pegar o id e consultar no banco de dados.
    List<Departamento> findAll();
}
