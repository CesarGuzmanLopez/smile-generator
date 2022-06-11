package com.smiles.v2.main.views.panels;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.util.EventListener;
import java.awt.GridBagConstraints;

import com.smiles.v2.main.domain.models.Smiles;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.views.events.AddSmileHEvent;
import com.smiles.v2.main.views.events.AddSmileHListener;


@SuppressWarnings("java:S1948")
public class WindowsToAddSmile extends JFrame implements EventListener  {
    private SmileVerificationInterface smileVerify;
    private AddSmileHListener addSmileHListener;
    public WindowsToAddSmile(SmileVerificationInterface smileVerify,AddSmileHListener addSmileHListener) {
        super();
        if(smileVerify == null) {
            throw new NullPointerException("SmileVerificationInterface is null");
        } else {
            this.smileVerify = smileVerify;
        }
        setTitle("Add Substituent");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        this.addSmileHListener = addSmileHListener;
        initialize();
        setVisible(true);

    }
    private void initialize() {

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelSmile = new JLabel("Smile: ");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelSmile,gbc);

        JTextField textFieldSmile = new JTextField();
        textFieldSmile.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(textFieldSmile,gbc);

        JLabel labelSmileName = new JLabel("Name: ");
        labelSmileName.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSmileName,gbc);

        JTextField textFieldSmileName = new JTextField();
        textFieldSmileName.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(textFieldSmileName,gbc);

        JLabel labelSmileDesc = new JLabel("Description: ");
        labelSmileDesc.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labelSmileDesc,gbc);

        JTextField textFieldSmileDesc = new JTextField();
        textFieldSmileDesc.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(textFieldSmileDesc,gbc);

        JLabel hydrogensImplicit = new JLabel("Hydrogens are implicit: ");
        hydrogensImplicit.setPreferredSize(new java.awt.Dimension(210, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(hydrogensImplicit,gbc);

        JCheckBox checkBoxHydrogenImplicit = new JCheckBox();
        checkBoxHydrogenImplicit.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(checkBoxHydrogenImplicit,gbc);

        JButton cancelButton = new JButton("cancel");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton,gbc);
        JButton addButton = new JButton("Add");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addButton.addActionListener(e -> add(textFieldSmile.getText(),textFieldSmileName.getText(),textFieldSmileDesc.getText(),checkBoxHydrogenImplicit.isSelected()));
        add(addButton,gbc);
    }
    void add(String smile, String name, String description, boolean hydrogensImplicit){
        try{
            SmilesHInterface smilesFactory = new Smiles (name,smile,description,hydrogensImplicit,smileVerify);
            addSmileHListener.addSmileHEvent( new AddSmileHEvent(this,smilesFactory));
            dispose();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Invalid smile\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }



}
