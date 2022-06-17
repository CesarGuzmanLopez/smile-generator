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
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

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
    private MoleculeDataFactoryInterface factory;
    private SmileVerificationInterface smileVerifier;
    public WindowsGenerate(Molecule principal,MoleculesList moleculeList,
    MoleculeDataFactoryInterface factory,
    SmileVerificationInterface smileVerifier
      ) {
        setTitle("Generate");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        initialize();
        this.principal = principal;
        this.moleculeList = moleculeList;
        setVisible(true);
        this.factory = factory;
        this.smileVerifier = smileVerifier;
    }
    void generate(){
        try{
            WriteAndGenerate.verifyEntry(moleculeList,principal,(int)rSubstitutes.getValue(),saveFileListDescriptive,saveFileListSmile);
            WriteAndGenerate generator =new WriteAndGenerate(moleculeList,principal,(int)rSubstitutes.getValue(),saveFileListDescriptive,saveFileListSmile,factory, smileVerifier);
            generator.generate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose();
        JOptionPane.showMessageDialog(this, "Generate", "Generate", JOptionPane.INFORMATION_MESSAGE);
    }
    private void initialize() {

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelOutputSmiles = new JLabel("smile list output file: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelOutputSmiles, gbc);

        JButton selectFileSmileList = new JButton("Select File");

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectFileSmileList.addActionListener(e ->selectOutputSmilesList());
        add(selectFileSmileList, gbc);


        JLabel labelSmileName = new JLabel("descriptive list Output file: ");
        labelSmileName.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSmileName, gbc);

        JButton selectFileDescriptiveList = new JButton("Select File");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectFileDescriptiveList.addActionListener(e -> selectOutputSmilesDescriptive());
        add(selectFileDescriptiveList, gbc);

        JLabel labelSmileDesc = new JLabel("r-substitutions: ");
        labelSmileDesc.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labelSmileDesc, gbc);
        rSubstitutes= new JSpinner();
        rSubstitutes.setValue(1);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(rSubstitutes,gbc);


        JButton cancelButton = new JButton("cancel");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton, gbc);
        JButton generateButton = new JButton("Generate");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        generateButton.addActionListener(e -> generate());
        add(generateButton, gbc);

    }

    private void selectOutputSmilesList(){
        JFileChooser saveFileListSmile1 = new JFileChooser();
        saveFileListSmile1.showSaveDialog(null);
        saveFileListSmile1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.saveFileListSmile = saveFileListSmile1.getSelectedFile();
    }
    private void selectOutputSmilesDescriptive(){
        JFileChooser saveFileListDescriptive1 = new JFileChooser();
        saveFileListDescriptive1.showSaveDialog(null);
        saveFileListDescriptive1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.saveFileListDescriptive = saveFileListDescriptive1.getSelectedFile();
    }

}
