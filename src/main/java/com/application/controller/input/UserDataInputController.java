package com.application.controller.input;

import com.application.model.users.Personal;
import com.application.model.users.Users;
import com.application.service.users.PersonalService;
import com.application.service.users.UsersService;
import com.application.util.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class  UserDataInputController {

    @Autowired
    PersonalService personalService;

    @Autowired
    UsersService usersService;

    PasswordUtil passwordUtil = new PasswordUtil();

    public void addPersonal(String username, String surname, String name, String patronymic, String post,String password) {
        try {
            Users user = new Users();
            Personal personal = new Personal();
            user.setUsername(username);
            user.setPassword(passwordUtil.hashPassword(password));
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setActive(false);
            user.setBlocked(false);
            user.setDeleted(false);
            personal.setPost(post);
            personal.setUser(user);
            usersService.addUser(user);
            personalService.addUser(personal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public void changeUserData(Long id, String username, String surname, String name, String patronymic, String post,String password) {
        try {
            Users user = usersService.findUserById(id);
                user.setUsername(username);
                user.setSurname(surname);
                user.setName(name);
                if(!password.equals("")) {
                    user.setPassword(passwordUtil.hashPassword(password));
                }
                user.setPatronymic(patronymic);
                user.setActive(false);
                user.setBlocked(false);
                user.setDeleted(false);
                Personal personal = personalService.getPersonalByUser(user);
                personal.setPost(post);
                personal.setUser(user);
                usersService.addUser(user);
                personalService.addUser(personal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public void makeProfileDeleted(Long id) {
        try {
            Users user = usersService.findUserById(id);
                user.setUsername(null);
                user.setDeleted(true);
                usersService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
