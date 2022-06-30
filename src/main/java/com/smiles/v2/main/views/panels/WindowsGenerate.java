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

    public WindowsGenerate(final Molecule principal, final MoleculesList moleculeList) {
        setTitle("Generate");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        initialize();
        this.principal = principal;
        this.moleculeList = moleculeList;
        setVisible(true);
        if (principal.hasHydrogenImplicit()) numBounds.setEnabled(true);
    }

    /** Genera View Principal generate. */
    void generate() {
        try {
            WriteAndGenerate.verifyEntry(principal, moleculeList, (int) rSubstitutes.getValue(),
                    saveFileListDescriptive, saveFileListSmile);
            final WriteAndGenerate generator = new WriteAndGenerate(moleculeList, principal,
                    (int) rSubstitutes.getValue(), (int) numBounds.getValue(), saveFileListDescriptive,
                    saveFileListSmile);
            generator.generate();
        } catch (Exception e) { //NOSONAR
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose();
        JOptionPane.showMessageDialog(this, "Generate", "Generate", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initialize() {

        final GridBagConstraints gbc = new GridBagConstraints();

        final JLabel labelOutputSmiles = new JLabel("smile list output file: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelOutputSmiles, gbc);

        final JButton selectFileSmileList = new JButton("Select File");

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectFileSmileList.addActionListener(e -> selectOutputSmilesList());
        add(selectFileSmileList, gbc);

        final JLabel labelSmileName = new JLabel("descriptive list Output file: ");
        labelSmileName.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSmileName, gbc);

        final JButton selectFileDescriptiveList = new JButton("Select File");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectFileDescriptiveList.addActionListener(e -> selectOutputSmilesDescriptive());
        add(selectFileDescriptiveList, gbc);

        final JLabel labelSmileDesc = new JLabel("r-substitutions: ");
        labelSmileDesc.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labelSmileDesc, gbc);
        rSubstitutes = new JSpinner();
        rSubstitutes.setValue(1);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(rSubstitutes, gbc);

        final JLabel num = new JLabel("bounds: ");
        num.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(num, gbc);
        numBounds = new JSpinner();
        numBounds.setValue(1);
        numBounds.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(numBounds, gbc);

        final JButton cancelButton = new JButton("cancel");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton, gbc);
        final JButton generateButton = new JButton("Generate");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        generateButton.addActionListener(e -> generate());
        add(generateButton, gbc);

    }

    private void selectOutputSmilesList() {
        final JFileChooser saveFileListSmile1 = new JFileChooser();
        saveFileListSmile1.showSaveDialog(null);
        saveFileListSmile1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.saveFileListSmile = saveFileListSmile1.getSelectedFile();
    }

    private void selectOutputSmilesDescriptive() {
        final JFileChooser saveFileListDescriptive1 = new JFileChooser();
        saveFileListDescriptive1.showSaveDialog(null);
        saveFileListDescriptive1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.saveFileListDescriptive = saveFileListDescriptive1.getSelectedFile();
    }

}
