package com.example.textmessageappwithsecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;



    @Override
    public void run (String... strings) throws Exception{
        Message message = new Message("Portfolio","App text message project using\n  " +
                "Java, Spring Boot, Cloudinary, HTML, CSS, Bootstrap Framework and Heroku",
                LocalDate.now(),
                "Chau Nguyen",
                "https://res.cloudinary.com/ho-chi-minh-city/image/upload/v1558617709/shmq4ofenblveteiapit.jpg");
        messageRepository.save(message);

        User chau = new User ("chaunguyen0691@gmail.com", "123", "Chau", "Nguyen", true, "chau" );
        chau.setPassword(userService.encode(chau.getPassword()));
        userService.saveUser(chau);
    }

}