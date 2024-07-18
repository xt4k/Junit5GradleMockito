package junit5.mockito.dao;

import lombok.SneakyThrows;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDao {


    @SneakyThrows
    public boolean delete(Integer userId) {
        try {
            DriverManager.getConnection("url","username","password");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
