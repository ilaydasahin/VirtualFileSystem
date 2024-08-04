/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualfilesystem;

/**
 *
 * @author sahin
 */


import java.util.Comparator;
import java.util.Optional;

public class Disk {
    private long totalSize;
    private long usedSize;

    // Parametreli constructor
    public Disk(long totalSize) {
        this.totalSize = totalSize;
        this.usedSize = 0;
        System.out.println("Disk olusturuldu. Toplam boyut: " + totalSize + " KB");
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getUsedSize() {
        return usedSize;
    }

    public long getFreeSize() {
        return totalSize - usedSize;
    }

    public boolean hasSpaceFor(long size) {
        boolean hasSpace = size <= getFreeSize();
        if (hasSpace) {
            System.out.println("Yeterli alan var. Gerekli boyut: " + size + " KB, Kalan alan: " + getFreeSize() + " KB");
        } else {
            System.out.println("Yetersiz disk alani! Gerekli boyut: " + size + " KB, Kalan alan: " + getFreeSize() + " KB");
        }
        return hasSpace;
    }

    public boolean allocateSpace(long size) {
        if (hasSpaceFor(size)) {
            usedSize += size;
            System.out.println("Disk alani tahsis edildi. Tahsis edilen boyut: " + size + " KB, Kullanılan alan: " + usedSize + " KB");
            return true;
        } else {
            System.out.println("Diskte yeterli alan yok! Tahsis işlemi basarısız.");
            return false;
        }
    }

    public void deallocateSpace(long size) {
        usedSize -= size;
        System.out.println("Disk alani serbest birakildi. Serbest birakilan boyut: " + size + " KB, Kullanılan alan: " + usedSize + " KB");
    }

    public void freeUpSpace(VDirectory root, long requiredSize) {
        while (!hasSpaceFor(requiredSize)) {
            Optional<VFile> oldestFile = root.getFiles().stream()
                    .min(Comparator.comparingLong(VFile::getCreationTime));

            if (oldestFile.isPresent()) {
                VFile fileToRemove = oldestFile.get();
                root.removeFile(fileToRemove.getName());
                deallocateSpace(fileToRemove.getSize());
                System.out.println("Eski dosya silindi: " + fileToRemove.getName() + " (Boyut: " + fileToRemove.getSize() + " KB)");
            } else {
                System.out.println("Disk dolu ve silinecek dosya yok.");
                break;
            }
        }
    }
}
