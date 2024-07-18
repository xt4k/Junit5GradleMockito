package extension;

import extension.model.GlobalExtension;
import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Base Test.")
@ExtendWith({
        GlobalExtension.class
})
abstract class TestBase {
    protected UserService userService;
    protected static final User IVAN = new User(1, "Ivan", "3210");
    protected static final User PETRO = new User(2, "Petro", "4567");

    @BeforeAll
    static void init() {
        System.out.println("default before all in test-set");
    }

    @BeforeEach
    void prepare() {
        System.out.println("default before each test method" + this);
        this.userService = new UserService();
        userService.add(IVAN,PETRO);
    }

    @AfterEach
    void afterTest() {
        System.out.println("default after test method: " + this);
    }

    @AfterAll
    static void tearDown() {
        System.out.println("default after all ");
    }
}
