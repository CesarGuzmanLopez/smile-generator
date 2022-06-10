package com.smiles.v2.main.views.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagLayout;


import com.smiles.v2.main.interfaces.SmilesH;
import com.smiles.v2.main.interfaces.SmilesList;

public class OptionPanel extends javax.swing.JPanel implements SmilesList, ItemListener {
    List<SmilesH> smilesList;
    public OptionPanel(SmilesList smilesList) {
        super();
        if(smilesList == null) {
            throw new NullPointerException("SmilesList is null");
        } else {
            this.smilesList = smilesList.getSmilesHList();
        }
        initComponents();
    }
    private void initComponents(){
        setBorder(javax.swing.BorderFactory.createTitledBorder("substituent"));
        setLayout(new GridBagLayout());
        List<JCheckBox> checkBoxList = new ArrayList<>();
        for(SmilesH smilesH : smilesList){
            JCheckBox checkBox = new JCheckBox(smilesH.getName());
            checkBox.addItemListener(this);
            checkBoxList.add(checkBox);
            add(checkBox);
        }
        repaint();
    }
    @Override
    public List<SmilesH> getSmilesHList() {
        // TODO Lista de smiles que se seleccionaron
        return null;
    }
    @Override
    public void itemStateChanged(ItemEvent arg0) {
        // TODO Auto-generated method stub

    }


}
