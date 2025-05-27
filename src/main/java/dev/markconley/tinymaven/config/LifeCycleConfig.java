package dev.markconley.tinymaven.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class LifeCycleConfig {

    private LifeCycleConfig() {}

    private static final Map<BuildLifeCycle, List<BuildStep>> JAR_BUILD_LIFECYCLE = Map.of(
        BuildLifeCycle.BUILD, List.of(
            BuildStep.CLEAN,
            BuildStep.SOURCE_COMPILE,
            BuildStep.TEST_COMPILE,
            BuildStep.TEST_RUN,
            BuildStep.PACKAGE_JAR
        ),
        BuildLifeCycle.TEST, List.of(
            BuildStep.TEST_COMPILE,
            BuildStep.TEST_RUN
        ),
        BuildLifeCycle.PACKAGE_JAR, List.of(
            BuildStep.PACKAGE_JAR
        ),
        BuildLifeCycle.COMPILE, List.of(
            BuildStep.SOURCE_COMPILE
        )
    );

    private static final Map<BuildLifeCycle, List<BuildStep>> WAR_BUILD_LIFECYCLE = Map.of(
        BuildLifeCycle.BUILD, List.of(
            BuildStep.CLEAN,
            BuildStep.SOURCE_COMPILE,
            BuildStep.TEST_COMPILE,
            BuildStep.TEST_RUN,
            BuildStep.PACKAGE_WAR
        ),
        BuildLifeCycle.TEST, List.of(
            BuildStep.TEST_COMPILE,
            BuildStep.TEST_RUN
        ),
        BuildLifeCycle.PACKAGE_WAR, List.of(
            BuildStep.PACKAGE_WAR
        ),
        BuildLifeCycle.COMPILE, List.of(
            BuildStep.SOURCE_COMPILE
        )
    );

    public static List<BuildStep> getLifeCycleSteps(String packaging, BuildLifeCycle lifeCycle) {
        if ("war".equalsIgnoreCase(packaging)) {
            return WAR_BUILD_LIFECYCLE.getOrDefault(lifeCycle, Collections.emptyList());
        }
        // Default to JAR
        return JAR_BUILD_LIFECYCLE.getOrDefault(lifeCycle, Collections.emptyList());
    }
}
