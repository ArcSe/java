package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private  int maxSize;

    private List<Path> foundFiles = new ArrayList<>();

    boolean minSizeCheck = false;
    boolean maxSizeCheck = false;
    boolean partOfNameCheck = false;
    boolean partOfContentCheck = false;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        if(maxSizeCheck && maxSize<content.length){
            return FileVisitResult.CONTINUE;
        }
        if(minSizeCheck && minSize>content.length){
            return FileVisitResult.CONTINUE;
        }

        if(partOfNameCheck && partOfName!=null && !file.getFileName().toString().contains(partOfName))
            return FileVisitResult.CONTINUE;

        String contentStr = new String(content);
        if(partOfContentCheck && partOfContent!=null && !contentStr.contains(partOfContent))
            return FileVisitResult.CONTINUE;

        foundFiles.add(file);

        return FileVisitResult.CONTINUE;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
        this.partOfNameCheck = true;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
        this.partOfContentCheck = true;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
        this.minSizeCheck = true;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.maxSizeCheck = true;
    }

}
