package org.example;


import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(String login, String password) {
        User user = new User(login, password);
        checkForNull(login);
        checkForNull(password);
        boolean userExist = this.userRepository
                .getAllUser()
                .stream()
                .anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("Пользователь уже существует");
        }
        this.userRepository.addUser(user);
    }

    public List<String> getAllLogin() {
        List<String> login = new ArrayList<>();
        for (User user : userRepository.getAllUser()) {
            String userLogin = user.getLogin();
            login.add(userLogin);
        }
        return login;
    }

    public boolean userByLoginAndPassword(String login, String password) {
        for (User user : userRepository.getAllUser()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void checkForNull (String string){
        if(string == null || string.isBlank()){
            throw new IllegalArgumentException("Логин и пароль должны быть определены");
        }
    }
}
