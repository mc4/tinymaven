package dev.markconley.tinymaven.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

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
			System.err.println("Failed to create WAR file: " + e.getMessage());
		}
	}

	private void createWarArchive() throws IOException {
		String warName = config.getProject().getName() + ".war";
		Path outputDirectory = Paths.get("build");
		Path warPath = outputDirectory.resolve(warName);
		Files.createDirectories(outputDirectory);

		try (FileOutputStream fos = new FileOutputStream(warPath.toFile());
				JarOutputStream jos = new JarOutputStream(fos, createManifest(config.getProject().getMainClass()))) {

			// Add WEB-INF/classes
			Path classesDirectory = Paths.get("build/classes");
			if (Files.exists(classesDirectory)) {
				addDirectoryToJar(jos, classesDirectory, "WEB-INF/classes/");
			}

			// TODO: add web resources (like index.html, etc.)
		}
	}

	private Manifest createManifest(String mainClass) {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClass);
		return manifest;
	}

	private void addDirectoryToJar(JarOutputStream jos, Path sourceDirectory, String prefix) throws IOException {
		Files.walk(sourceDirectory).filter(Files::isRegularFile).forEach(path -> {
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
