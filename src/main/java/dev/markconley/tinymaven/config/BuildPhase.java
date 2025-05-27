package dev.markconley.tinymaven.config;

import java.util.Arrays;

public enum BuildPhase {
    CLEAN("clean"),
    SOURCECOMPILE("sourceCompile"),
    TESTCOMPILE("testCompile"),
    TESTRUN("testRun"),
    PACKAGEJAR("packageJar"),
    PACKAGEWAR("packageWar");

    private final String taskName;

    BuildPhase(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskName() {
        return taskName;
    }

    public static BuildPhase fromName(String name) {
        return Arrays.stream(values())
                .filter(phase -> phase.taskName.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
