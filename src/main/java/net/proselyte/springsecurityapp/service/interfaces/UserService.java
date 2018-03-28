package net.proselyte.springsecurityapp.service.interfaces;

import net.proselyte.springsecurityapp.model.User;


public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
