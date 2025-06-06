package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import dev.markconley.tinymaven.config.ManifestAttributes;
import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class PackageWarTask implements Task {

	private final ProjectConfig config;

	public PackageWarTask(ProjectConfig config) {
		this.config = Objects.requireNonNull(config, "Project Configuration cannot be null");
	}

	@Override
	public void execute() throws TinyMavenException {
		System.out.println("Packaging WAR...");
		try {
			createWarArchive();
		} catch (IOException e) {
			throw new TinyMavenException("Failed to create WAR file", e);
		}
	}

	private void createWarArchive() throws IOException {
		String warName = config.getProject().getName() + ".war";
		Path outputDirectory = Paths.get("build");
		Path warPath = outputDirectory.resolve(warName);
		Files.createDirectories(outputDirectory);
		Manifest manifest = createManifest(config.getProject().getMainClass());

		try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(warPath), manifest)) {
			Path classesDir = Paths.get(config.getOutputDirectory()).toAbsolutePath().normalize();
			if (Files.exists(classesDir)) {
				addDirectoryToJar(jos, classesDir, "WEB-INF/classes/");
			}

			Path webResourcesDir = Paths.get("src/main/webapp").toAbsolutePath().normalize();
			if (Files.exists(webResourcesDir)) {
				addDirectoryToJar(jos, webResourcesDir, "");
			}

			System.out.println("WAR packaged successfully at: " + warPath);
		}
	}

	private Manifest createManifest(String mainClass) {
		Manifest manifest = new Manifest();
		Attributes attrs = manifest.getMainAttributes();
		attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		attrs.put(Attributes.Name.MAIN_CLASS, mainClass);
		attrs.put(new Attributes.Name(ManifestAttributes.CREATED_BY), "TinyMaven 1.0");
		attrs.put(new Attributes.Name(ManifestAttributes.BUILT_BY), "Mark Conley");
		attrs.put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_TITLE), "TinyMaven");
		attrs.put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_VERSION), "1.0.0");
		return manifest;
	}

	private void addDirectoryToJar(JarOutputStream jos, Path sourceDirectory, String prefix) throws IOException {
		Files.walk(sourceDirectory)
			.filter(Files::isRegularFile)
			.forEach(path -> {
			String entryName = prefix + sourceDirectory.relativize(path).toString().replace("\\", "/");
			JarEntry entry = new JarEntry(entryName);
			try {
				jos.putNextEntry(entry);
				Files.copy(path, jos);
				jos.closeEntry();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

}
