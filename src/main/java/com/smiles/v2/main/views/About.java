package com.smiles.v2.main.views;

import java.io.IOException;
import java.io.InputStream;
import  java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

public class About extends JFrame {
    public About() {
        super();
        setTitle("About");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        /**
         *
         * Create the panel.
         * with the logo image and texbox with the description of the program
         */
        JPanel panel = new JPanel();

        panel.setBorder(new MatteBorder(5, 5, 5, 5, (java.awt.Color) new java.awt.Color(115, 115, 115)));
        panel.setLayout(new GridBagLayout());
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream logo1 = classloader.getResourceAsStream("logo_1.png");
        InputStream logo2 = classloader.getResourceAsStream("SMILE_logo.png");
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("About");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(label,gbc);




        JTextArea coloredLabel =new JTextArea(getTextAbout());
        coloredLabel.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(coloredLabel,gbc);


        try {
            BufferedImage logo1B = ImageIO.read(logo1);
            logo1B = resize(logo1B,100,80);
            BufferedImage logo2B = ImageIO.read(logo2);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            JLabel picLabel2 = new JLabel(new ImageIcon(logo2B));
            panel.add(picLabel2,gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            JLabel picLabel = new JLabel(new ImageIcon(logo1B));
            panel.add(picLabel,gbc);

        }catch( IOException e){
            throw new RuntimeException("image no found: "+e.getMessage());
        }

        add(panel);
        setVisible(true);

    }
    private static BufferedImage resize(final Image img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage redImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = redImg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return redImg;
    }

    private String getTextAbout() {
        String text = "";
        text += "This program is a molecular viewer and generate SMILES.\n";
        text += "The program is developed by:\n";
        text += "Cesar Gerardo Guzman Lopez(88-8@live.com.mx)\n";
        return text;
    }

}
