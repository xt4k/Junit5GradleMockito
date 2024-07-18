package extension.model;

import junit5.mockito.UserService;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class PostProcessExtension  implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {

        System.out.println("CUSTOM after test processing op -> extension class:");

        var declaredFields = testInstance.getClass().getDeclaredFields();
        for (Field declaredField: declaredFields) {
            if (declaredField.isAnnotationPresent(Getter.class)) {
                declaredField.set(testInstance, new UserService());
                System.out.println(" some annotation detected");
            }

        }
    }
}
