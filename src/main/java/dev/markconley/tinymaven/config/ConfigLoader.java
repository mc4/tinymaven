package dev.markconley.tinymaven.config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ConfigLoader {
	public static ProjectConfig loadConfig(String path) {
		try (InputStream inputStream = new FileInputStream(path)) {
			Yaml yaml = new Yaml(new Constructor(ProjectConfig.class, new LoaderOptions()));
			return yaml.load(inputStream);
		} catch (Exception e) {
			System.err.println("Failed to load build.yaml: " + e.getMessage());
			System.exit(1);
			return null;
		}
	}
}
