package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 32);
        userService.saveUser("Sergey", "Sergeev", (byte) 58);
        userService.saveUser("Vasya", "Vasyeliev", (byte) 24);
        userService.saveUser("Kirill", "Kirilov", (byte) 18);
        userService.saveUser("Kirill", "Kirilov", (byte) 18);

        userService.removeUserById(10);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeConnectionHibernate();
    }
}

