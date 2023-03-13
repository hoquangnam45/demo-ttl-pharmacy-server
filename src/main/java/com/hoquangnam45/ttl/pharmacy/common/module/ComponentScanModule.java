package com.hoquangnam45.ttl.pharmacy.common.module;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;

public class ComponentScanModule extends AbstractModule {
  private final String packageName;
  private final boolean recursive;

  public ComponentScanModule(String packageName, boolean recursive) {
    this.packageName = packageName;
    this.recursive = recursive;
  }

  @Override
  protected void configure() {
    getClassOfPackage(packageName, recursive).forEach(this::bind);
  }

  public static List<Class<?>> getClassOfPackage(String packageName, boolean recursive) {
    try (InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      Iterable<String> lines = () -> reader.lines().iterator();
      List<Class<?>> clazzes = new ArrayList<Class<?>>();
      for (String line : lines) {
        if (!line.endsWith(".class") && recursive) {
          clazzes.addAll(getClassOfPackage(packageName + "." + line, true));
        } else if (line.endsWith(".class")) {
          Class<?> clazz = getClass(line, packageName);

          // Filter out erronous anonymous classes
          if (clazz.getCanonicalName() == null) {
            continue;
          }

          clazzes.add(clazz);
        }
      }
      return clazzes;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  protected static Class<?> getClass(String className, String packageName) throws ClassNotFoundException {
    return Class.forName(packageName + "."
        + className.substring(0, className.lastIndexOf('.')));
  }
}
