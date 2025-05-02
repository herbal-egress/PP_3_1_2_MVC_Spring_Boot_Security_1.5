package mvc.spring.security.controllers;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import mvc.spring.security.repositories.RoleRepository;
import mvc.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller

public class UserController {
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // для заполнения формы при создании юзера.
    @GetMapping("/users/new") //
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("reg_page");
        mav.addObject("new_user", user);
// ищем все роли, указанные в форме и назначаем их новому юзеру:
        List<Role> roles = roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PostMapping("/users/save")
    public String performRegistration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }
        userService.register(user);
        return "redirect:/login";
    }

    // для заполнения формы при изменении юзера
    @GetMapping("/users/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
        User user = (User) userService.loadUserById(id);
        ModelAndView mav = new ModelAndView("reg_page");
        mav.addObject("new_user", user);
//  добавление в модель коллекции объектов Role:
        List<Role> roles = roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }
}
