package dev.markconley.tinymaven.config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import dev.markconley.tinymaven.exception.ConfigurationException;

public class ConfigLoader {
    public static ProjectConfig loadConfig(String path) throws ConfigurationException {
        try (InputStream inputStream = new FileInputStream(path)) {
            Yaml yaml = new Yaml(new Constructor(ProjectConfig.class, new LoaderOptions()));
            ProjectConfig config = yaml.load(inputStream);
            if (config == null) {
                throw new ConfigurationException("Configuration file is empty or invalid");
            }
            return config;
        } catch (Exception e) {
            throw new ConfigurationException("Failed to load build.yaml: " + e.getMessage(), e);
        }
    }
}
