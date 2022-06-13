package com.smiles.v2.main.views.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.ScrollPane;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeListInterface;
import com.smiles.v2.main.views.events.AddSmileHEvent;
import com.smiles.v2.main.views.events.AddSmileHListener;
import com.smiles.v2.main.views.panels.OptionPanel;

@SuppressWarnings("java:S1948")
public final class OptionPanel extends javax.swing.JPanel
        implements MoleculeListInterface, ItemListener, AddSmileHListener {
    private MoleculeListInterface moleculeList;

    private List<JCheckBox> substituentCheckBox;
    private JPanel panelWithCheckBox;
    private SmileVerificationInterface smileVerify;
    private MoleculePanel moleculePreview;
    public OptionPanel(MoleculeListInterface smilesList, SmileVerificationInterface smileVerify,MoleculePanel moleculePreview) {
        super();
        if (smilesList == null) {
            throw new NullPointerException("SmilesList is null");
        } else {
            this.moleculeList = smilesList;
        }
        if (smileVerify == null) {
            throw new NullPointerException("SmileVerificationInterface is null");
        } else {
            this.smileVerify = smileVerify;
        }
        if (moleculePreview == null) {
            throw new NullPointerException("MoleculePanel is null");
        } else {
            this.moleculePreview = moleculePreview;
        }
        initComponents();
        smilesToCheckBox();
    }

    private void initComponents() {
        ScrollPane scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        JLabel label = new JLabel("Add Substituent");
        JButton addSmileButton = new JButton("+");
        addSmileButton.addActionListener(e -> new WindowsToAddSmile(smileVerify, this));
        panelWithCheckBox = new JPanel();

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 0;
        add(label, gbcPanel);
        gbcPanel.gridx = 1;
        add(addSmileButton, gbcPanel);
        scroll.setPreferredSize(new Dimension(170, 250));
        scroll.setMaximumSize(new Dimension(190, 300));
        scroll.setMinimumSize(new Dimension(160, 100));
        gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 1;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 0;
        gbcPanel.weighty = 1;
        gbcPanel.anchor = GridBagConstraints.NORTH;
        add(scroll, gbcPanel);
        scroll.add(panelWithCheckBox);
    }

    private void smilesToCheckBox() {
        List<Molecule> smilesHList = moleculeList.getMoleculeListMolecule();
        panelWithCheckBox.setLayout(new GridBagLayout());
        substituentCheckBox = new ArrayList<>();
        int fila = 0;
        for (SmilesHInterface smilesH : smilesHList) {
            addToPanelCheckBox(fila, smilesH);
            fila++;
        }
        repaint();
    }

    private void addToPanelCheckBox(int fila, SmilesHInterface smileH) {
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.anchor = GridBagConstraints.WEST;
        JCheckBox checkBox = new JCheckBox(smileH.getName());
        checkBox.addItemListener(this);
        substituentCheckBox.add(checkBox);
        gbcPanel.gridy = fila;
        gbcPanel.gridx = 0;
        panelWithCheckBox.add(checkBox, gbcPanel);

    }
    /**
     * Return of list the molecules selected
     */
    @Override
    public List<Molecule> getMoleculeListMolecule() {
        List<Molecule> moleculesSelected = new ArrayList<>();
        for (JCheckBox checkBox : substituentCheckBox) {
            if (checkBox.isSelected()) {
                Molecule molecule = this.moleculeList.getMolecule(checkBox.getText());
                moleculesSelected.add(molecule);
            }
        }
        return moleculesSelected;
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {

        if (arg0.getStateChange() == ItemEvent.SELECTED) {
            JCheckBox checkBox = (JCheckBox) arg0.getSource();
            String name = checkBox.getText();
            Molecule molecule = moleculeList.getMolecule(name);
            moleculePreview.setMolecule(molecule);
        }
    }

    @Override
    public int addSmiles(String name, String smi, String message, boolean hydrogen) {
        int numberSmile = moleculeList.addSmiles(name, smi, message, hydrogen);
        addToPanelCheckBox(numberSmile, moleculeList.getMoleculeListMolecule().get(numberSmile));
        return numberSmile;
    }

    @Override
    public int addSmiles(SmilesHInterface smile) {
        int numberSmile = moleculeList.addSmiles(smile);
        addToPanelCheckBox(numberSmile,
                moleculeList.getMoleculeListMolecule().get(numberSmile));

        revalidate();
        repaint();

        return numberSmile;
    }
    @Override
    public Molecule getMolecule(String name) {
        return moleculeList.getMolecule(name);
    }
    @Override
    public void addSmileHEvent(AddSmileHEvent evt) {
        addSmiles(evt.getSmileH());
    }


}
