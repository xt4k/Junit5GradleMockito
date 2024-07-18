package junit5.mockito;

import junit5.mockito.dto.User;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class UserService {
    private final List<User> users = new ArrayList<>();
    public String exceptionNullMessage = "null value is illegal for userName or password";

    public List<User> getAll() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public void add(User... user) {
        users.addAll(Arrays.asList(user));
    }

    public Optional<User> login(String userName, String password) {
        if (userName== null||password==null) {
            throw new IllegalArgumentException(exceptionNullMessage);
        }

        System.out.println("users: "+ Arrays.asList(users));
        return users.stream()
                .filter(user -> user.getUsername().equals(userName))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    public Map<Integer, User> getAllConvertedById() {
        return  users.stream().collect(toMap(User::getId, Function.identity()));
    }
}
