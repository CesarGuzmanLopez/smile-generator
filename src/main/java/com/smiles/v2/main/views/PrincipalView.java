package com.smiles.v2.main.views;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import java.awt.GridBagConstraints;
import com.smiles.v2.main.views.panels.Menu;
import com.smiles.v2.main.views.panels.MoleculePanel;
import com.smiles.v2.main.views.panels.OptionPanel;
import com.smiles.v2.main.views.panels.WindowsGenerate;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.Smiles;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.MoleculeListInterface;

@SuppressWarnings("java:S1948")
public final class PrincipalView extends javax.swing.JFrame {
    private static final long serialVersionUID = 2L;

    private MoleculePanel moleculePanelPrincipal;
    private OptionPanel optionPanel;
    private MoleculePanel moleculePreviewPanel;
    private JButton generateButton;

    // entry point for the program
    private JTextField textFieldSmile;
    private JTextField textFieldName;
    private JCheckBox checkBoxHydrogenImplicit;
    private JButton drawSmileButton;

    // Dependencies to inject
    private SmileVerificationInterface verifySmile;
    private MoleculeListInterface smilesList;
    private MoleculeGraphPainterInterface moleculeGraphPainter;

    private MoleculeDataFactoryInterface moleculeFactory;

    public PrincipalView(MoleculeListInterface smilesList,
            SmileVerificationInterface verifySmile,
            MoleculeGraphPainterInterface moleculeGraphPainter,
            MoleculeDataFactoryInterface moleculeFactory) {
        super("Smile generator");
        setSize(850, 550);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.smilesList = smilesList;
        this.verifySmile = verifySmile;
        this.moleculeGraphPainter = moleculeGraphPainter;
        this.moleculeFactory = moleculeFactory;

    }

