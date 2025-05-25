package dev.markconley.tinymaven.config;

import java.util.List;
import java.util.Map;

public class LifeCycleConfig {

    public static final Map<String, List<String>> LIFECYCLES = Map.of(
        "build", List.of("sourceCompile", "testCompile", "testRun", "packageJar"),
        "test", List.of("testCompile", "testRun"),
        "packagejar", List.of("packageJar"),
        "packageWar", List.of("packageWar"),
        "compile", List.of("sourceCompile")
    );
    
}