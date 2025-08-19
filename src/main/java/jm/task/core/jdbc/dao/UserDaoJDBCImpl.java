package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try( Statement stmt = conn.createStatement() ) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `pp_task1`.`users` " + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT)");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement stmt = conn.createStatement()){
            stmt.executeUpdate("DROP TABLE IF EXISTS `pp_task1`.`users`");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `pp_task1`.`users`(`name`,`lastname`,`age`) VALUES (?,?,?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `pp_task1`.`users` WHERE (`id` = ?)")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM `pp_task1`.`users`")){
            while(resultSet.next()){
                User user = new User(resultSet.getString("name"),resultSet.getString("lastname"),resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement stmt = conn.createStatement()){
            stmt.executeUpdate("TRUNCATE TABLE `pp_task1`.`users`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}