package dev.markconley.tinymaven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import dev.markconley.tinymaven.config.ProjectConfig;

public class BuildExecutor {
	private final ProjectConfig config;

	public BuildExecutor(ProjectConfig config) {
		this.config = config;
	}

	public void compileSources() {
		System.out.println("Compiling sources...");
		ensureOutputDirectoryExists();

		List<String> options = List.of("-d", "build/classes");
		Path pathToScan = Paths.get("src/main/java/");
		List<File> sourceFilesToCompile = collectJavaSourceFiles(pathToScan);

		if (sourceFilesToCompile.isEmpty()) {
			System.out.println("No .java files found to compile.");
			return;
		}

		runCompilation(sourceFilesToCompile, options);
	}

	private static List<File> collectJavaSourceFiles(Path pathToScan) {
		try (Stream<Path> streamOfFiles = Files.walk(pathToScan)) {
			return streamOfFiles.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
					.map(Path::toFile).toList();
		} catch (IOException e) {
			System.out
					.println("something bad happened when reading or processing the files to scan in 'src/main/java/'");
			e.printStackTrace();
		}
		return List.of();
	}

	private static void ensureOutputDirectoryExists() {
		Path path = Paths.get("build/classes/");
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean runCompilation(List<File> sources, List<String> options) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			throw new IllegalStateException("No system Java compiler available. Are you running on a JDK?");
		}
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

	public void runTests() {
		System.out.println("Running tests...");
		// TODO: Scan for test classes, run methods, report results
	}

	public void packageJar() {
		System.out.println("Packaging JAR...");
		// TODO: Bundle compiled classes into a JAR with manifest!!!
	}
}
