package dev.markconley.tinymaven;

import java.util.List;

public class ProjectConfig {
	private Project project;
	private List<Dependency> dependencies;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public static class Project {
		private String name;
		private String version;
		private String mainClass;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getMainClass() {
			return mainClass;
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

		public void setGroup(String group) {
			this.group = group;
		}

		public String getArtifact() {
			return artifact;
		}

		public void setArtifact(String artifact) {
			this.artifact = artifact;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
	}
}
