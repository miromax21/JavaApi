package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Article;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.interfaces.SecurityService;
import net.proselyte.springsecurityapp.service.interfaces.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> createArticle(@RequestBody User user) {
        if(user == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        if (error != null) {
//            model.addAttribute("error", "Username or password is incorrect.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "Logged out successfully.");
//        }
       // User user1
        return  new ResponseEntity<User>(user, HttpStatus.OK);
      //  return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
