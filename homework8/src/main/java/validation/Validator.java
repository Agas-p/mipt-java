package validation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Validator {
    private Validator() {
    }

    public static ValidationResult validate(Object object) {
        Objects.requireNonNull(object);

        List<ValidationError> errors = new ArrayList<>();
        Class<?> currentClass = object.getClass();

        while (currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }
                validateField(object, field, errors);
            }

            currentClass = currentClass.getSuperclass();
        }

        return new ValidationResult(errors);
    }

    private static void validateField(Object object, Field field, List<ValidationError> errors) {
        Object value = readField(object, field);
        validateNotNull(field, value, errors);
        validateSize(field, value, errors);
        validateRange(field, value, errors);
        validateEmail(field, value, errors);
    }

    private static Object readField(Object object, Field field) {
        try {
            if (!field.trySetAccessible()) {
                throw new ValidationConfigurationException(fieldMessage(field));
            }
            return field.get(object);
        } catch (IllegalAccessException exception) {
            throw new ValidationConfigurationException(fieldMessage(field), exception);
        } catch (RuntimeException exception) {
            if (exception instanceof ValidationConfigurationException) {
                throw exception;
            }
            throw new ValidationConfigurationException(fieldMessage(field), exception);
        }
    }

    private static void validateNotNull(Field field, Object value, List<ValidationError> errors) {
        NotNull annotation = field.getAnnotation(NotNull.class);
        if (annotation == null) {
            return;
        }

        if (value == null) {
            errors.add(new ValidationError(
                    field.getName(),
                    "NotNull",
                    annotation.message()
            ));
        }
    }

    private static void validateSize(Field field, Object value, List<ValidationError> errors) {
        Size annotation = field.getAnnotation(Size.class);
        if (annotation == null) {
            return;
        }

        if (annotation.min() < 0 || annotation.min() > annotation.max()) {
            throw new ValidationConfigurationException(
                    "Invalid @Size configuration for field "
                            + field.getDeclaringClass().getName()
                            + "."
                            + field.getName()
            );
        }

        if (!CharSequence.class.isAssignableFrom(field.getType())) {
            throw new ValidationConfigurationException(
                    "@Size cannot be used with field "
                            + field.getDeclaringClass().getName()
                            + "."
                            + field.getName()
            );
        }

        if (value == null) {
            return;
        }

        CharSequence text = (CharSequence) value;
        int length = text.length();

        if (length < annotation.min() || length > annotation.max()) {
            errors.add(new ValidationError(
                    field.getName(),
                    "Size",
                    annotation.message()
            ));
        }
    }

    private static void validateRange(Field field, Object value, List<ValidationError> errors) {
        Range annotation = field.getAnnotation(Range.class);
        if (annotation == null) {
            return;
        }

        double min = annotation.min();
        double max = annotation.max();

        if (Double.isNaN(min)
                || Double.isNaN(max)
                || Double.isInfinite(min)
                || Double.isInfinite(max)
                || min > max) {
            throw new ValidationConfigurationException(
                    "Invalid @Range configuration for field "
                            + field.getDeclaringClass().getName()
                            + "."
                            + field.getName()
            );
        }

        if (!isNumericType(field.getType())) {
            throw new ValidationConfigurationException(
                    "@Range cannot be used with field "
                            + field.getDeclaringClass().getName()
                            + "."
                            + field.getName()
            );
        }

        if (value == null) {
            return;
        }

        Number number = (Number) value;
        double numberValue = number.doubleValue();

        if (Double.isNaN(numberValue) || numberValue < min || numberValue > max) {
            errors.add(new ValidationError(
                    field.getName(),
                    "Range",
                    annotation.message()
            ));
        }
    }

    private static void validateEmail(Field field, Object value, List<ValidationError> errors) {
        Email annotation = field.getAnnotation(Email.class);
        if (annotation == null) {
            return;
        }

        if (!CharSequence.class.isAssignableFrom(field.getType())) {
            throw new ValidationConfigurationException(
                    "@Email cannot be used with field "
                            + field.getDeclaringClass().getName()
                            + "."
                            + field.getName()
            );
        }

        if (value == null) {
            return;
        }

        String email = value.toString();

        if (!isValidEmail(email)) {
            errors.add(new ValidationError(
                    field.getName(),
                    "Email",
                    annotation.message()
            ));
        }
    }

    private static boolean isNumericType(Class<?> type) {
        if (Number.class.isAssignableFrom(type)) {
            return true;
        }

        return type == byte.class
                || type == short.class
                || type == int.class
                || type == long.class
                || type == float.class
                || type == double.class;
    }

    private static boolean isValidEmail(String email) {
        if (email.isBlank()) {
            return false;
        }

        for (int i = 0; i < email.length(); i++) {
            if (Character.isWhitespace(email.charAt(i))) {
                return false;
            }
        }

        int atIndex = email.indexOf('@');

        if (atIndex <= 0) {
            return false;
        }

        if (atIndex != email.lastIndexOf('@')) {
            return false;
        }

        if (atIndex == email.length() - 1) {
            return false;
        }

        int dotIndex = email.indexOf('.', atIndex + 1);

        return dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }

    private static String fieldMessage(Field field) {
        return "Cannot access field "
                + field.getDeclaringClass().getName()
                + "."
                + field.getName();
    }
}
