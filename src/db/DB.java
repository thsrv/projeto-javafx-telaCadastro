package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    //Objeto de conexão com o banco de dados.
    private static Connection conect = null;

    //Metodo para conectar ao banco de dados.

    public static Connection getConect() {
        if (conect == null) {
            try {
                //caso não conectado, code abaixo conect.
                Properties prop = loadProperties();//ler propriedades.
                String url = prop.getProperty("dburl");//pegar url do file db.properties.
                conect = DriverManager.getConnection(url, prop);//obter conexão com o banco.

            } catch (SQLException se) {
                throw new DbException(se.getMessage());
            }
        }
        return conect;
    }

    //metodo para carregar as propriedades do file db.prop...
    private static Properties loadProperties() {
        try (FileInputStream fileStream = new FileInputStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(fileStream);
            return prop;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    //fechar a conexão.
    public static void closeConect() {
        if (conect != null) {//teste
            try {
                conect.close();
            } catch (SQLException se) {
                throw new DbException(se.getMessage());
            }
        }

    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException se) {
                throw new DbException(se.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException se) {
                throw new DbException(se.getMessage());
            }
        }
    }

}
