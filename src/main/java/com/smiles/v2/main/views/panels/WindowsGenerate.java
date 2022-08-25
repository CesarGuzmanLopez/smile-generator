package com.smiles.v2.main.views.panels;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import com.smiles.v2.main.domain.generator.WriteAndGenerate;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import java.awt.GridBagLayout;
import java.io.File;
import java.awt.GridBagConstraints;

@SuppressWarnings("java:S1948")
public class WindowsGenerate extends JFrame {

    private JSpinner rSubstitutes;
    private Molecule principal;
    private MoleculesList moleculeList;
    private File saveFileListSmile;
    private File saveFileListDescriptive;
    private JSpinner numBounds;
    private JFileChooser savePath;
    private String path;
    private JLabel selectedFolder;

    public WindowsGenerate(final Molecule principal, final MoleculesList moleculeList) {
        setTitle("Generate");
        setSize(550, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        initialize();
        this.principal = principal;
        this.moleculeList = moleculeList;
        setVisible(true);
        if (principal.hasHydrogenImplicit()) {
            numBounds.setEnabled(true);
        }
        path = System.getProperty("user.dir");
    }

    /** Genera View Principal generate. */
    void generate() {
        saveFileListDescriptive = new File(savePath.getSelectedFile().getAbsolutePath(), principal.getName() + ".log");
        saveFileListSmile = new File(savePath.getSelectedFile().getAbsolutePath(), principal.getName() + ".smiles");

        try {
            WriteAndGenerate.verifyEntry(principal, moleculeList, (int) rSubstitutes.getValue(),
                    saveFileListDescriptive, saveFileListSmile);
            final WriteAndGenerate generator = new WriteAndGenerate(moleculeList, principal,
                    (int) rSubstitutes.getValue(), (int) numBounds.getValue(), saveFileListDescriptive,
                    saveFileListSmile);

            File directory = new File(savePath.getSelectedFile().getAbsolutePath(), principal.getName());
            if (!directory.exists()) {
                directory.mkdir();
            }
            generator.setSaveImages(directory.getAbsolutePath());
            generator.generate();
        } catch (Exception e) { // NOSONAR
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
        JOptionPane.showMessageDialog(this, "Generate", "Generate", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initialize() {
        final GridBagConstraints gbc = new GridBagConstraints();
        final JLabel labelSmileName = new JLabel("Path select to save: ");
        labelSmileName.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSmileName, gbc);
        final JButton selectSaveFolder = new JButton("Select path");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectSaveFolder.addActionListener(e -> selectSave());
        add(selectSaveFolder, gbc);
        selectedFolder = new JLabel("");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(selectedFolder, gbc);
        final JLabel labelSmileDesc = new JLabel("r-substitutions: ");
        labelSmileDesc.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(labelSmileDesc, gbc);
        rSubstitutes = new JSpinner();
        rSubstitutes.setValue(1);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(rSubstitutes, gbc);
        final JLabel num = new JLabel("bounds: ");
        num.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(num, gbc);
        numBounds = new JSpinner();
        numBounds.setValue(1);
        numBounds.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(numBounds, gbc);
        final JButton cancelButton = new JButton("cancel");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton, gbc);
        final JButton generateButton = new JButton("Generate");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        generateButton.addActionListener(e -> generate());
        add(generateButton, gbc);
    }

    private void selectSave() {
        savePath = new JFileChooser();
        savePath.setCurrentDirectory(new File(path));
        savePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int outPut = savePath.showSaveDialog(this);
        savePath.setAcceptAllFileFilterUsed(false);
        path = savePath.getCurrentDirectory().getPath();
        if (outPut == JFileChooser.APPROVE_OPTION) {
            selectedFolder.setText("Selected");
        } else {
            savePath = null;
            selectedFolder.setText("");
        }
    }
}
