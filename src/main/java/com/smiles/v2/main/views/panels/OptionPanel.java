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

@SuppressWarnings("java:S1948")
public final class OptionPanel extends javax.swing.JPanel
        implements MoleculeListInterface, ItemListener, AddSmileHListener {
    private MoleculeListInterface moleculeList;

    private List<JCheckBox> substituentCheckBox;
    private JPanel panelWithCheckBox;
    private SmileVerificationInterface smileVerify;
    private MoleculePanel moleculePreview;
    public OptionPanel(final MoleculeListInterface smilesList, final SmileVerificationInterface smileVerify,
            final MoleculePanel moleculePreview) {
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
        final ScrollPane scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        final JLabel label = new JLabel("Add Substituent");
        final JButton addSmileButton = new JButton("+");
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
        final List<Molecule> smilesHList = moleculeList.getListMolecule();
        panelWithCheckBox.setLayout(new GridBagLayout());
        substituentCheckBox = new ArrayList<>();
        int fila = 0;
        for (SmilesHInterface smilesH : smilesHList) {
            addToPanelCheckBox(fila, smilesH);
            fila++;
        }
        repaint();
    }

    private void addToPanelCheckBox(final int fila, final SmilesHInterface smileH) {
        final GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.anchor = GridBagConstraints.WEST;
        final JCheckBox checkBox = new JCheckBox(smileH.getName());
        checkBox.addItemListener(this);
        substituentCheckBox.add(checkBox);
        gbcPanel.gridy = fila;
        gbcPanel.gridx = 0;
        panelWithCheckBox.add(checkBox, gbcPanel);

    }

    /**
     * Return of list the molecules selected.
     */
    @Override
    public List<Molecule> getListMolecule() {
        final List<Molecule> moleculesSelected = new ArrayList<>();
        for (JCheckBox checkBox : substituentCheckBox) {
            if (checkBox.isSelected()) {
                final Molecule molecule = this.moleculeList.getMolecule(checkBox.getText());
                moleculesSelected.add(molecule);
            }
        }
        return moleculesSelected;
    }

    @Override
    public void itemStateChanged(final ItemEvent arg0) {

        if (arg0.getStateChange() == ItemEvent.SELECTED) {
            final JCheckBox checkBox = (JCheckBox) arg0.getSource();
            final String name = checkBox.getText();
            final Molecule molecule = moleculeList.getMolecule(name);
            moleculePreview.setMolecule(molecule);
        }
    }

    @Override
    public int addSmiles(final String name, final String smi, final String message, final boolean hydrogen) {
        final int numberSmile = moleculeList.addSmiles(name, smi, message, hydrogen);
        addToPanelCheckBox(numberSmile, moleculeList.getListMolecule().get(numberSmile));
        return numberSmile;
    }

    @Override
    public int addSmiles(final SmilesHInterface smile) {
        final int numberSmile = moleculeList.addSmiles(smile);
        addToPanelCheckBox(numberSmile,
                moleculeList.getListMolecule().get(numberSmile));

        revalidate();
        repaint();

        return numberSmile;
    }

    @Override
    public Molecule getMolecule(final String name) {
        return moleculeList.getMolecule(name);
    }

    @Override
    public void addSmileHEvent(final AddSmileHEvent evt) {
        addSmiles(evt.getSmileH());
    }


}
