package dev.markconley.tinymaven.runner;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import dev.markconley.tinymaven.annotation.TinyTest;
import dev.markconley.tinymaven.annotation.TinyTestClass;

public class TinyTestRunner {

	public void runTests(Path compiledTestClassesDir) throws Exception {
		List<Class<?>> testClasses = loadTestClasses(compiledTestClassesDir);
		for (Class<?> testClass : testClasses) {
			if (!testClass.isAnnotationPresent(TinyTestClass.class)) {
				continue;
			}

			Object testInstance = testClass.getDeclaredConstructor().newInstance();
			for (Method method : testClass.getDeclaredMethods()) {
				if (method.isAnnotationPresent(TinyTest.class)) {
					try {
						method.setAccessible(true);
						method.invoke(testInstance);
						System.out.println("[ PASS ]" + testClass.getSimpleName() + "#" + method.getName());
					} catch (Throwable t) {
						System.out.println("[ FAIL ]" + testClass.getSimpleName() + "#" + method.getName() + " failed");
						t.getCause().printStackTrace(System.out);
					}
				}
			}
		}
	}

	private List<Class<?>> loadTestClasses(Path compiledDirectory) throws Exception {
		File dir = compiledDirectory.toFile();
		URL[] urls = { dir.toURI().toURL() };
		List<Class<?>> classes = new ArrayList<>();

		try (URLClassLoader classLoader = new URLClassLoader(urls)) {
			Files.walk(compiledDirectory)
				.filter(p -> p.toString().endsWith(".class"))
				.forEach(p -> {
					String className = compiledDirectory.relativize(p).toString()
							.replace(File.separator, ".")
							.replaceAll("\\.class$", "");
					try {
						classes.add(classLoader.loadClass(className));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			});
		}
		return classes;
	}
	
}
