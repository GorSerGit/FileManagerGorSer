package com.example.mymicroservice.controller;

import com.example.mymicroservice.model.MyRequest;
import com.example.mymicroservice.model.MyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.mymicroservice.SQL.PostgreSQLExample;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

import java.text.SimpleDateFormat;



@RestController
@RequestMapping("/api")
public class MyController {

    @PostMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
    public MyResponse processRequest(@RequestBody MyRequest request) {

	String URL = "jdbc:postgresql://MyPostgreSQL:5432/"; // 
    	String USER = "GorSer";
    	String PASSWORD = "123456";
    	String DATABASE_NAME = "Files";

	PostgreSQLExample example = new PostgreSQLExample();
 	example.createDatabaseAndTable(URL, USER, PASSWORD, DATABASE_NAME );

	List<JSONObject> otvet = example.get(URL, USER, PASSWORD, DATABASE_NAME);

	List<String> stringList = new ArrayList<>();

	Collections.sort(otvet, new Comparator<JSONObject>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String dateA = a.getString("creation_date");
                String dateB = b.getString("creation_date");
                
                try {
                    Date d1 = new Date(dateFormat.parse(dateA).getTime());
                    Date d2 = new Date(dateFormat.parse(dateB).getTime());
                    return dateA.compareTo(dateB);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
	for (JSONObject jsonObject : otvet) {
            stringList.add(jsonObject.toString());
        }
	
        List<String> responseMessage = stringList;
        return new MyResponse(responseMessage);
    }

    @PostMapping("/get/this")
    @ResponseStatus(HttpStatus.OK)
    public MyResponse processgetthis(@RequestBody MyRequest request) {
	
	String URL = "jdbc:postgresql://MyPostgreSQL:5432/";  
    	String USER = "GorSer";
    	String PASSWORD = "123456";
    	String DATABASE_NAME = "Files";

	PostgreSQLExample example = new PostgreSQLExample();
 	example.createDatabaseAndTable(URL, USER, PASSWORD, DATABASE_NAME );

	//example.insert();
	List<JSONObject> get_bd = example.get_this(URL, USER, PASSWORD, DATABASE_NAME, request.id);

	List<String> stringList = new ArrayList<>();

	for (JSONObject jsonObject : get_bd) {
            stringList.add(jsonObject.toString());
        }
	
        List<String> responseMessage = stringList;
        return new MyResponse(responseMessage);
    }

    @PostMapping("/insert/this")
    @ResponseStatus(HttpStatus.OK)
    public MyResponse processinsertthis(@RequestBody MyRequest request) {

	String URL = "jdbc:postgresql://MyPostgreSQL:5432/"; 
    	String USER = "GorSer";
    	String PASSWORD = "123456";
    	String DATABASE_NAME = "Files";

	PostgreSQLExample example = new PostgreSQLExample();
 	example.createDatabaseAndTable(URL, USER, PASSWORD, DATABASE_NAME );

	String dateString = request.creation_date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	Date sqlDate = null;
        
        try {
            // Преобразуем строку в LocalDate
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            // Преобразуем LocalDate в java.sql.Date
            sqlDate = Date.valueOf(localDate);
            //System.out.println("Преобразованная дата SQL: " + sqlDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

	byte[] bytefile = request.file.getBytes(StandardCharsets.UTF_8);

	List<JSONObject> get_bd = example.insertUser(URL, USER, PASSWORD, DATABASE_NAME, bytefile, request.title, request.description, sqlDate);

	List<String> stringList = new ArrayList<>();

	for (JSONObject jsonObject : get_bd) {
            stringList.add(jsonObject.toString());
        }
	
	
        List<String> responseMessage = stringList;
        return new MyResponse(responseMessage);
    }


}