package launchcode.org.ballersGuide.controllers;

import launchcode.org.ballersGuide.models.Role;
import launchcode.org.ballersGuide.models.User;
import launchcode.org.ballersGuide.models.data.RoleDao;
import launchcode.org.ballersGuide.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.MessageDigest;

import java.util.Random;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", "User");

        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "User");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleDao.findAll());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Errors");
            return "user/add";
        }

        if(!exist(newUser))
        {
            Role role = roleDao.findOne(9);
            newUser.setRole(role);
            Random random = new Random();
            newUser.setSalt(random.nextInt());
            newUser.setPassword(encodePassword(newUser.getPassword(), newUser.getSalt()));
            userDao.save(newUser);
            return "redirect:/user";
        }
        else
        {
            model.addAttribute("title", "Email already in use");
            return "user/add";
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("title", "User");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleDao.findAll());
        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model,
                      @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Errors");
            return "user/register";
        }

        if(!exist(newUser))
        {
            Role role = roleDao.findOne(9);
            newUser.setRole(role);
            Random random = new Random();
            newUser.setSalt(random.nextInt());
            newUser.setPassword(encodePassword(newUser.getPassword(), newUser.getSalt()));
            userDao.save(newUser);
            return "redirect:/user/log-in";
        }
        else
        {
            model.addAttribute("title", "Email already in use");
            return "user/register";
        }
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", "Remove User");
        return "user/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] userIds) {

        for (int userId : userIds) {
            userDao.delete(userId);
        }

        return "redirect:/user";
    }

    @RequestMapping(value = "edit/{userId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int userId) {

        model.addAttribute("title", "Edit User");
        model.addAttribute("user", userDao.findOne(userId));
        model.addAttribute("roles", roleDao.findAll());
        return "user/edit";
    }

    @RequestMapping(value = "edit/{userId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int userId,
                                  @ModelAttribute  @Valid User newUser,
                                  @RequestParam int roleId,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Position");
            return "user/edit";
        }

        User editedUser = userDao.findOne(userId);
        editedUser.setName(newUser.getName());
        editedUser.setLastName(newUser.getLastName());
        editedUser.setEmail(newUser.getEmail());
        editedUser.setRole(roleDao.findOne(roleId));
        userDao.save(editedUser);

        return "redirect:/user";
    }

    private String encodePassword(String password, int salt) {
        try {
            password+=salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Boolean exist(User newUser){
        Iterable<User> users = userDao.findAll();
        for(User user:users)
            if(user.getEmail().equals(newUser.getEmail())){
                return true;
                }
        return false;
    }

    private User getUser(User newUser){
        Iterable<User> users = userDao.findAll();
        for(User user:users)
            if(user.getEmail().equals(newUser.getEmail())){
                return user;
            }
        return null;
    }

    @RequestMapping(value = "log-in", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "user/log-in";
    }

    @RequestMapping(value = "log-in", method = RequestMethod.POST)
    public String login(Model model,
                      @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Errors");
            return "user/log-in";
        }

        if(exist(newUser))
        {
            User loginUser = getUser(newUser);
            if(loginUser.getPassword().equals(encodePassword(newUser.getPassword(),loginUser.getSalt()))) {
                if(loginUser.getRole().getId() == 7)
                {
                    return "redirect:/player/index-admin";
                }
                else
                {
                    return "redirect:/player";
                }
            }
        }
            model.addAttribute("title", "Email does not exist");
            return "redirect:/user/register";
    }
}
