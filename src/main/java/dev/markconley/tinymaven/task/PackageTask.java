package dev.markconley.tinymaven.task;

import dev.markconley.tinymaven.config.ProjectConfig;

public class PackageTask implements Task {

	@Override
	public void execute(ProjectConfig config) {
		
	}
	
	public void packageJar() {
		System.out.println("Packaging JAR...");
		// TODO: Bundle compiled classes into a JAR with manifest!!!
	}

}
