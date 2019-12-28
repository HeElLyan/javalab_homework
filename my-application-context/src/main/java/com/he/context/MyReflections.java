package com.he.context;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MyReflections {

    public  <T> Set<Class<? extends T>> getSubTypesOf(Class<T> tClass) {
        return getAllClasses().stream()
                .filter(tClass::isAssignableFrom)
                .map(x -> (Class<? extends T>) x)
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> getAllClasses() {
        Set<Path> allPaths = getAllPath();
        Set<Class<?>> allClasses = new HashSet<>();
        for (Path path : allPaths) {
            try {
                String relatedPath = getClass().getClassLoader().getResource("").toURI().relativize(path.toUri()).toString();
                allClasses.add(Class.forName(pathToPackageName(removedEncoding(relatedPath))));
            } catch (URISyntaxException | ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return allClasses;
    }

    public Set<Path> getAllPath() {
        Set<Path> paths = new HashSet<>();
        FileVisitor<Path> myFileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                paths.add(file);
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Path.of(getClass().getClassLoader().getResource("").toURI()), myFileVisitor);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        return paths;
    }

    private String pathToPackageName(String path) {
        return path.replace('/', '.');
    }

    private String removedEncoding(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

}
