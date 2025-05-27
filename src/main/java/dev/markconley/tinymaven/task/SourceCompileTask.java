package dev.markconley.tinymaven.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class SourceCompileTask implements Task {
	
	private JavaCompiler compiler;
    private final ProjectConfig config;
	
	public SourceCompileTask(ProjectConfig config, JavaCompiler compiler) {
		this.compiler = Objects.requireNonNull(compiler, "JavaCompiler must not be null");
		this.config = Objects.requireNonNull(config, "Project Configuration cannot be null");
	}

	@Override
	public void execute() throws TinyMavenException {
		compileSources();
	}
	
	private void compileSources() {
		System.out.println("Compiling sources...");
		
		ensureOutputDirectoryExists(config.getOutputDirectory());

		List<String> options = List.of("-d", config.getOutputDirectory());
		Path pathToScan = Paths.get(config.getSourceDirectory());
		
		List<File> sourceFilesToCompile = collectJavaSourceFiles(pathToScan);

		if (sourceFilesToCompile.isEmpty()) {
			System.out.println("No .java files found to compile.");
			return;
		}

		runCompilation(sourceFilesToCompile, options);
	}

	private void ensureOutputDirectoryExists(String outputDirectory) {
		Path path = Paths.get(outputDirectory);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<File> collectJavaSourceFiles(Path pathToScan) {
		try (Stream<Path> streamOfFiles = Files.walk(pathToScan)) {
			return streamOfFiles
					.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
					.map(Path::toFile)
					.toList();
		} catch (IOException e) {
			System.out.println(
					"something bad happened when reading or processing the files to scan in " + "'" + pathToScan + "'");
			e.printStackTrace();
		}
		return List.of();
	}


	private boolean runCompilation(List<File> sources, List<String> options) {
		try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
			Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sources);
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null,compilationUnits);
			boolean success = task.call();
			return success;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
