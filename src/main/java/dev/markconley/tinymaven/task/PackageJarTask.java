package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import dev.markconley.tinymaven.config.ManifestAttributes;
import dev.markconley.tinymaven.config.ProjectConfig;

public class PackageJarTask implements Task {

	@Override
	public void execute(ProjectConfig config) {
		packageJar(config);
	}

	private void packageJar(ProjectConfig config) {
	    System.out.println("Packaging JAR...");

	    try {
	        Path buildRoot = Paths.get(config.getOutputDirectory()).toAbsolutePath().normalize();
	        Path jarOutputDir = Paths.get("build/libs").toAbsolutePath().normalize();
	        Files.createDirectories(jarOutputDir);

	        String jarFileName = config.getProject().getName() + ".jar";
	        Path jarPath = jarOutputDir.resolve(jarFileName);

	        List<Path> classFiles = collectClassFiles(buildRoot.toString());
	        Manifest manifest = createManifest(config.getProject().getMainClass());

	        buildJar(jarPath, classFiles, manifest, buildRoot);

	        System.out.println("JAR packaged successfully at: " + jarPath);
	    } catch (IOException e) {
	        System.err.println("Failed to create JAR file: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	private Manifest createManifest(String mainClass) {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClass);
		manifest.getMainAttributes().put(new Attributes.Name(ManifestAttributes.CREATED_BY), "TinyMaven 1.0 (Java 24)");
		manifest.getMainAttributes().put(new Attributes.Name(ManifestAttributes.BUILT_BY), "Mark Conley");
		manifest.getMainAttributes().put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_TITLE), "TinyMaven");
		manifest.getMainAttributes().put(new Attributes.Name(ManifestAttributes.IMPLEMENTATION_VERSION), "1.0.0");
		return manifest;
	}

	private List<Path> collectClassFiles(String sourceDirectory) throws IOException {
		return Files.walk(Path.of(sourceDirectory))
				.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".class"))
				.collect(Collectors.toList());
	}

	private void buildJar(Path jarPath, List<Path> classFiles, Manifest manifest, Path buildRoot)
	        throws IOException {

	    try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(jarPath), manifest)) {
	        for (Path classFile : classFiles) {
	            Path normalizedClassFile = classFile.toAbsolutePath().normalize();

	            if (!normalizedClassFile.startsWith(buildRoot)) {
	                System.err.println("Skipping file outside build root: " + classFile);
	                continue;
	            }

	            String entryName = buildRoot.relativize(normalizedClassFile).toString().replace("\\", "/");
	            System.out.println("Adding to JAR: " + entryName);

	            JarEntry entry = new JarEntry(entryName);
	            jos.putNextEntry(entry);
	            Files.copy(normalizedClassFile, jos);
	            jos.closeEntry();
	        }
	    }
	}

}
