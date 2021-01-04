package web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDaoHibernateImpl;
import web.model.User;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDaoHibernateImpl userDaoHibernateImpl;
    @Autowired
    public UsersController(UserDaoHibernateImpl userDaoHibernateImpl) {
        this.userDaoHibernateImpl = userDaoHibernateImpl;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        //Получим список пользователей из DAO и передадим в представление
        model.addAttribute("users", userDaoHibernateImpl.index());
        return "users/showAll";
    }
    @GetMapping("/{id}")
    public String getCurrentUser(@PathVariable("id") int id, Model model) {
        //Получим одного пользователя по id из DAO и передадим на представление
        model.addAttribute("user", userDaoHibernateImpl.show(id));
        return "users/show";
    }
    @GetMapping("/new")
    public String addNewUser(Model model) {
        //Вернет html форму для создания нового пользователя
        model.addAttribute("user", new User());
        return "users/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "users/new";
        userDaoHibernateImpl.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDaoHibernateImpl.show(id));
        return "users/edit";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "users/edit";
        userDaoHibernateImpl.update(id, user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDaoHibernateImpl.delete(id);
        return "redirect:/users";
    }
}
