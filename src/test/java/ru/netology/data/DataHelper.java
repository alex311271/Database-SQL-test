package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.util.Locale;

//import static sun.security.tools.jarsigner.Main.getPass;

public class DataHelper {

    private static final String passBase = "$2a$10$WQ6/r1tJyXlAhV8VVT6vyOtlVEehdLdWNpf1X69ROXZI1uHHU4NAK";

        private DataHelper() {
    }

    private static String getId() {
        Faker faker = new Faker();
        //Универсальный id:
        return faker.internet().uuid();
    }

    private static String getLogin() {
        Faker faker = new Faker();
        //Рандомный логин:
        return faker.name().firstName();
    }

    public static String getPassword() {
        return passBase;
    }

    public static String getIncorrectPassword() {
        Faker faker = new Faker();
        //Рандомный пароль:
        String password = faker.internet().password();
        if (password.equals("qwerty123")) {
            password = faker.internet().password();
            return password;
        }
        return password;
    }



    @SneakyThrows
    public static void cleanDb() {
        var authSQL = "DELETE FROM auth_codes";
        var cardsSQL = "DELETE FROM cards";
        var usersSQL = "DELETE FROM users";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test", "admin", "22Dec22"
                );
        ) {
            runner.execute(conn, authSQL, new ScalarHandler<>());
            runner.execute(conn, cardsSQL, new ScalarHandler<>());
            runner.execute(conn, usersSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    private static void createUser() {


        var addUserSQL = "INSERT INTO users (id, login, password) VALUES (?, ?, ?);";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test", "admin", "22Dec22"
                );
        ) {
            runner.update(conn, addUserSQL, getId(), getLogin(), getPassword());
        }
    }

    public static void generateUser() {
        createUser();
    }

    @SneakyThrows
    private static User requestUser() {
        var runner = new QueryRunner();
        var userSQL = "SELECT * FROM users;";

        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test", "admin", "22Dec22")) {
            return runner.query(connection, userSQL, new BeanHandler<>(User.class));
        }
    }

    public static User getUserData() {
        return requestUser();
    }

    @SneakyThrows
    private static String requestCod(User user) {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes WHERE user_id = ?;";

        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test", "admin", "22Dec22")) {
            return runner.query(connection, codeSQL, user.getId(), new ScalarHandler<>());
        }
    }

    public static String getCod(User user) {
        return requestCod(user);
    }

    @SneakyThrows
    public static void cleanDbAfter() {
        var authSQL = "DELETE FROM auth_codes";
        var usersSQL = "DELETE FROM users";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test", "admin", "22Dec22"
                );
        ) {
            runner.execute(conn, authSQL, new ScalarHandler<>());
            runner.execute(conn, usersSQL, new ScalarHandler<>());
        }
    }


}
