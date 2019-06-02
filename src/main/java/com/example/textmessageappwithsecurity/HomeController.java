package com.example.textmessageappwithsecurity;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller

public class HomeController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @RequestParam("password") String pw){
        System.out.println("pw: " + pw);
        if(result.hasErrors()){
//            model.addAttribute("user", user);
            return "register";
        } else {
            user.encode(pw);
            userService.saveUser(user);
            model.addAttribute("message", "New User Account Created");
        }
        return "login";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        User myuser = ((CustomerUserDetails)
                ((UsernamePasswordAuthenticationToken) principal)
                        .getPrincipal())
                .getUser();
        model.addAttribute("myuser", myuser);
        return "secure";
    }
    
    @RequestMapping("/")
    public String base(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        if(userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
            model.addAttribute("user", userService.getUser());
        }
        return "base";
    }

    @RequestMapping("/list")
    public String listMessage(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        if(userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
            model.addAttribute("user", userService.getUser());
        }
        return "list";
    }

    @GetMapping("/add")
    public String addMessage(Principal principal, Model model){
        model.addAttribute("user", userService.getUser());
        model.addAttribute("message", new Message());
        return "addMessage";

    }
//    @PostMapping("/process")
//    public String processMessage (@Valid Message message,
//                                  BindingResult result,
//                                  @RequestParam("file") MultipartFile file,
//                                  Model model
//                                  ) {
//        System.out.println("object =" + message);
//        if (result.hasErrors()) {
//            for (ObjectError e : result.getAllErrors()) {
//                System.out.println(e);
//            }
//        }
//        //if there is a picture path and file is empty then save message
//        if (message.getImage() != null && file.isEmpty()) {
//            messageRepository.save(message);
//            return "redirect:/addMessage";
//        }
//        if( file.isEmpty()){
//            model.addAttribute("user", userService.getUser());
//            return "addMessage";
//        }
//        else {
//
//        try {
//            Map uploadResult = cloudinaryConfig.upload(
//                    file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
//            message.setImage(uploadResult.get("url").toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "redirect:/list";
//        }
//    }
//
//            message.setUser(userService.getUser());
//            messageRepository.save(message);
//            return "redirect:/";
//        }

    @PostMapping("/process")
    public String processMessage(@RequestParam("file") MultipartFile file, @Valid Message message, BindingResult result, Model model){
        model.addAttribute("user", userService.getUser());

        if(result.hasErrors()){
            return "addMessage";
        }
        if(file.isEmpty()){
            return "redirect:/add";
        }
        try{
            Map uploadResult = cloudinaryConfig.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            message.setImage(uploadResult.get("url").toString());
            message.setUser(userService.getUser());
            messageRepository.save(message);
        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/list";
        }
        model.addAttribute("message", message);
        return "redirect:/list";
    }



    @RequestMapping("/detail/{id}")
    public String detailMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
            model.addAttribute("user", userService.getUser());

        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        model.addAttribute("user", userService.getUser());


        return "addMessage";
    }


    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String getAbout(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "about";
    }
//    @RequestMapping("/secure")
//    public String secure(Model model) {
//
//        model.addAttribute("user", userService.getUser());
//        return "secure";
//    }

    @RequestMapping("/term")
    public String term (){
        return "term";
    }
}

