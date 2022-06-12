package com.smiles.v2.main.framework.cdk;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.AtomInterface;
import java.awt.BasicStroke;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;


import javax.vecmath.Point2d;

import org.openscience.cdk.interfaces.IAtom;

@SuppressWarnings("java:S1948")
public class JpanelDrawMoleculeSelectedAtoms extends JpanelDrawMoleculeAbstract {
    private static final long serialVersionUID = 1L;
    private List<AtomInterface> atoms ;

    public JpanelDrawMoleculeSelectedAtoms(Molecule molecule) {
        super(molecule);
        atoms = molecule.getMoleculeData().getListAtoms();
        addMouseListener(new SelectedAtom());

    }


    class SelectedAtom extends MouseAdapter {
        @Override

        public void mouseClicked(MouseEvent arg0) {
            Point2d mousePos = new Point2d(arg0.getX(), arg0.getY());
            double tolerance =  tolerance();
            for (AtomInterface  atom : atoms) {
                IAtom iAtom =((AtomCDK)atom).getIAtom();
                if(pointToScreen(iAtom).distance(mousePos) < tolerance){
                    molecule.getMoleculeData().selectOrderAtom(atom);
                }
            }
            revalidate();
            repaint();

        }
    }

    @Override
    void paintHerder( Graphics moleculePanel) {
        List<AtomInterface> selectedAtoms =
        molecule.getMoleculeData().getListAtomsSelected();
        double tolerance = tolerance();
        int conSelect =1;
        for (AtomInterface atom : selectedAtoms) {
            IAtom iAtom =((AtomCDK)atom).getIAtom();

            float[] dashingPattern = {12f, 6f};

            Stroke stroke = new BasicStroke((int) ((tolerance)/4), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern, 0.0f);
            ((Graphics2D) moleculePanel).setStroke(stroke);

            moleculePanel.setColor(new Color(0xff,0x40 ,0x40 ));

            moleculePanel.drawOval( (int)( pointToScreen(iAtom).x-tolerance) , (int)( pointToScreen(iAtom).y-tolerance),(int) tolerance*2,(int) tolerance*2);

            moleculePanel.setColor(new Color(0x79,0x79 ,0x79 ));
            moleculePanel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            ((Graphics2D) moleculePanel).drawString(Integer.toString(conSelect), (int)( pointToScreen(iAtom).x-tolerance)  +(int) tolerance*2, (int)( pointToScreen(iAtom).y-tolerance));
            conSelect++;
        }


    }




}
