package first;

import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tags({
                @Tag("fast"),
                @Tag("user")
        })
public class UserServicePerClassTest {
    private UserService userService;

    @BeforeAll
    void init(){
        System.out.println("before all "+this);

    }

    @BeforeEach
    void prepare(){
        System.out.println("before test method"+ this);
        userService = new UserService();

    }

    @Test
    void usersAreEmptyIfNoUserAddedTest() {
        var userService = new UserService();
        var users = userService.getAll();
        assertTrue(users.isEmpty(),() -> "Users list should be empty");
    }

    @Test
    void userSizeIfUserAddedTest(){
        var userService = new UserService();
        userService.add(new User(1,"Ivan","321"));
        userService.add(new User(2,"Peter", "234"));

        var users = userService.getAll();
        assertEquals(2,users.size());
    }


    @AfterEach
    void afterTest() {
        System.out.println("after test method: " + this);
    }

    @AfterAll
    void tearDown() {
        System.out.println("after all "+this);
    }

}
