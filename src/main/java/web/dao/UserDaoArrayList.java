package web.dao;

import org.springframework.stereotype.Component;
import web.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoArrayList implements UserDao {
    private static int ID_COUNT;
    private List<User> userList;

    {
        userList = new ArrayList<>();

        userList.add(new User( ++ID_COUNT,"Иван", "Царевич", "car-tridevyatoe@carstvo.rus"));
        userList.add(new User( ++ID_COUNT,"Алёша", "Попович", "alesha-tridevyatoe@carstvo.rus"));
        userList.add(new User( ++ID_COUNT,"Добрыня", "Никитыч", "dobrynya-tridevyatoe@carstvo.rus"));
    }

    public List<User> index() {
        return userList;
    }

    public User show(int id) {
        return userList.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public void save(User user) { user.setId(++ID_COUNT); userList.add(user); }

    public void update(int id, User updatedUser) {
        User userWillUpdated = show(id);
        userWillUpdated.setName(updatedUser.getName());
        userWillUpdated.setSurname(updatedUser.getSurname());
        userWillUpdated.setEmail(updatedUser.getEmail());
    }

    public void delete(int id) {
        userList.removeIf(u -> u.getId() == id);
    }
}
