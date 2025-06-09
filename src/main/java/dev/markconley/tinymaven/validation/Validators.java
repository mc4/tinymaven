package dev.markconley.tinymaven.validation;

import java.util.function.Function;

public class Validators {

	public static Validator<String> notBlank(String fieldName) {
		return value -> {
			ValidationResult result = new ValidationResult();
			if (value == null || value.trim().isEmpty()) {
				result.addError(fieldName + " is required and cannot be blank.");
			}
			return result;
		};
	}

	public static <T> Validator<T> notNull(String fieldName, Function<T, Object> extractor) {
		return t -> {
			ValidationResult result = new ValidationResult();
			if (extractor.apply(t) == null) {
				result.addError(fieldName + " is required.");
			}
			return result;
		};
	}
	
}
