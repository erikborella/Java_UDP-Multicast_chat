/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *
 * @author erik0
 */
public class JsonableMessage {
    private String date;
    private String time;
    private String username;
    private String message;

    public JsonableMessage(String date, String time, String username, String message) {
        this.date = date;
        this.time = time;
        this.username = username;
        this.message = message;
    }
    
    public JsonableMessage(String username, String message) {
        this.username = username;
        this.message = message;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date =  LocalDate.now().format(formatter);
        
        this.time = LocalTime.now().toString();
    }
    
    public static JsonableMessage fromJson(String json) {
        Gson gson = new Gson();
        JsonableMessage m = gson.fromJson(json, JsonableMessage.class);
        
        return m;
    }
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
    
}
