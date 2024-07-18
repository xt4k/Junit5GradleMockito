package junit5.mockito.dao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoSpy extends UserDao {

    private final UserDao userDao;



    private Map<Integer,Boolean> answers = new HashMap<>();

    public UserDaoSpy(UserDao userDao) {
        this.userDao = userDao ;
    }
    //private Answer1<Integer,Boolean> answer;

    @Override
    public boolean delete(Integer userId) {
        return answers.getOrDefault(userId, false);
    }
}
