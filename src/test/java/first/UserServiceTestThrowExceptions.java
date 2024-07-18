package first;

import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTestThrowExceptions {
    private UserService userService;
    private static final User IVAN = new User(1, "Ivan", "3210");
    private static final User PETRO = new User(2, "Petro", "4567");

    @BeforeAll
    static void init() {
        System.out.println("before all ");

    }

    @BeforeEach
    void prepare() {
        System.out.println("before test method" + this);
        userService = new UserService();
        userService.add(IVAN,PETRO);
    }

    @Test
    void usersAreEmptyIfNoUserAddedTest() {
        var userService = new UserService();
        var users = userService.getAll();
        assertThat(users.isEmpty())
                .as("Users list should be empty")
                .isTrue();

    }

    @Test
    void userSizeIfUserAddedTest() {
        var users = userService.getAll();
        assertThat(users.size())
                .isEqualTo(2);
    }

    @Test
    @Tag("login")
    void loginSuccessIfUserExistTest() {
        Optional<User> mayBeUser = userService.login(IVAN.getUsername(), IVAN.getPassword());

        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get()).isEqualTo(IVAN);
    }

    @Test
    @Tag("login")
    void loginFailIfWrongPasswordTest() {
        Optional<User> mayBeUser = userService.login(IVAN.getUsername(), "bla");
        assertThat(mayBeUser).isNotPresent();
    }


    @Test
    @Tag("login")
    void loginFailIfUserNotExistTest() {
        Optional<User> mayBeUser = userService.login("dummy", "12333");
        assertThat(mayBeUser)
                .isEmpty();
    }


    @Test
    void usersConvertedToMapById() {
       Map<Integer,User> mapUsers = userService.getAllConvertedById();

        assertAll(
               () ->  Assertions.assertThat(mapUsers).containsKeys(IVAN.getId(), PETRO.getId()),
               () ->  Assertions.assertThat(mapUsers).containsValues(IVAN, PETRO)
        );
    }

    @Test
    @Tag("login")
    void throwExceptionsIfUsernameOrPasswordIsNull() {
        assertAll(
                ()-> {
                    var exception =  assertThrows(IllegalArgumentException.class,() ->  userService.login(null, ""));
                            assertThat(exception.getMessage())
                                    .isEqualTo(userService.exceptionNullMessage);

                },
                ()-> {
                    var exception =  assertThrows(IllegalArgumentException.class,() ->  userService.login("", null));
                    assertThat(exception.getMessage())
                            .isEqualTo(userService.exceptionNullMessage);

                }
        );
    }


    @AfterEach
    void afterTest() {
        System.out.println("after test method: " + this);
    }

    @AfterAll
    static void tearDown() {
        System.out.println("after all ");
    }

}
