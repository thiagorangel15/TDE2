package com.empresa.projetoapi.service;

import com.empresa.projetoapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();

        User user1 = new User(1, "Maria", 23, "maria@gmail.com");
        User user2 = new User(2, "João", 22, "joao@gmail.com");
        User user3 = new User(3, "José", 25, "jose@gmail.com");
        User user4 = new User(4, "Pedro", 27, "pedro@gmail.com");
        User user5 = new User(5, "Paulo", 28, "paulo@gmail.com");

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }

    public Optional<User> getUser(Integer id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public List<User> getUserList() {
        return userList;
    }

    public User createUser(User user) {
        userList.add(user);
        return user;
    }

    public boolean updateUser(Integer id, User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getId() == id) {
                updatedUser.setId(id);
                userList.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(Integer id) {
        return userList.removeIf(user -> user.getId() == id);
    }
}
