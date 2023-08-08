package com.example.newApp;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONObject;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MainController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    sqlClass example = new sqlClass();


    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String x) {
        x = example.Select("'one'");
           return new Greeting(counter.incrementAndGet(), String.format(template, x));
    }


    @PostMapping("/login")
    public User login(@RequestBody User user) {

        example.Insert(user.getLogin(), user.getEmail(), user.getLogin(), user.getPassword(), Date.valueOf(LocalDate.now().toString()));

        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDate(LocalDate.now().toString());
        try {
            new JSONObject(user.getLogin());
        } catch (JSONException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ResponseStatusException in testResponseStatusException");
        }
        return user;
    }
}
