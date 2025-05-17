package dev.markconley.tinymaven.task;

import java.util.Objects;

import javax.tools.JavaCompiler;

import dev.markconley.tinymaven.config.ProjectConfig;

public class TestTask implements Task {

	private JavaCompiler compiler;
	
	public TestTask(JavaCompiler compiler) {
		this.compiler = Objects.requireNonNull(compiler);
	}
	
	@Override
	public void execute(ProjectConfig config) {
		
	}
	
	public void runTests() {
		System.out.println("Running tests...");
		// TODO: Scan for test classes, run methods, report results
	}

}
