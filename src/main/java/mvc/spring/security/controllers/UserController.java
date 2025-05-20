package mvc.spring.security.controllers;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import mvc.spring.security.services.RoleService;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/start")
    public String pageForAll(Model model) {
        model.addAttribute("start_key", userService.findAllUser());
        return "usersview/start_page";
    }

    @RequestMapping("/admin")
    public String pageForAdmin(Model model) {
        model.addAttribute("allusers_key", userService.findAllUser());
        return "usersview/allusers";
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Model model, Principal principal) {
        User user = userService.findUserByName(principal.getName());
        model.addAttribute("authname_key", user);
        return "usersview/user_page";
    }

    @GetMapping("/admin/id")
    public String userById(@RequestParam int id, Model model) {
        model.addAttribute("userById_key", userService.findUserById(id));
        return "usersview/userById";
    }

    @GetMapping("/users/reg")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("reg_page");
        mav.addObject("newuser_key", user);
        List<Role> roles = roleService.findAllRole();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @GetMapping("/admin/edit/id")
    public String editUser(@RequestParam int id, Model model) {
        model.addAttribute("edit_key", userService.findUserById(id));
        List<Role> roles = roleService.findAllRole();
        model.addAttribute("allRoles", roles);
        return "usersview/edit";
    }

    @PostMapping("/users/save")
    public String performRegistration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/start";
        }
        userService.register(user);
        return "redirect:/start";
    }

    @PatchMapping("/admin/id")
    public String updateUser(@RequestParam int id, User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/id")
    public String deleteUser(@RequestParam int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}