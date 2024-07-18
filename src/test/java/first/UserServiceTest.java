package first;

import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTest {
    private UserService userService;
    static final User IVAN = new User(1, "Ivan","3210") ;
    static final User PETRO =new User(2,"Petro","4567") ;

    @BeforeAll
    static void init(){
        System.out.println("before all ");

    }

    @BeforeEach
    void prepare(){
        System.out.println("before test method"+ this);
        userService = new UserService();
        userService.add(IVAN);
        userService.add(PETRO);

    }

    @Test
    void usersAreEmptyIfNoUserAddedTest() {
        var userService = new UserService();
        var users = userService.getAll();
        assertTrue(users.isEmpty(),() -> "Users list should be empty");
    }

    @Test
    void userSizeIfUserAddedTest(){
      //  var userService = new UserService();
       // userService.add(IVAN);
     //   userService.add(PETRO);

        var users = userService.getAll();
        assertEquals(2,users.size());
    }

    @Test
    @Tag("login")
    void loginSuccessIfUserExistTest() {
        Optional<User> mayBeUser = userService.login(IVAN.getUsername(), IVAN.getPassword());

        assertTrue(mayBeUser.isPresent());
        mayBeUser.ifPresent(user->assertEquals(IVAN,user));
    }

    @Test
    @Tag("login")
    void loginFailIfWrongPasswordTest() {
        Optional<User> mayBeUser = userService.login(IVAN.getUsername(), "bla");

        assertTrue(mayBeUser.isEmpty());
    }


    @Test
    @Tag("login")
    void loginFailIfUserNotExistTest() {
        Optional<User> mayBeUser = userService.login("dummy", "12333");
        assertTrue(mayBeUser.isEmpty());
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
