package com.bbloggsbott.manualimageselector.presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private JLabel originalImage, segmentedImage, logs;
    private JButton nextImage, deleteImage;
    private JPanel buttonsPanel, imagePanel, mainPanel;

    private String  originalImageDir, segmentedImageDir, segmentedImageSuffix;
    private ArrayList<File> originalImageFiles;
    private int imageIndex;
    private File currentImage;

    MainFrame(String originalImageDir, String segmentedImageDir) throws IOException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manual Image Selector");
        this.setLayout(new BorderLayout());

        this.originalImageDir = originalImageDir;
        this.segmentedImageDir = segmentedImageDir;
        this.segmentedImageSuffix = "_segmented";
        File folder = new File(originalImageDir);
        this.originalImageFiles = new ArrayList<>(Arrays.asList(folder.listFiles()));
        imageIndex = 0;

        BufferedImage originalImg = ImageIO.read(originalImageFiles.get(0));
        originalImage = new JLabel(new ImageIcon(originalImg));
        String segmentedImageFileName = getSegmentedImageFileName(originalImageFiles.get(0));
        System.out.println(segmentedImageFileName);
        BufferedImage segmentedImg = ImageIO.read(new File(segmentedImageFileName));
        segmentedImage = new JLabel(new ImageIcon(segmentedImg));
        nextImage = new JButton("Next");
        deleteImage = new JButton("Delete");
        logs = new JLabel();

        buttonsPanel = new JPanel(new FlowLayout());
        imagePanel = new JPanel(new GridLayout(0,2));
        mainPanel = new JPanel(new BorderLayout());

        buttonsPanel.add(deleteImage);
        buttonsPanel.add(nextImage);
        imagePanel.add(originalImage);
        imagePanel.add(segmentedImage);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(logs, BorderLayout.SOUTH);

        nextImage.addActionListener(e -> loadNextImage());
        deleteImage.addActionListener(e -> deleteCurrentImage());

        logs.setText("Done");
        setSize(900,600);
        setVisible(true);

    }

    public MainFrame(String originalImageDir, String segmentedImageDir, String segmentedImageSuffix) throws IOException{
        this(originalImageDir, segmentedImageDir);
        this.segmentedImageSuffix = segmentedImageSuffix;
    }


    private void loadNextImage(){
        imageIndex+=1;
        currentImage = originalImageFiles.get(imageIndex);
        try{
            originalImage.setIcon(new ImageIcon(ImageIO.read(currentImage)));
            segmentedImage.setIcon(new ImageIcon(ImageIO.read(new File(getSegmentedImageFileName(currentImage)))));
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    private void deleteCurrentImage(){
        String name = currentImage.getName();
        if(currentImage.delete()){
            originalImageFiles.remove(imageIndex);
            logs.setText("Deteled File "+name);
            loadNextImage();
        } else {
            logs.setText("Could not delete file "+name);
        }
    }

    private String getSegmentedImageFileName(File file){
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        String extension = name.substring(lastIndex);
        name = name.substring(0,lastIndex);
        Path segmentedPath = Paths.get(segmentedImageDir, name + segmentedImageSuffix + extension);
        return segmentedPath.toString();

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
