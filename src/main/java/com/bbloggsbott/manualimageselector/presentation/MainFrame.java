package com.bbloggsbott.manualimageselector.presentation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private JLabel originalImage, segmentedImage, logs;
    private JButton nextImage, deleteButton;
    private String  originalImageDir, segmentedImageDir, segmentedImageSuffix;
    private ArrayList<File> originalImageFiles;
    private int imageIndex;
    private File currentImage;

    MainFrame(String originalImageDir, String segmentedImageDir){
        this.originalImageDir = originalImageDir;
        this.segmentedImageDir = segmentedImageDir;
        this.segmentedImageSuffix = "_segmented";
        File folder = new File(originalImageDir);
        this.originalImageFiles = new ArrayList<>(Arrays.asList(folder.listFiles()));

        originalImage = new JLabel(new ImageIcon());
        segmentedImage = new JLabel(new ImageIcon());
        nextImage = new JButton();
        deleteButton = new JButton();
        imageIndex = 0;

        nextImage.addActionListener(e -> loadNextImage());

        deleteButton.addActionListener(e -> deleteCurrentImage());
    }

    MainFrame(String originalImageDir, String segmentedImageDir, String segmentedImageSuffix){
        this(originalImageDir, segmentedImageDir);
        this.segmentedImageSuffix = segmentedImageSuffix;
    }


    //TODO: Load next image to the JLabels
    private void loadNextImage(){
        imageIndex+=1;
        currentImage = originalImageFiles.get(imageIndex);
        originalImage = new JLabel(new ImageIcon(currentImage.getAbsolutePath()));
        segmentedImage = new JLabel(new ImageIcon(getSegmentedImageFileName(currentImage)));
    }

    //TODO: Delete Current Image
    private void deleteCurrentImage(){
        String name = currentImage.getName();
        if(currentImage.delete()){
            originalImageFiles.remove(imageIndex);
            logs.setText("Deteled File "+name);
        } else {
            logs.setText("Could not delete file "+name);
        }
    }

    private String getSegmentedImageFileName(File file){
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        String extension = name.substring(lastIndex);
        return segmentedImageDir + name + segmentedImageSuffix + extension;
    }

    public static String getSegmentedImageSuffix(){
        JPanel forSuffix = new JPanel(new FlowLayout());
        JLabel suffixLabel = new JLabel("Suffix: ");
        JTextField suffix = new JTextField(10);
        suffix.setText("_segmented");
        forSuffix.add(suffixLabel);
        forSuffix.add(suffix);
        String[] options = { "OK"};
        JOptionPane.showOptionDialog(null, forSuffix, "Enter Segmented Image Suffix", JOptionPane.NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return suffix.getText();
    }
}
