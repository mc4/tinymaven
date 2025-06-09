package dev.markconley.tinymaven.validation;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult validate(T t);

    default Validator<T> and(Validator<T> other) {
        return t -> {
            ValidationResult result = this.validate(t);
            ValidationResult otherResult = other.validate(t);
            result.getErrors().addAll(otherResult.getErrors());
            result.getWarnings().addAll(otherResult.getWarnings());
            return result;
        };
    }
    
}
