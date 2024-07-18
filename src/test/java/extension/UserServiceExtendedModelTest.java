package extension;

import extension.model.GlobalExtension;
import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import paramresolver.UserServiceParameterResolver;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Tag("extended")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("User Service test-set with extended model.")
@ExtendWith({
        UserServiceParameterResolver.class,
        GlobalExtension.class
})
class UserServiceExtendedModelTest {
    private UserService userService;
    private static final User IVAN = new User(1, "Ivan", "3210");
    private static final User PETRO = new User(2, "Petro", "4567");

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

    @Test
    void usersConvertedToMapById() {
        Map<Integer, User> mapUsers = userService.getAllConvertedById();
        assertAll(
                () -> assertThat(mapUsers).containsKeys(IVAN.getId(), PETRO.getId()),
                () -> assertThat(mapUsers).containsValues(IVAN, PETRO)
        );
    }

    @Nested
    @Tag("login")
    class LoginTest {

        @Test
        @DisplayName("Negative: Test if not-existed user cannot login")
        void loginFailIfUserNotExistTest() {
            Optional<User> mayBeUser = userService.login("dummy", "12333");
            assertTrue(mayBeUser.isEmpty());
        }

        @ParameterizedTest(name = "{arguments} - test")
        @DisplayName("parametrized with csv direct values")
        @CsvSource({
                "Ivan, 3210",
                "Petro, 4567"
        })
        void loginCsvDirectParametrizedTest(String userName, String password) {
            userService.add(IVAN, PETRO);
            var mayBeUser = userService.login(userName, password);

            assertThat(mayBeUser.get().getUsername()).isEqualTo(userName);
        }
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
