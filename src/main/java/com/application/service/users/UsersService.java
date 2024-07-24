package com.application.service.users;

import com.application.controller.output.ui.AuthorizationView;
import com.application.model.users.Personal;
import com.application.model.users.Users;
import com.application.repository.users.PersonalRepository;
import com.application.repository.users.UsersRepository;
import com.application.util.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonalRepository personalRepository;

    PasswordUtil passwordUtil = new PasswordUtil();

    public String authorizeUser(String username, String password) {
        Users user = usersRepository.findByUsername(username);
        if (user != null && passwordUtil.verifyPassword(password, user.getPassword()) && !user.getDeleted()) {
            Personal personal = personalRepository.findByUser(user);
            if (personal != null) {
                AuthorizationView.setFullusername(personal.getUser().getSurname()+" "+personal.getUser().getName()+" "+personal.getUser().getPatronymic());
                return personal.getPost(); // Возвращаем роль пользователя
            } else {
                return "User has no role";
            }
        } else {
            return "Invalid username or password";
        }
    }

    public void addUser(Users user){
        usersRepository.save(user);
    }

    public Users findUserById(Long id) {
        return usersRepository.findUsersById(id);
    }

}