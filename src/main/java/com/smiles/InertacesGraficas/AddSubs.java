package com.smiles.InertacesGraficas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.smiles.Controladores.GraphicController;

import java.awt.TextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddSubs extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {

            Substituent a = null;
            AddSubs dialog = new AddSubs(a, null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public String Name;
    public String Smile;
    boolean Impli;
    boolean termina;
    public AddSubs(Substituent a,GraphicController Pantalla) {
        setType(Type.POPUP);
        setResizable(false);
        setAlwaysOnTop(true);
        setBounds(100, 100, 321, 197);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        TextField textField = new TextField();
        textField.setBounds(155, 33, 140, 22);
        contentPanel.add(textField);
        
        TextField textField_1 = new TextField();
        textField_1.setBounds(155, 61, 140, 22);
        contentPanel.add(textField_1);
        
        JCheckBox chckbxHydrogenImpl = new JCheckBox("Explicit Hydrogens");
        chckbxHydrogenImpl.setBounds(153, 89, 142, 23);
        contentPanel.add(chckbxHydrogenImpl);
        
        JLabel lblNameMolecule = new JLabel("Name Molecule");
        lblNameMolecule.setBounds(10, 36, 100, 14);
        contentPanel.add(lblNameMolecule);
        
        JLabel lblSmile = new JLabel("Smile");
        lblSmile.setBounds(10, 69, 100, 14);
        contentPanel.add(lblSmile);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        Name=textField.getText();
                        Smile=textField_1.getText().replaceAll(" ","");
                        if(Smile.length()==0)Smile = Name;
                        if(Name.length()==0)Name=Smile;
                        if(a.AddSubs(Smile, Name,!chckbxHydrogenImpl.isSelected())) {
                            textField.setText("");
                            textField_1.setText("");
                            chckbxHydrogenImpl.setSelected(false);
                            setVisible(false);
                            termina=true;
                            Pantalla.llena_panel();
                            Pantalla.scrollPane_3.removeAll();
                            Pantalla.scrollPane_3.add(Pantalla.panel_2);
                            Pantalla.scrollPane_3.repaint();
                        }else {
                            JOptionPane.showMessageDialog(null, "Incorrect smile.");
                        }
                        
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        textField.setText("");
                        textField_1.setText("");
                        chckbxHydrogenImpl.setSelected(false);
                        setVisible(false);
                        termina=true;
                    }

                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                
            }
        }
    }
    

    
    
//    public  String Valor() {
    //    return Valora;
//    }


}
