package com.bbloggsbott.manualimageselector.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JLabel originalImage, segmentedImage;
    private JButton nextImage, deleteButton;
    private String currentImage, originalImageDir, segmentedImageDir, segmentedImageSuffix;
    private ArrayList<String> imagePrefixes;
    private int imageIndex;

    MainFrame(String originalImageDir, String segmentedImageDir){
        this.originalImageDir = originalImageDir;
        this.segmentedImageDir = segmentedImageDir;
        this.segmentedImageSuffix = "_segmented";
        originalImage = new JLabel(new ImageIcon());
        segmentedImage = new JLabel(new ImageIcon());
        nextImage = new JButton();
        deleteButton = new JButton();
        imageIndex = 0;

        nextImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNextImage();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCurrentImage();
            }
        });
    }

    MainFrame(String originalImageDir, String segmentedImageDir, String segmentedImageSuffix){
        this(originalImageDir, segmentedImageDir);
        this.segmentedImageSuffix = segmentedImageSuffix;
    }

    //TODO: Load next image to the JLabels
    void loadNextImage(){
        String nextImagePrefix = imagePrefixes.get(imageIndex);
        originalImage = new JLabel(new ImageIcon());
        segmentedImage = new JLabel(new ImageIcon());
        currentImage = nextImagePrefix;
        this.repaint();
    }

    //TODO: Delete Current Image
    void deleteCurrentImage(){

    }
}
