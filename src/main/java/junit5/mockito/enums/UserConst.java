package junit5.mockito.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserConst {
    IVAN(1, "Ivan", "3210"),
    PETRO(2, "Petro", "4567"),
    VASKO(999, "VASKO_DA_GAMA", "0000");

    private final Integer userId;
    private final String userName;
    private final String password;

}
