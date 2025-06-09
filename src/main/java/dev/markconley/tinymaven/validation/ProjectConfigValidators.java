package dev.markconley.tinymaven.validation;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.config.ProjectConfig.Dependency;
import dev.markconley.tinymaven.config.ProjectConfig.Project;

public class ProjectConfigValidators {

	public static Validator<Project> projectValidator() {
		return project -> {
			ValidationResult result = new ValidationResult();
			if (project == null) {
				result.addError("Project section is missing.");
				return result;
			}

			Validator<String> nameValidator = Validators.notBlank("Project name");
			Validator<String> versionValidator = Validators.notBlank("Project version");
			Validator<String> mainClassValidator = mainClass -> {
				ValidationResult r = new ValidationResult();
				if (mainClass == null || mainClass.trim().isEmpty()) {
					r.addError("Main class is required.");
				} else if (!mainClass.contains(".")) {
					r.addError("Main class should be fully qualified (e.g., com.example.Main).");
				}
				return r;
			};

			result.getErrors().addAll(nameValidator.validate(project.getName()).getErrors());
			result.getErrors().addAll(versionValidator.validate(project.getVersion()).getErrors());
			result.getErrors().addAll(mainClassValidator.validate(project.getMainClass()).getErrors());

			String packaging = project.getPackaging();
			if (packaging != null && !(packaging.equalsIgnoreCase("jar") || packaging.equalsIgnoreCase("war"))) {
				result.addWarning("Unknown packaging type: '" + packaging + "'.");
			}

			return result;
		};
	}

	public static Validator<ProjectConfig> projectConfigValidator() {
		return config -> {
			ValidationResult result = new ValidationResult();

			ValidationResult projectResult = projectValidator().validate(config.getProject());
			result.getErrors().addAll(projectResult.getErrors());
			result.getWarnings().addAll(projectResult.getWarnings());

			if (isBlank(config.getSourceDirectory())) {
				result.addWarning("Source directory missing; defaulting to 'src/main/java'.");
			}
			if (isBlank(config.getOutputDirectory())) {
				result.addWarning("Output directory missing; defaulting to 'build/classes'.");
			}
			if (isBlank(config.getTestSourceDirectory())) {
				result.addWarning("Test source directory missing; defaulting to 'src/test/java'.");
			}
			if (isBlank(config.getTestOutputDirectory())) {
				result.addWarning("Test output directory missing; defaulting to 'build/test-classes'.");
			}

			if (config.getDependencies() != null) {
				for (int i = 0; i < config.getDependencies().size(); i++) {
					Dependency dep = config.getDependencies().get(i);
					if (dep == null) {
						result.addError("Dependency at index " + i + " is null.");
						continue;
					}
					if (isBlank(dep.getGroup())) {
						result.addError("Dependency at index " + i + " missing 'group'.");
					}
					if (isBlank(dep.getArtifact())) {
						result.addError("Dependency at index " + i + " missing 'artifact'.");
					}
					if (isBlank(dep.getVersion())) {
						result.addWarning("Dependency at index " + i + " missing 'version'.");
					}
				}
				
			}

			return result;
		};
	}

	private static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
	
}
