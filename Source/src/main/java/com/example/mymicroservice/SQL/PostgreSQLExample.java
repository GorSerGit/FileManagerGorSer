package com.example.mymicroservice.SQL;

import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

import java.sql.ResultSet;
import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;


public class PostgreSQLExample {

    // Метод для создания базы данных и таблицы
    public void createDatabaseAndTable(String URL, String USER, String PASSWORD, String DATABASE_NAME) {
        
        Connection conn = null;
        Statement stmt = null;

        // Создание таблицы
        try (Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
                 
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Files ("
                    + "id SERIAL PRIMARY KEY, "
                    + "file BYTEA, "
                    + "title VARCHAR(100), "
                    + "description VARCHAR(1000), "
                    + "creation_date DATE "
                    + ");";

            statement.execute(createTableSQL);
            System.out.println("Таблица 'users' успешно создана или уже существует.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для вставки данных в таблицу 'users'
    public List<JSONObject> insertUser(String URL, String USER, String PASSWORD, String DATABASE_NAME, byte[] fileData, String title, String description,  Date creation_date) {
        String query = "INSERT INTO Files (file, title, description, creation_date) VALUES (?, ?, ?, ?)";

	int id = 0;

        try (Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
            
	    PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Установка параметров
            preparedStatement.setBytes(1, fileData);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setDate(4, creation_date);

            // Выполнение запроса
            preparedStatement.executeUpdate();
          
	    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1); // Получаем ID
                        System.out.println("Добавленный пользователь с ID: " + id);
                    } else {
                        System.out.println("Не удалось получить ID добавленной записи.");
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

	List<JSONObject> otvet = new ArrayList<>();

	JSONObject data = new JSONObject();

        data.put("id", String.valueOf(id));

	otvet.add(data);

	
	return otvet;
    }
    
    
    public List<JSONObject> get(String URL, String USER, String PASSWORD, String DATABASE_NAME){
                
        Connection conn = null;
        Statement stmt = null;
	List<JSONObject> otvet = null;

        try {
            
            // Установка соединения с базой данных
            conn = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
            
            // Создание объекта Statement для выполнения SQL-запросов
            stmt = conn.createStatement();
            String sql = "SELECT id, title, description, file, creation_date FROM Files"; // Запрос

            // Выполнение запроса и получение результата
            ResultSet rs = stmt.executeQuery(sql);

	    otvet = new ArrayList<>();    

            // Обработка результата
            while (rs.next()) {

		String otv = null;
                
		JSONObject data = new JSONObject();

		otv = new String(rs.getBytes("file"), StandardCharsets.UTF_8);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		data.put("id", rs.getInt("id"));
        	data.put("title", rs.getString("title"));
        	data.put("description", rs.getString("description"));
        	data.put("creation_date", formatter.format(rs.getDate("creation_date")));
		data.put("file", otv);

		otvet.add(data);
        	
            }            
	
            // Закрытие ResultSet
            rs.close();

        } catch (SQLException e) {
             System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Закрытие ресурсов
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

	return otvet;
    }    


    public List<JSONObject> get_this(String URL, String USER, String PASSWORD, String DATABASE_NAME, int id){

        Statement stmt = null;
	List<JSONObject> otvet = null;

	String query = "SELECT * FROM Files WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Установка значения параметра
            pstmt.setInt(1, id);

            // Выполнение запроса
            ResultSet rs = pstmt.executeQuery();
	    otvet = new ArrayList<>(); 

	    String otv = null;

            // Обработка результатов
            while (rs.next()) {
                JSONObject data = new JSONObject();

		otv = new String(rs.getBytes("file"), StandardCharsets.UTF_8);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		data.put("id", rs.getInt("id"));
        	data.put("title", rs.getString("title"));
        	data.put("description", rs.getString("description"));
        	data.put("creation_date", formatter.format(rs.getDate("creation_date")));
		data.put("file", otv);

		otvet.add(data);
            }

		

        } catch (SQLException e) {
            e.printStackTrace();
        }

            
	return otvet;

    }

    
    
}
