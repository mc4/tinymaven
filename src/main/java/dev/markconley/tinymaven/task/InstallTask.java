package dev.markconley.tinymaven.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import dev.markconley.tinymaven.config.ProjectConfig;
import dev.markconley.tinymaven.exception.TinyMavenException;

public class InstallTask implements Task {

	private final ProjectConfig config;

	public InstallTask(ProjectConfig config) {
		this.config = Objects.requireNonNull(config);
	}

	@Override
	public void execute() throws TinyMavenException {
		System.out.println("Installing artifact to local TinyMaven repository...");

		try {
			String type = config.getProject().getPackaging(); 
			String fileName = config.getProject().getName() + "." + type;
			Path sourcePath = Paths.get("build/libs").resolve(fileName);
			if (!Files.exists(sourcePath)) {
				throw new TinyMavenException("Artifact not found: " + sourcePath + ". Did you run `package`?");
			}

            String groupIdPath = config.getProject().getGroupId().replace('.', '/');
            Path localRepo = Paths.get(System.getProperty("user.home"), ".tinymaven", "repository");
            Path targetDir = localRepo.resolve(Paths.get(
                groupIdPath,
                config.getProject().getName(),
                config.getProject().getVersion()
            ));

            Files.createDirectories(targetDir);
            Path targetFile = targetDir.resolve(
                config.getProject().getName() + "-" + config.getProject().getVersion() + "." + type
            );

			Files.copy(sourcePath, targetFile, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Installed to: " + targetFile);

		} catch (IOException e) {
			throw new TinyMavenException("Failed to install artifact", e);
		}
	}
}
