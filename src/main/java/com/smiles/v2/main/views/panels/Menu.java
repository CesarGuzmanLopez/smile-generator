package com.smiles.v2.main.views.panels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.views.About;
import com.smiles.v2.main.views.PrincipalView;

public class Menu extends JMenuBar {
    private PrincipalView principalView;

    public Menu(final PrincipalView principalView) {
        super();
        final JMenu menuFile = new JMenu("File");
        add(menuFile);
        final JMenu menuHelp = new JMenu("Help");
        add(menuHelp);
        this.principalView = principalView;

        final JMenuItem menuSaveImage = new JMenuItem("Save Image");
        menuFile.add(menuSaveImage);
        menuSaveImage.addActionListener(e -> savePrincipal());

        final JMenuItem about = new JMenuItem("About");
        menuHelp.add(about);
        about.addActionListener(e -> showAbout());

    }
    /** Show the about dialog. */

    private void showAbout() {
        About about = new About();
        about.setVisible(true);
    }
    /** Save the principal view. */
    private void savePrincipal() {
        Molecule molecule = principalView.getPrincipal();
        if (molecule == null) return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(principalView);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage bi = molecule.getImage(700, 700, molecule.getName());
            try {
                ImageIO.write(bi, "png", fileToSave);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }

}
