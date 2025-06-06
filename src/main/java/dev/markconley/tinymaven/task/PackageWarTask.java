package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class PackageWarTask extends AbstractPackagingTask {

	public PackageWarTask(ProjectConfig config) {
		super(config);
	}

	@Override
	public void execute() throws TinyMavenException {
		System.out.println("Packaging WAR...");
		try {
			Path outputDir = getJarOutputDir();
			String warFileName = config.getProject().getName() + ".war";
			Path warPath = outputDir.resolve(warFileName);

			Manifest manifest = createManifest();

			try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(warPath), manifest)) {
				Path classesDir = getBuildClassesDir();
				if (Files.exists(classesDir)) {
					addWebInfClassesToJar(jos, classesDir, "WEB-INF/classes/");
				}

			}

			System.out.println("WAR packaged successfully at: " + warPath);

		} catch (IOException e) {
			throw new TinyMavenException("Failed to package WAR", e);
		}
	}
	
	private void addWebInfClassesToJar(JarOutputStream jos, Path sourceDirectory, String prefix) throws IOException {
		Files.walk(sourceDirectory)
			 .filter(Files::isRegularFile)
			 .forEach(path -> {
				try {
					String entryName = prefix + sourceDirectory.relativize(path).toString().replace("\\", "/");
					JarEntry entry = new JarEntry(entryName);
					jos.putNextEntry(entry);
					Files.copy(path, jos);
					jos.closeEntry();
				} catch (IOException e) {
					throw new RuntimeException("Failed to add file to JAR: " + path, e);
				}
			});
	}

}
