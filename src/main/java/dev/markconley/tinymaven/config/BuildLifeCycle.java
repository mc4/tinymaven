package dev.markconley.tinymaven.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum BuildLifeCycle {
	BUILD("build"), 
	TEST("test"), 
	PACKAGE_JAR("packagejar"), 
	PACKAGE_WAR("packagewar"), 
	COMPILE("compile");

	private final String name;

	BuildLifeCycle(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// Map lifecycle to ordered list of phases/tasks
	private static final Map<BuildLifeCycle, List<BuildPhase>> LIFECYCLE_MAP = Map.of(
			BUILD, List.of(BuildPhase.CLEAN, BuildPhase.SOURCECOMPILE, BuildPhase.TESTCOMPILE, BuildPhase.TESTRUN,
					BuildPhase.PACKAGEJAR),
			TEST, List.of(BuildPhase.CLEAN, BuildPhase.TESTCOMPILE, BuildPhase.TESTRUN), 
			PACKAGE_JAR, List.of(BuildPhase.PACKAGEJAR),
			PACKAGE_WAR, List.of(BuildPhase.PACKAGEWAR), 
			COMPILE, List.of(BuildPhase.SOURCECOMPILE));
	
	private static final Map<BuildLifeCycle, List<BuildPhase>> WAR_LIFECYCLE_OVERRIDES = Map.of(
			BUILD, List.of(BuildPhase.CLEAN, BuildPhase.SOURCECOMPILE, BuildPhase.TESTCOMPILE, BuildPhase.TESTRUN, BuildPhase.PACKAGEWAR),
			PACKAGE_WAR, List.of(BuildPhase.PACKAGEWAR)
		);

	public List<BuildPhase> getPhases() {
		return LIFECYCLE_MAP.getOrDefault(this, List.of());
	}

	public static BuildLifeCycle fromName(String name) {
		for (BuildLifeCycle lc : values()) {
			if (lc.name.equalsIgnoreCase(name)) {
				return lc;
			}
		}
		return null;
	}
	
	public static List<String> getStepsFor(String packaging, BuildLifeCycle lifecycle) {
		if (lifecycle == null) {
			return Collections.emptyList();
		}

		List<BuildPhase> phases;
		if ("war".equalsIgnoreCase(packaging)) {
			// WAR lifecycle may override default phases
			phases = WAR_LIFECYCLE_OVERRIDES.getOrDefault(lifecycle, LIFECYCLE_MAP.getOrDefault(lifecycle, List.of()));
		} else {
			// default to JAR config
			phases = LIFECYCLE_MAP.getOrDefault(lifecycle, List.of());
		}

		return phases.stream()
			.map(BuildPhase::getTaskName)
			.toList();
	}

}
