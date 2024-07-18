package mockito;

import junit5.mockito.UserServiceWithMockito;
import junit5.mockito.dao.UserDao;
import junit5.mockito.enums.UserConst;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static junit5.mockito.enums.UserConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.RepeatedTest.LONG_DISPLAY_NAME;

@Nested
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("parametrized")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("User Service test-set with parametrization.")
class UserServiceWithBDDMockitoTest {
    private UserServiceWithMockito userService;
    private UserDao userDao;

    @BeforeAll
    static void init() {
        System.out.println("before all ");
    }

    @BeforeEach
    void prepare() {
        System.out.println("Default `BEFORE EACH` test methods: " + this);
        userDao = Mockito.spy(new UserDao());
        //userDao = Mockito.mock(UserDao.class);
        this.userService = new UserServiceWithMockito(userDao);
    }

    @Test
    @DisplayName("Check that delete user several times return error")
    void deleteUserSeveralTimesReturnErrorTest() {
        userService.add(IVAN);

        Integer id = IVAN.getUserId();


        Mockito.doReturn(true).when(userDao).delete(IVAN.getUserId());
        var isDeleted = userService.delete(id);
        System.out.printf("user '%s' is deleted: `%s`%n", IVAN, isDeleted);

        System.out.println(userService.delete(id));
        System.out.println(userService.delete(id));


        Mockito.verify(userDao, Mockito.times(3)).delete(id);

        assertThat(isDeleted).isTrue();
    }

    @Test
    @DisplayName("Check delete user argument with ArgumentCaptor")
    void deleteUserByArgumentCaptureTest() {
        Integer id = VASKO.getUserId();
        Mockito.doReturn(true).when(userDao).delete(Mockito.any());
        userService.add(VASKO);

        userService.delete(id);
        var argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(userDao, Mockito.times(1)).delete(argumentCaptor.capture());

        System.out.printf("user '%s'`%n", VASKO);

        System.out.println("getValue: "+argumentCaptor.getValue());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }


    @Test
    @DisplayName("Check if user can be deleted with Mockito")
    void shouldDeleteExistedUserTest() {
        Mockito.doReturn(true)
                .when(userDao)
                .delete(IVAN.getUserId());

        var isDeleted = userService.delete(IVAN.getUserId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    @DisplayName("User can be deleted with BDDMockito given-then")
    void shouldDeleteExistedUserBDDMockitoGivenThenTest() {
        userService.add(IVAN);
        BDDMockito.given(userDao.delete(IVAN.getUserId())).willReturn(true);

        var isDeleted = userService.delete(IVAN.getUserId());
        assertThat(isDeleted).isTrue();
    }


    @Test
    @DisplayName("User can be deleted with BDDMockito then-given")
    void shouldDeleteExistedUserBDDMockitoThenGivenTest() {
        BDDMockito.willReturn(true).given(userDao).delete(IVAN.getUserId());

        var isDeleted = userService.delete(IVAN.getUserId());
        assertThat(isDeleted).isTrue();
    }


    public static Stream<Arguments> getArgumentsForDelete() {
        return Stream.of(
                Arguments.of(Optional.of(IVAN), true),
                Arguments.of(Optional.of(PETRO), false),
                Arguments.of(Optional.of(VASKO), false)
        );
    }

    @Test
    @DisplayName("Check if user can be deleted with Mockito")
    void shouldNotDeleteNotExistedUser() {
        Mockito.doReturn(true)
                .when(userDao)
                .delete(IVAN.getUserId());

        var isDeleted = userService.delete(UserConst.PETRO.getUserId());
        assertThat(isDeleted).isFalse();
    }

    @Nested
    @Tag("login")
    @Timeout(value = 255, unit = TimeUnit.NANOSECONDS)
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
        @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
        void exampleOfTimeOutAnnotatedTest() {
            System.out.println("some test with timeout annotation for test method");
        }
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
