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
import com.smiles.v2.main.interfaces.SmilesListInterface;
import com.smiles.v2.main.views.events.AddSmileHEvent;
import com.smiles.v2.main.views.events.AddSmileHListener;
import com.smiles.v2.main.views.panels.OptionPanel;

@SuppressWarnings("java:S1948")
public final class OptionPanel extends javax.swing.JPanel
        implements SmilesListInterface, ItemListener, AddSmileHListener {
    private SmilesListInterface smilesList;

    private List<JCheckBox> substituentCheckBox;
    private JPanel panelWithCheckBox;
    protected SmileVerificationInterface smileVerify;

    public OptionPanel(SmilesListInterface smilesList, SmileVerificationInterface smileVerify) {
        super();
        if (smilesList == null) {
            throw new NullPointerException("SmilesList is null");
        } else {
            this.smilesList = smilesList;
        }
        if (smileVerify == null) {
            throw new NullPointerException("SmileVerificationInterface is null");
        } else {
            this.smileVerify = smileVerify;
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
        List<SmilesHInterface> smilesHList = smilesList.getSmilesHList();
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

    @Override
    public List<SmilesHInterface> getSmilesHList() {
        // TODO Lista de smiles que se seleccionaron
        return null;
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public int addSmiles(String name, String smi, String message, boolean hydrogen) {
        int numberSmile = smilesList.addSmiles(name, smi, message, hydrogen);
        addToPanelCheckBox(numberSmile, smilesList.getSmilesHList().get(numberSmile));
        return numberSmile;

    }

    @Override
    public int addSmiles(SmilesHInterface smile) {
        int numberSmile = smilesList.addSmiles(smile);
        addToPanelCheckBox(numberSmile,
                smilesList.getSmilesHList().get(numberSmile));
        revalidate();
        repaint();

        return numberSmile;
    }

    @Override
    public void addSmileHEvent(AddSmileHEvent evt) {
        addSmiles(evt.getSmileH());
    }
}
