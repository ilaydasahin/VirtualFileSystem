/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualfilesystem;

/**
 *
 * @author sahin
 */
import java.util.ArrayList;
import java.util.List;

public class VDirectory {
    private String name;
    private List<VFile> files;
    private List<VDirectory> directories;
    
    public VDirectory(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        System.out.println("Dizin olusturuldu: " + name);
    }
    
    public String getName() {
        return name;
    }
    
    public List<VFile> getFiles() {
        return files;
    }
    
    public List<VDirectory> getDirectories() {
        return directories;
    }
    
    public boolean fileExists(String fileName) {
        return files.stream().anyMatch(file -> file.getName().equals(fileName));
    }
    
    public boolean directoryExists(String dirName) {
        return directories.stream().anyMatch(directory -> directory.getName().equals(dirName));
    }

    public void addFile(VFile file) {
        if (fileExists(file.getName())) {
            System.out.println("Hata: Ayni isimde bir dosya mevcut: " + file.getName());
        } else {
            this.files.add(file);
            System.out.println("Dosya eklendi: " + file.getName() + " dizin: " + name);
        }
    }
    
    public void addDirectory(VDirectory directory) {
        if (directoryExists(directory.getName())) {
            System.out.println("Hata: Ayni isimde bir dizin mevcut: " + directory.getName());
        } else {
            this.directories.add(directory);
            System.out.println("Dizin eklendi: " + directory.getName() + " dizin: " + name);
        }
    }
    
    public void removeFile(String fileName) {
        boolean removed = files.removeIf(file -> file.getName().equals(fileName));
        if (removed) {
            System.out.println("Dosya silindi: " + fileName + " dizin: " + name);
        } else {
            System.out.println("Dosya bulunamadi: " + fileName);
        }
    }
    
    public void removeDirectory(String directoryName) {
        boolean removed = directories.removeIf(directory -> directory.getName().equals(directoryName));
        if (removed) {
            System.out.println("Dizin silindi: " + directoryName + " dizin: " + name);
        } else {
            System.out.println("Dizin bulunamadi: " + directoryName);
        }
    }
    
    public void listContents() {
        System.out.println(name + " dizinindeki icerikler:");
        for (VFile file : files) {
            System.out.println("Dosya: " + file.getName() + " (Boyut: " + file.getSize() + ")");
        }
        for (VDirectory directory : directories) {
            System.out.println("Dizin: " + directory.getName());
        }
    }
}
