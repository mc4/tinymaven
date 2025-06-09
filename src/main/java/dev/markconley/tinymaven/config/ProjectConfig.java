package dev.markconley.tinymaven.config;

import java.util.List;
import java.util.Map;

public class ProjectConfig {
	private Project project;
	private List<Dependency> dependencies;
	private Map<String, List<String>> lifecycle;
	
	private String sourceDirectory;
	private String outputDirectory;
	private String testSourceDirectory;
	private String testOutputDirectory;

	public ProjectConfig() {
	}

	public Project getProject() {
		return project;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	// Default to "src/main/java" if not specified
	public String getSourceDirectory() {
		return sourceDirectory != null ? sourceDirectory : "src/main/java";
	}

	// Default to "build/classes" if not specified
	public String getOutputDirectory() {
		return outputDirectory != null ? outputDirectory : "build/classes";
	}
	
	public String getTestSourceDirectory() {
	    return testSourceDirectory != null ? testSourceDirectory : "src/test/java";
	}

	public String getTestOutputDirectory() {
	    return testOutputDirectory != null ? testOutputDirectory : "build/test-classes";
	}
	
	public Map<String, List<String>> getLifecycle() {
		return lifecycle;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	
	public void setTestSourceDirectory(String testSourceDirectory) {
	    this.testSourceDirectory = testSourceDirectory;
	}

	public void setTestOutputDirectory(String testOutputDirectory) {
	    this.testOutputDirectory = testOutputDirectory;
	}
	
	public void setLifecycle(Map<String, List<String>> lifecycle) {
		this.lifecycle = lifecycle;
	}

	public static class Project {
		private String name;
		private String version;
		private String mainClass;
		private String packaging;
		private String groupId;

		public String getName() {
			return name;
		}

		public String getVersion() {
			return version;
		}

		public String getMainClass() {
			return mainClass;
		}

		public String getPackaging() {
			return packaging != null ? packaging : "jar"; // default to jar
		}

		public String getGroupId() {
			return groupId != null ? groupId : "default.group"; // add fallback if needed
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public void setMainClass(String mainClass) {
			this.mainClass = mainClass;
		}

		public void setPackaging(String packaging) {
			this.packaging = packaging;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
	}

	public static class Dependency {
		private String group;
		private String artifact;
		private String version;

		public String getGroup() {
			return group;
		}

		public String getArtifact() {
			return artifact;
		}

		public String getVersion() {
			return version;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public void setArtifact(String artifact) {
			this.artifact = artifact;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		
	}
	
}