    private void createAndDrawSmile() {
        String smile = textFieldSmile.getText();
        if (!verifySmile.isValid(smile)) {
            moleculePanelPrincipal.setMolecule(null);
            generateButton.setEnabled(false);
        }
        String name = textFieldName.getText();
        boolean implicitHydrogen = checkBoxHydrogenImplicit.isSelected();
        try {
            Smiles smileH = new Smiles(name, smile, "Select hydrogens to replace", implicitHydrogen, verifySmile);
            moleculePanelPrincipal
                    .setMolecule(new Molecule(smileH, verifySmile,  moleculeFactory));
        } catch (Exception e) {
            generateButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        generateButton.setEnabled(true);
    }
    private void generate(){
        MoleculesList selected = MoleculesList.createMoleculesList(verifySmile, moleculeFactory, optionPanel.getMoleculeListMolecule());
        new WindowsGenerate(moleculePanelPrincipal.getMolecule(), selected);
    }

    public void initialize() {
        setJMenuBar(new Menu());
        setLayout(new GridBagLayout());
        //the order is important in the initialization of the components
        initializeEntrySmile(0, 0, 1, 0);
        initializeMoleculePanelPrincipal(0, 1, 1, 1);
        initializeMoleculePreviewPanel(2, 1, 1, 1);
        initializeOptionPanel(1, 1, 0, 1);
        initializeActionGeneratorPanel(0, 3, 3, 1);
        drawSmileButton.addActionListener(e -> createAndDrawSmile());
        generateButton.addActionListener(e -> generate());
        textFieldSmile.getDocument().addDocumentListener(new ChangeTextFieldSmile());
        drawSmileButton.setEnabled(false);
        generateButton.setEnabled(false);
        checkBoxHydrogenImplicit.setSelected(true);
        setVisible(true);
    }
    private void initializeEntrySmile(int gridx, int gridy, double weightx, double weighty) {
        JPanel panelSmile = new JPanel();
        panelSmile.setLayout(new GridBagLayout());
        panelSmile.setBorder(javax.swing.BorderFactory.createTitledBorder("Entry"));

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.ipadx = 10;
        JLabel labelSmile = new JLabel("Smile: ");
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        panelSmile.add(labelSmile, gbcPanel);

        textFieldSmile = new JTextField();
        textFieldSmile.setPreferredSize(new Dimension(200, 30));
        textFieldSmile.setMinimumSize(new Dimension(100, 30));
        gbcPanel.gridx = 1;
        gbcPanel.gridy = 0;
        panelSmile.add(textFieldSmile, gbcPanel);
        checkBoxHydrogenImplicit = new JCheckBox("Implicit hydrogen");

        JLabel labelSmileName = new JLabel("Name: ");
        gbcPanel.gridx = 2;
        gbcPanel.gridy = 0;
        panelSmile.add(labelSmileName, gbcPanel);

        textFieldName = new JTextField();
        textFieldName.setPreferredSize(new Dimension(200, 30));
        textFieldName.setMinimumSize(new Dimension(100, 30));
        gbcPanel.gridx = 3;
        gbcPanel.gridy = 0;
        panelSmile.add(textFieldName, gbcPanel);

        gbcPanel.gridx = 4;
        gbcPanel.gridy = 0;
        gbcPanel.anchor = GridBagConstraints.EAST;
        panelSmile.add(checkBoxHydrogenImplicit, gbcPanel);
        gbcPanel.ipadx = 0;
        gbcPanel.anchor = GridBagConstraints.EAST;

        gbcPanel.gridx = 5;
        gbcPanel.gridy = 0;
        drawSmileButton = new JButton("Draw");
        panelSmile.add(drawSmileButton, gbcPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth = 3;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new java.awt.Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(panelSmile, gbc);
    }

    private void initializeMoleculePanelPrincipal(int gridx, int gridy, double weightx, double weighty) {
        moleculePanelPrincipal = new MoleculePanel(moleculeGraphPainter);
        moleculePanelPrincipal.setPreferredSize(new Dimension(300, 300));
        moleculePanelPrincipal.setMaximumSize(new Dimension(400, 400));
        moleculePanelPrincipal.setMinimumSize(new Dimension(200, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.CENTER;
        add(moleculePanelPrincipal, gbc);
    }

    private void initializeOptionPanel(int gridx, int gridy, double weightx, double weighty) {

        optionPanel = new OptionPanel(smilesList, verifySmile,moleculePreviewPanel);

        optionPanel.setPreferredSize(new Dimension(200, 300));
        optionPanel.setMaximumSize(new Dimension(250, 400));
        optionPanel.setMinimumSize(new Dimension(190, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.CENTER;
        add(optionPanel, gbc);
    }

    private void initializeMoleculePreviewPanel(int gridx, int gridy, double weightx, double weighty) {
        moleculePreviewPanel = new MoleculePanel(moleculeGraphPainter);
        moleculePreviewPanel.setPreferredSize(new Dimension(200, 200));
        moleculePreviewPanel.setMaximumSize(new Dimension(300, 300));
        moleculePreviewPanel.setMinimumSize(new Dimension(180, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.CENTER;
        add(moleculePreviewPanel, gbc);
    }

    private void initializeActionGeneratorPanel(int gridx, int gridy, double weightx, double weighty) {
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridBagLayout());
        panelAction.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));
        GridBagConstraints gbcPanel = new GridBagConstraints();


        generateButton = new JButton("Generate");
        panelAction.add(generateButton, gbcPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(panelAction, gbc);
    }

    class ChangeTextFieldSmile implements DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }

        public void change() {
            String textSmile = textFieldSmile.getText();
            drawSmileButton.setEnabled(textSmile.length() > 0);

            if (textSmile.length() == 0) {
                textFieldName.setText(textSmile);
                return;
            }
            String textName = textFieldName.getText();
            if (textName.length() == 0) {
                textFieldName.setText(textSmile);
                return;
            }
            if (textName.equals(textSmile.substring(0, textSmile.length() - 1))) {
                textFieldName.setText(textSmile);
                return;
            }
            if (textName.length() > textSmile.length() &&
                    textSmile.equals(textName.substring(0, textName.length() - 1))) {
                textFieldName.setText(textSmile);
            }

        }
    }
}
