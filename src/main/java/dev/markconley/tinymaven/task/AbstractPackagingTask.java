package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import dev.markconley.tinymaven.config.ManifestAttributes;
import dev.markconley.tinymaven.config.ProjectConfig;

public abstract class AbstractPackagingTask implements Task {

	protected final ProjectConfig config;

	protected AbstractPackagingTask(ProjectConfig config) {
		this.config = config;
	}

	protected Manifest createManifest() {
		Manifest manifest = new Manifest();
		Attributes attrs = manifest.getMainAttributes();
		attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		attrs.put(Attributes.Name.MAIN_CLASS, config.getProject().getMainClass());
		attrs.put(new Attributes.Name(ManifestAttributes.CREATED_BY), "TinyMaven 1.0");
		attrs.put(new Attributes.Name(ManifestAttributes.BUILT_BY), "Mark Conley");
		attrs.put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_TITLE), "TinyMaven");
		attrs.put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_VERSION), "1.0.0");
		return manifest;
	}

	protected Path getJarOutputDir() throws IOException {
		Path outputDir = Paths.get("build", "libs").toAbsolutePath().normalize();
		Files.createDirectories(outputDir);
		return outputDir;
	}

	protected Path getBuildClassesDir() {
		return Paths.get(config.getOutputDirectory()).toAbsolutePath().normalize();
	}

}
