package first;

import junit5.mockito.UserService;
import junit5.mockito.dto.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.RepeatedTest.LONG_DISPLAY_NAME;

@Nested
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("parametrized")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("User Service test-set with parametrization.")
class UserServiceParametrizedTest {
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
        this.userService = new UserService();
        userService.add(IVAN);
        userService.add(PETRO);

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
   // @Timeout(value = 255, unit = TimeUnit.NANOSECONDS)
    class LoginTest {

        @Test()
        @Disabled("this is an example of flaky test.")
        @DisplayName("example of disabled test")
        @Tag("flaky, disabled")
        void exampleOfFlakyTest() {
            System.out.println("some flaky test operations");
        }


        @RepeatedTest(value = 5, name = LONG_DISPLAY_NAME)
        @DisplayName("example of repeatable test")
        @Tag("flaky, repeatable")
        void exampleOfRepeatableTest(RepetitionInfo repetitionInfo) {
            System.out.printf("printed %s of %s%n", repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions());
            System.out.println("some repeatable test operation");
        }


        @Test
        @DisplayName("example of test with timeout (as assert)")
        @Tag("timeout")
        void exampleOfTimeOutAssertTest() {
            var result = assertTimeout(Duration.ofMillis(200L), () -> {
                Thread.sleep(300L);
                return userService.login("", IVAN.getPassword());

            });
            System.out.println("some test with timeout operation");
        }

        @Test
        @DisplayName("example of test with timeout with different threads")
        @Tag("timeout")
        void exampleOfTimeThreadsTest() {
            System.out.println("current thread: "+ Thread.currentThread().getName());
            var result = assertTimeoutPreemptively(Duration.ofMillis(200L), () -> {
                System.out.println("thread for a waiting: "+ Thread.currentThread().getName());
                Thread.sleep(300L);
                return userService.login("", IVAN.getPassword());

            });
            System.out.println("some test with different threads");
        }



        @Test
        @DisplayName("example of test with timeout (as assert)")
        @Tag("timeout")
        @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
        void exampleOfTimeOutAnnotatedTest() {
            System.out.println("some test with timeout annotation for test method");
        }



        @Test
        @DisplayName("Positive: Test if existed user may login")
        void loginSuccessIfUserExistTest() {
            Optional<User> mayBeUser = userService.login(IVAN.getUsername(), IVAN.getPassword());

            assertTrue(mayBeUser.isPresent());
            mayBeUser.ifPresent(user -> assertEquals(IVAN, user));
        }

        @Test
        @DisplayName("Negative: Test if user with wrong pass cannot login")
        void loginFailIfWrongPasswordTest() {
            Optional<User> mayBeUser = userService.login(IVAN.getUsername(), "bla");

            assertTrue(mayBeUser.isEmpty());
        }


        @Test
        @DisplayName("Negative: Test if not-existed user cannot login")
        void loginFailIfUserNotExistTest() {
            Optional<User> mayBeUser = userService.login("dummy", "12333");
            assertTrue(mayBeUser.isEmpty());
        }

        @ParameterizedTest(name = "{index}--{arguments}")
        //@ArgumentsSource()
        //@NullSource
        //@EmptySource
        //@NullAndEmptySource
        //  @ValueSource(strings = {
        //  "Ivan", "Petro"
        //  })
        //@EnumSource
        @MethodSource("first.UserServiceParametrizedTest#getArgumentsForLoginTest")
        void loginParametrizedTest(String userName, String password, Optional<User> user) {
            userService.add(IVAN, PETRO);

            var mayBeUser = userService.login(userName, password);

            assertThat(mayBeUser).isEqualTo(user);
        }
    }

    @ParameterizedTest(name = "{argumentsWithNames} - test")
    @DisplayName("parametrized with csv file")
    @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 1)
    void loginCsvFileParametrizedTest(String userName, String password) {
        userService.add(IVAN, PETRO);

        var mayBeUser = userService.login(userName, password);

        assertThat(mayBeUser.get().getUsername()).isEqualTo(userName);
    }

    @ParameterizedTest(name = "{arguments} - test")
    @DisplayName("parametrized with csv direct values")
    @CsvSource({
            "Ivan, 3210",
            "Petro, 4567"
    })
    void loginCsvDirectParametrizedTest(String userName, String password) {
        userService.add(IVAN, PETRO);

        System.out.println("username/pass: %s/%s".formatted(userName, password));
        var mayBeUser = userService.login(userName, password);

        assertThat(mayBeUser.get().getUsername()).isEqualTo(userName);
    }


    public static Stream<Arguments> getArgumentsForLoginTest() {
        return Stream.of(
                Arguments.of("Ivan", "3210", Optional.of(IVAN)),
                Arguments.of("Petro", "4567", Optional.of(PETRO)),
                Arguments.of("", "3210", Optional.empty()),
                Arguments.of("Daria", "", Optional.empty())
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
