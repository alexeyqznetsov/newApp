package com.example.newApp;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.sql.*;
import java.util.Date;

public class sqlClass {

    private static final String URL = "jdbc:postgresql://192.168.0.26:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String ft = "app.first_table";
    private static final String st = "app.second_table";

    public String Select(String login) {

        String SQL = "SELECT * FROM " + ft +
                " JOIN " + st + " ON(" + ft + ".login = " + st + ".login)" +
                " WHERE " + ft + ".login = " + login;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  //подключение к БД
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);           //объект, который содержит запрос к БД

            if ((resultSet.next())) { //возвращаем значения
                login = resultSet.getString("login");
                String pass = resultSet.getString("pass");
                Date date_time = resultSet.getDate("date_time");
                String email = resultSet.getString("email");

                System.out.println("login: " + login + "; pass: " + pass + "; date_time: " + date_time +
                        "; login: " + login + "; email: " + email);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ResponseStatusException in testResponseStatusException");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {                                                       //закрытие ресурсов
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return login;
    }


    public void Insert(String login, String email, String login1, String pass, java.sql.Date date_time) {

        String insertQuery = "INSERT INTO " + st + "(login, email) VALUES (?, ?);" +
                "INSERT INTO " + ft + "(login, pass, date_time) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  //try-with-res., авт-ки особождает ресурсы
             PreparedStatement pS = connection.prepareStatement(insertQuery))
        {
            pS.setString(1, login);
            pS.setString(2, email);
            pS.setString(3, login1);
            pS.setString(4, pass);
            pS.setDate(5, java.sql.Date.valueOf(String.valueOf(date_time)));

            pS.executeUpdate();     //меняем данные
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
