package com.javarush.zybin.file;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GettingPath {

    public static Path getCurrentDir() {
        String stringRoot = System.getProperty("user.dir");
        Path root = Paths.get(stringRoot);
        return root.resolve("text");
    }
}
