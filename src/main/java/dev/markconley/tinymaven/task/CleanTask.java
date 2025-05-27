package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class CleanTask implements Task {
	
    private final ProjectConfig config;

    public CleanTask(ProjectConfig config) {
        this.config = config;
    }

	@Override
	public void execute() throws TinyMavenException {
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
