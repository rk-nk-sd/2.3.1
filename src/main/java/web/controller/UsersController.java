package web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.model.User;

import javax.naming.Binding;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDao userDao;
    @Autowired
    public UsersController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public String index(Model model) {
        //Получим список пользователей из DAO и передадим в представление
        model.addAttribute("users", userDao.index());
        return "users/showAll";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получим одного пользователя по id из DAO и передадим на представление
        model.addAttribute("user", userDao.show(id));
        return "users/show";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        //Вернет html форму для создания нового пользователя
        model.addAttribute("user", new User());
        return "users/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "users/new";
        userDao.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.show(id));
        return "users/edit";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "users/edit";
        userDao.update(id, user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/users";
    }
}
