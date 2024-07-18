package junit5.mockito;

import junit5.mockito.dao.UserDao;
import junit5.mockito.dto.User;
import junit5.mockito.enums.UserConst;
import junit5.mockito.provider.UserProvider;

import java.util.ArrayList;
import java.util.List;

public class UserServiceWithMockito {
    private final List<User> users = new ArrayList<>();
    private UserDao userDao;

//    public String exceptionNullMessage = "null value is illegal for userName or password";

    public UserServiceWithMockito(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean delete(Integer userId) {

        return userDao.delete(userId);
    }

 /*   public List<User> getAll() {
        return users;
    }*/

    public void add(UserConst userConst) {
        users.add(new UserProvider(userConst).provide());
    }

/*    public void add(UserConst userConst) {
        User user = new UserProvider(userConst).provide();
        users.add(user);
    }

    public void add(User... user) {
        users.addAll(Arrays.asList(user));
    }

    public Optional<User> login(String userName, String password) {
        if (userName == null || password == null) {
            throw new IllegalArgumentException(exceptionNullMessage);
        }

        System.out.println("users: " + Arrays.asList(users));
        return users.stream()
                .filter(user -> user.getUsername().equals(userName))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }*/


}
