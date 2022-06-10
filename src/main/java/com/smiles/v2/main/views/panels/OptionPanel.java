package com.smiles.v2.main.views.panels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.smiles.v2.main.interfaces.SmilesH;
import com.smiles.v2.main.interfaces.SmilesList;

@SuppressWarnings("java:S1948")
public class OptionPanel extends javax.swing.JPanel implements SmilesList, ItemListener {
    SmilesList smilesList;
    public OptionPanel(SmilesList smilesList) {
        super();
        if(smilesList == null) {
            throw new NullPointerException("SmilesList is null");
        } else {
            this.smilesList = smilesList;
        }
        initComponents();
    }
    private void initComponents(){
        List<SmilesH> smilesHList = smilesList.getSmilesHList();
        setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx=0;
        gbcPanel.anchor=GridBagConstraints.WEST;

        List<JCheckBox> checkBoxList = new ArrayList<>();
        int fila = 0;

        for(SmilesH smilesH : smilesHList){
            JCheckBox checkBox = new JCheckBox(smilesH.getName());
            checkBox.addItemListener(this);
            checkBoxList.add(checkBox);
            gbcPanel.gridy= fila;
            add(checkBox,gbcPanel);
            fila++;
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
