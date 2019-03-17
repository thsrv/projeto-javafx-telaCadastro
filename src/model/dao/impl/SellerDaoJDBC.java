package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entidades.Departamento;
import model.entidades.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conect;

    public SellerDaoJDBC(Connection conect) {
        this.conect = conect;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement prepSt = null;
        try {
            prepSt = conect.prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            prepSt.setString(1, obj.getName());
            prepSt.setString(2, obj.getEmail());
            prepSt.setDate(3, new Date(obj.getBirthDate().getTime()));
            prepSt.setDouble(4, obj.getBaseSalary());
            prepSt.setInt(5, obj.getDepartment().getId());

            int rowsAffected = prepSt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = prepSt.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepSt);

        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement prepSt = null;
        try {
            prepSt = conect.prepareStatement(
                    "UPDATE seller " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "WHERE Id = ?");

            prepSt.setString(1, obj.getName());
            prepSt.setString(2, obj.getEmail());
            prepSt.setDate(3, new Date(obj.getBirthDate().getTime()));
            prepSt.setDouble(4, obj.getBaseSalary());
            prepSt.setInt(5, obj.getDepartment().getId());
            prepSt.setInt(6, obj.getId());

            prepSt.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepSt);

        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement prepSt = null;
        try {
            prepSt = conect.prepareStatement(
                    "DELETE FROM seller " +
                            "WHERE Id = ?");
            prepSt.setInt(1, id);

            int rows = prepSt.executeUpdate();

            if (rows == 0) {
                throw new DbException("Error! Id nonexistent!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepSt);
        }

    }

    //Buscar ID
    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conect.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?");


            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();//codigo vai ser executado e armazernar no resultSet.
            if (resultSet.next()) {//testar se gerou resultado.
                //Criando obj department
                Departamento dep = intantiateDepartment(resultSet);


                //Criando obj seller
                Seller seller = intantiateSeller(resultSet, dep);
                return seller;
            }
            return null;//não existe nenhum seller com id informado.
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }

    }

    private Seller intantiateSeller(ResultSet resultSet, Departamento dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(dep);
        return seller;
    }

    private Departamento intantiateDepartment(ResultSet resultSet) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(resultSet.getInt("DepartmentId"));
        dep.setNome(resultSet.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            prepStatement = conect.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name");

            resultSet = prepStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (resultSet.next()) {
                Departamento dep = map.get(resultSet.getInt("DepartmentId"));
                if (dep == null) {
                    dep = intantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = intantiateSeller(resultSet, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Departamento department) {
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            prepStatement = conect.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name");

            prepStatement.setInt(1, department.getId());
            resultSet = prepStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (resultSet.next()) {
                Departamento dep = map.get(resultSet.getInt("DepartmentId"));
                if (dep == null) {
                    dep = intantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = intantiateSeller(resultSet, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
