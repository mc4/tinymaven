package dev.markconley.tinymaven.task;

import dev.markconley.tinymaven.config.ProjectConfig;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CleanTask implements Task {

	@Override
	public void execute(ProjectConfig config) {
		cleanDirectory(config.getOutputDirectory());
		cleanDirectory(config.getTestOutputDirectory());
	}

	private void cleanDirectory(String directory) {
		Path path = Paths.get(directory);
		if (Files.exists(path)) {
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
				System.out.println("Cleaned: " + directory);
			} catch (IOException e) {
				System.err.println("Failed to clean: " + directory);
				e.printStackTrace();
			}
		} else {
			System.out.println("Nothing to clean in: " + directory);
		}
	}
	
}
