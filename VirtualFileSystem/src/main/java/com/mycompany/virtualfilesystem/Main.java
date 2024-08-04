/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualfilesystem;

/**
 *
 * @author sahin
 */


import java.util.InputMismatchException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Disk disk = new Disk(1024); // 1 MB disk alanı
        VDirectory root = new VDirectory("root");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nYapmak istediginiz islemi secin veya komut girin:");
            System.out.println("1. Dosya Ekle (komut: addfile <dosya_adi> <icerik>)");
            System.out.println("2. Dizin Ekle (komut: adddir <dizin_adi>)");
            System.out.println("3. Dosya Sil (komut: delfile <dosya_adi>)");
            System.out.println("4. Dizin Sil (komut: deldir <dizin_adi>)");
            System.out.println("5. Icerikleri Listele (komut: list)");
            System.out.println("6. Dosya Icerigini Oku (komut: readfile <dosya_adi>)");
            System.out.println("7. Cikis (komut: exit)");
            System.out.print("Seciminiz veya Komut: ");

            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+", 2);
            String command = parts[0];
            String argsCommand = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "1":
                case "addfile":
                    if (command.equals("1")) {
                        System.out.print("Dosya Adi: ");
                        String fileName = scanner.nextLine();
                        System.out.print("Dosya Icerigi: ");
                        String fileContent = scanner.nextLine();
                        addFile(disk, root, fileName, fileContent);
                    } else {
                        String[] fileArgs = argsCommand.split("\\s+", 2);
                        if (fileArgs.length == 2) {
                            addFile(disk, root, fileArgs[0], fileArgs[1]);
                        } else {
                            System.out.println("Gecersiz komut! Kullanim: addfile <dosya_adi> <icerik>");
                        }
                    }
                    break;

                case "2":
                case "adddir":
                    if (command.equals("2")) {
                        System.out.print("Dizin Adi: ");
                        String dirName = scanner.nextLine();
                        addDirectory(root, dirName);
                    } else {
                        addDirectory(root, argsCommand);
                    }
                    break;

                case "3":
                case "delfile":
                    if (command.equals("3")) {
                        System.out.print("Silinecek Dosya Adi: ");
                        String fileToDelete = scanner.nextLine();
                        deleteFile(disk, root, fileToDelete);
                    } else {
                        deleteFile(disk, root, argsCommand);
                    }
                    break;

                case "4":
                case "deldir":
                    if (command.equals("4")) {
                        System.out.print("Silinecek Dizin Adi: ");
                        String dirToDelete = scanner.nextLine();
                        deleteDirectory(root, dirToDelete);
                    } else {
                        deleteDirectory(root, argsCommand);
                    }
                    break;

                case "5":
                case "list":
                    listContents(root);
                    break;

                case "6":
                case "readfile":
                    if (command.equals("6")) {
                        System.out.print("Icerigi okunacak Dosya Adi: ");
                        String fileToRead = scanner.nextLine();
                        readFile(root, fileToRead);
                    } else {
                        readFile(root, argsCommand);
                    }
                    break;

                case "7":
                case "exit":
                    System.out.println("Cikiliyor...");
                    return;

                default:
                    System.out.println("Gecersiz secenek veya komut. Lutfen tekrar deneyin.");
            }
        }
    }

    private static void addFile(Disk disk, VDirectory root, String fileName, String fileContent) {
        VFile newFile = new VFile(fileName);
        newFile.writeContent(fileContent);

        if (!disk.hasSpaceFor(newFile.getSize())) {
            disk.freeUpSpace(root, newFile.getSize());
        }

        if (disk.allocateSpace(newFile.getSize())) {
            root.addFile(newFile);
        } else {
            System.out.println("Dosya eklenemedi: " + fileName + ". Yetersiz disk alani.");
        }
    }

    private static void addDirectory(VDirectory root, String dirName) {
        VDirectory newDir = new VDirectory(dirName);
        root.addDirectory(newDir);
    }

    private static void deleteFile(Disk disk, VDirectory root, String fileToDelete) {
        root.getFiles().stream()
            .filter(file -> file.getName().equals(fileToDelete))
            .findFirst()
            .ifPresent(file -> {
                root.removeFile(fileToDelete);
                disk.deallocateSpace(file.getSize());
            });
    }

    private static void deleteDirectory(VDirectory root, String dirToDelete) {
        root.removeDirectory(dirToDelete);
    }

    private static void listContents(VDirectory root) {
        root.listContents();
    }

    private static void readFile(VDirectory root, String fileToRead) {
        root.getFiles().stream()
            .filter(file -> file.getName().equals(fileToRead))
            .findFirst()
            .ifPresentOrElse(
                file -> System.out.println("Dosya Icerigi: " + file.readContent()),
                () -> System.out.println("Dosya bulunamadi: " + fileToRead)
            );
    }
}
