package mvc.spring.security.controllers;

import mvc.spring.security.dao.UserDbDAO;
import mvc.spring.security.entities.User;
import mvc.spring.security.model.UserDb;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserDbController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDbDAO userDbDAO;

    @RequestMapping("/users") // http://localhost:8080/users
    public String pageForAll(Model model) {
        model.addAttribute("startkey", userDbDAO.allUsers());
        return "usersview/startpage";
    }

    @GetMapping("/user") // защищённый адрес. Только авторизованные // http://localhost:8080/user
    public String pageForAuthenticatedUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("auth_name", user);
        return "usersview/user_page";
    }

    //возвращает всех юзеров
    @RequestMapping("/users/admin") // http://localhost:8080/users/admin
    public String allUsersDb(Model model) {
        model.addAttribute("alluserskey", userDbDAO.allUsers());
        return "usersview/allusers";
    }

    // возвращает страницу конкретного юзера по id:
    @GetMapping("/users/admin/id")
    public String userDbById(@RequestParam int id, Model model) {
        model.addAttribute("userByIdkey", userDbDAO.userById(id));
        return "usersview/userById";
    }

    // возвращает HTML-форму для заполнения полей при создании нового человека:
    @GetMapping("/users/admin/new")
    public String viewForNewUserDb(@ModelAttribute("newkey") UserDb userDb) {
        return "usersview/new";
    }

    // принимает POST-запрос, и ДОБАВЛЯЕТ нового юзера в БД с помощью DAO:
    @PostMapping("/users/admin")
    public String createUserDb(@ModelAttribute("createkey") UserDb userDb) {
        userDbDAO.save(userDb);
        return "redirect:/users/admin";
    }

    // вызывается для подготовки редактирования юзера
    @GetMapping("/users/admin/edit/id")
    public String editUserDb(@RequestParam int id, Model model) {
        model.addAttribute("editkey", userDbDAO.userById(id));
        return "usersview/edit";
    }

    // отправляет запрос в БД для записи отредактированного юзера:
    @PatchMapping("/users/admin/id")
    public String updateUserDb(@RequestParam int id, @ModelAttribute("updatekey") UserDb userDb) {
        userDbDAO.update(id, userDb);
        return "redirect:/users/admin";
    }

    // удаление человека по id на странице /users/admin/id?id=
    @DeleteMapping("/users/admin/id")
    public String deleteUserDb(@RequestParam int id) {
        userDbDAO.delete(id);
        return "redirect:/users/admin";
    }
}