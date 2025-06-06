package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class PackageJarTask extends AbstractPackagingTask {

	public PackageJarTask(ProjectConfig config) {
		super(config);
	}

	@Override
	public void execute() throws TinyMavenException {
		System.out.println("Packaging JAR...");
		try {
			Path outputDir = getJarOutputDir();
			String jarFileName = config.getProject().getName() + ".jar";
			Path jarPath = outputDir.resolve(jarFileName);

			List<Path> classFiles = collectClassFiles(getBuildClassesDir());
			Manifest manifest = createManifest();

			try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(jarPath), manifest)) {
				for (Path classFile : classFiles) {
					String entryName = getBuildClassesDir().relativize(classFile).toString().replace("\\", "/");
					JarEntry entry = new JarEntry(entryName);
					jos.putNextEntry(entry);
					Files.copy(classFile, jos);
					jos.closeEntry();
				}
			}

			System.out.println("JAR packaged successfully at: " + jarPath);
		} catch (IOException e) {
			throw new TinyMavenException("Failed to package JAR", e);
		}
	}

	private List<Path> collectClassFiles(Path baseDir) throws IOException {
		return Files.walk(baseDir)
				.filter(p -> Files.isRegularFile(p) && p.toString().endsWith(".class"))
				.collect(Collectors.toList());
	}

}
