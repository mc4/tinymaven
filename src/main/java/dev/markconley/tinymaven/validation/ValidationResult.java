package dev.markconley.tinymaven.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
	private final List<String> errors = new ArrayList<>();
	private final List<String> warnings = new ArrayList<>();

	public void addError(String error) {
		errors.add(error);
	}

	public void addWarning(String warning) {
		warnings.add(warning);
	}

	public List<String> getErrors() {
		return errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasWarnings() {
		return !warnings.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!errors.isEmpty()) {
			sb.append("Errors:\n");
			errors.forEach(e -> sb.append(" - ").append(e).append("\n"));
		}
		if (!warnings.isEmpty()) {
			sb.append("Warnings:\n");
			warnings.forEach(w -> sb.append(" - ").append(w).append("\n"));
		}
		return sb.toString();
	}
}
