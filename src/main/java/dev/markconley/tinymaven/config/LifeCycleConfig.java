package dev.markconley.tinymaven.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class LifeCycleConfig {

	private static final Map<String, List<String>> JAR_BUILD_LIFECYCLE = Map.of(
			"build", List.of("clean", "sourceCompile", "testCompile", "testRun", "packageJar"), 
			"test", List.of("testCompile", "testRun"), "packagejar", List.of("packageJar"), 
			"compile", List.of("sourceCompile"));

	private static final Map<String, List<String>> WAR_BUILD_LIFECYCLE = Map.of(
			"build", List.of("clean", "sourceCompile", "testCompile", "testRun", "packageWar"), 
			"test", List.of("testCompile", "testRun"), "packagewar", List.of("packageWar"),
			"compile", List.of("sourceCompile"));

	private LifeCycleConfig() { }

	public static List<String> getLifeCycleSteps(String packaging, String lifeCycleName) {
		if ("war".equalsIgnoreCase(packaging)) {
			return WAR_BUILD_LIFECYCLE.getOrDefault(lifeCycleName.toLowerCase(), Collections.emptyList());
		}
		
		// Default to JAR 
		return JAR_BUILD_LIFECYCLE.getOrDefault(lifeCycleName.toLowerCase(), Collections.emptyList());
	}
}
