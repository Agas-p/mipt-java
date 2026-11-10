package validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    @Test
    void validObjectHasNoErrors() {
        TestUser user = new TestUser("Agasi", "agas@mail.ru", 20);
        ValidationResult result = Validator.validate(user);

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void notNullFindsError() {
        ValidationResult result = Validator.validate(new NotNullModel(null));

        assertFalse(result.isValid());
        assertTrue(hasError(result, "value", "NotNull"));
    }

    @Test
    void sizeFindsError() {
        ValidationResult result = Validator.validate(new SizeModel("A"));

        assertTrue(hasError(result, "value", "Size"));
    }

    @Test
    void rangeFindsError() {
        ValidationResult result = Validator.validate(new RangeModel(150));

        assertTrue(hasError(result, "value", "Range"));
    }

    @Test
    void emailFindsError() {
        ValidationResult result = Validator.validate(new EmailModel("wrong-email"));

        assertTrue(hasError(result, "value", "Email"));
    }

    @Test
    void collectsSeveralErrors() {
        TestUser user = new TestUser("A", "wrong-email", 200);
        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertEquals(3, result.getErrors().size());
    }

    @Test
    void sizeBoundaryValuesAreValid() {
        ValidationResult minResult = Validator.validate(new SizeModel("AB"));
        ValidationResult maxResult = Validator.validate(new SizeModel("ABCDE"));

        assertTrue(minResult.isValid());
        assertTrue(maxResult.isValid());
    }

    @Test
    void rangeBoundaryValuesAreValid() {
        ValidationResult minResult = Validator.validate(new RangeModel(0));
        ValidationResult maxResult = Validator.validate(new RangeModel(100));

        assertTrue(minResult.isValid());
        assertTrue(maxResult.isValid());
    }

    @Test
    void nullObjectThrowsException() {
        assertThrows(NullPointerException.class, () -> Validator.validate(null));
    }

    @Test
    void invalidSizeConfigurationThrowsException() {
        assertThrows(
                ValidationConfigurationException.class,
                () -> Validator.validate(new InvalidSizeModel("text"))
        );
    }

    @Test
    void unsupportedTypeThrowsException() {
        assertThrows(
                ValidationConfigurationException.class,
                () -> Validator.validate(new InvalidEmailModel(123))
        );
    }

    @Test
    void inheritedPrivateFieldIsValidated() {
        ValidationResult result = Validator.validate(new ChildModel());

        assertTrue(hasError(result, "parentValue", "NotNull"));
    }

    @Test
    void staticFieldsAreIgnored() {
        ValidationResult result = Validator.validate(new StaticModel());

        assertTrue(result.isValid());
    }

    @Test
    void errorsListCannotBeChanged() {
        ValidationResult result = Validator.validate(new SizeModel("A"));
        List<ValidationError> errors = result.getErrors();

        assertThrows(UnsupportedOperationException.class, errors::clear);
    }

    private boolean hasError(
            ValidationResult result,
            String fieldName,
            String annotationName
    ) {
        for (ValidationError error : result.getErrors()) {
            if (error.getFieldName().equals(fieldName)
                    && error.getAnnotationName().equals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    private static class TestUser {
        @NotNull
        @Size(min = 2, max = 50)
        private String name;

        @NotNull
        @Email
        private String email;

        @Range(min = 0, max = 150)
        private Integer age;

        TestUser(String name, String email, Integer age) {
            this.name = name;
            this.email = email;
            this.age = age;
        }
    }

    private static class NotNullModel {
        @NotNull
        private String value;

        NotNullModel(String value) {
            this.value = value;
        }
    }

    private static class SizeModel {
        @Size(min = 2, max = 5)
        private String value;

        SizeModel(String value) {
            this.value = value;
        }
    }

    private static class RangeModel {
        @Range(min = 0, max = 100)
        private Integer value;

        RangeModel(Integer value) {
            this.value = value;
        }
    }

    private static class EmailModel {
        @Email
        private String value;

        EmailModel(String value) {
            this.value = value;
        }
    }

    private static class InvalidSizeModel {
        @Size(min = 10, max = 2)
        private String value;

        InvalidSizeModel(String value) {
            this.value = value;
        }
    }

    private static class InvalidEmailModel {
        @Email
        private Integer value;

        InvalidEmailModel(Integer value) {
            this.value = value;
        }
    }

    private static class ParentModel {
        @NotNull
        private String parentValue;
    }

    private static class ChildModel extends ParentModel {
    }

    private static class StaticModel {
        @NotNull
        private static String value;
    }
}
