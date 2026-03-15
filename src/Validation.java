import java.math.BigDecimal;
import java.time.LocalDate;

final class Validation {
    private Validation() {
    }

    static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null.");
        }
        return value;
    }

    static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value;
    }

    static BigDecimal requireNonNegative(BigDecimal value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(fieldName + " must not be negative.");
        }
        return value;
    }

    static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must not be negative.");
        }
        return value;
    }

    static void requireDateOrder(LocalDate startDate, LocalDate endDate, String fieldName) {
        requireNonNull(startDate, fieldName + " start");
        requireNonNull(endDate, fieldName + " end");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(fieldName + " end date must not be earlier than start date.");
        }
    }

    static void requireSalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        if (maxSalary.compareTo(minSalary) < 0) {
            throw new IllegalArgumentException("maxSalary must be greater than or equal to minSalary.");
        }
    }
}
