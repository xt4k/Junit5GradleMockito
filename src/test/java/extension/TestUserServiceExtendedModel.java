package extension;

import extension.model.ConditionExtension;
import extension.model.PostProcessExtension;
import extension.model.ThrowableExtension;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import paramresolver.UserServiceParameterResolver;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Tag("test_ext")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("User Service test-set with extended model.")
@ExtendWith({
        UserServiceParameterResolver.class,
        PostProcessExtension.class,
        ConditionExtension.class,
        ThrowableExtension.class
})
class TestUserServiceExtendedModel extends TestBase {


    @Test
    void testThatThrowException() {
        RuntimeException exception = new RuntimeException("runtime exception");
        System.out.println("test will throw exception: " + exception.getMessage());
        throw exception;
    }

    @Test
    void testThatThrowIOException() throws IOException {
        IOException exception = new IOException("IO exception");
        System.out.println("test will throw exception: " + exception.getMessage());
        throw exception;
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

}
