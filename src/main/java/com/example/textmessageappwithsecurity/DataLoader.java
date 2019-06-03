//package com.example.textmessageappwithsecurity;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Autowired
//    MessageRepository messageRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//
//    @Override
//    public void run (String... strings) throws Exception{
//        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("ADMIN"));
//
//        Role adminRole = roleRepository.findByRole("ADMIN");
//        Role userRole = roleRepository.findByRole("USER");
//
//        User chau = new User ("chaunguyen0691@gmail.com", "123", "Chau", "Nguyen", true, "chau" );
//        chau.setPassword(userService.encode(chau.getPassword()));
//        userService.saveUser(chau);
//
//        User admin = new User("admin@admin.com", "password", "Admin", "User", true, "admin");
//        admin.setPassword(userService.encode(admin.getPassword()));
//        userService.saveAdmin(admin);
//
//        Message message = new Message("Portfolio","App text message project using  " +
//                "Java, Spring MVC,  Spring Boot, Entity Relationship, Cloudinary, HTML, CSS, Bootstrap Framework and Heroku",
//                LocalDate.now(),
//                "Chau Nguyen",
//                "https://res.cloudinary.com/ho-chi-minh-city/image/upload/v1558617709/shmq4ofenblveteiapit.jpg", admin);
//
//        messageRepository.save(message);
//
//
//
//    }
//
//}