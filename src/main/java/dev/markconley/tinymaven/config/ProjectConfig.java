package dev.markconley.tinymaven.config;

import java.util.List;

public class ProjectConfig {
	private Project project;
	private List<Dependency> dependencies;
	private List<String> tasks;

	public ProjectConfig() {
	}

	public Project getProject() {
		return project;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}

	public static class Project {
		private String name;
		private String version;
		private String mainClass;

		public String getName() {
			return name;
		}

		public String getVersion() {
			return version;
		}

		public String getMainClass() {
			return mainClass;
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
