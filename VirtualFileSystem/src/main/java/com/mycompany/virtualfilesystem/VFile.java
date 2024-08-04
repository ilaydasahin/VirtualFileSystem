/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualfilesystem;

/**
 *
 * @author sahin
 */

public class VFile {
    private String name;
    private String content;
    private long size;
    private long creationTime;  // Dosyanın oluşturulma zamanı
    
    public VFile(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
        this.creationTime = System.currentTimeMillis();
        System.out.println("Dosya olusturuldu: " + name);
    }
    
    public String getName() {
        return name;
    }
    
    public long getSize() {
        return size;
    }
    
    public long getCreationTime() {
        return creationTime;
    }
    
    public String readContent() {
        System.out.println("Dosya okunuyor: " + name);
        return content;
    }
    
    public void writeContent(String content) {
        this.content = content;
        this.size = content.length();
        System.out.println("Dosyaya yazildi : " + name + " icerik: " + content);
    }
    
    public void appendContent(String content) {
        this.content += content;
        this.size = this.content.length();
        System.out.println("Dosyaya ekleme yapildi: " + name + " icerik: " + content);
    }
}
