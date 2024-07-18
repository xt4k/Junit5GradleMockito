package junit5.mockito.provider;


import junit5.mockito.dto.User;
import junit5.mockito.enums.UserConst;

public class UserProvider implements Provider<User> {
    private User user;

    public UserProvider(UserConst userConst) {
        user = User.builder()
                .id(userConst.getUserId())
                .username(userConst.getUserName())
                .password(userConst.getPassword())
                .build();
    }

    @Override
    public User provide() {
        return user;
    }
}
