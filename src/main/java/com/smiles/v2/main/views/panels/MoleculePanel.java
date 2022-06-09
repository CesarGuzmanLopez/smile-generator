package com.smiles.v2.main.views.panels;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import com.smiles.v2.main.models.Molecule;
//una clase abstracta que representa un panel de una molécula
public class MoleculePanel extends javax.swing.JPanel implements ActionListener{
    protected Molecule molecule;
    public MoleculePanel() {
        super();
        setBackground(Color.white);
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

}
