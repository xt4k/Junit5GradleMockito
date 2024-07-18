package extension.model;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ConditionExtension implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        System.out.println("Custom extension execution condition");
        return System.getProperty("skip") != null
                ? ConditionEvaluationResult.disabled("test should be skipped") : ConditionEvaluationResult.enabled("enabled by default");

    }
}
