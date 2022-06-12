package com.smiles.v2.main.framework.cdk;

import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

import com.smiles.v2.main.domain.models.Molecule;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;
import javax.vecmath.Point2d;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.awt.Font;
import java.awt.Graphics;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
@SuppressWarnings("java:S1948")
abstract class JpanelDrawMoleculeAbstract extends JPanel {
    protected IAtomContainer iAtomContainer;

    protected Molecule molecule;
    protected RendererModel model;
    protected AtomContainerRenderer renderer;

    protected  JpanelDrawMoleculeAbstract(Molecule molecule) {
        this.molecule = molecule;
        MoleculeData moleculeData = (MoleculeData) molecule.getMoleculeData();
        this.iAtomContainer = moleculeData.getMoleculeContainer();
    }
    protected Point2d pointToScreen(IAtom atom){
        return renderer.toScreenCoordinates(atom.getPoint2d().x, atom.getPoint2d().y);
    }
    @Override
    protected void paintComponent(Graphics graphicsJpanel) {
        super.paintComponent(graphicsJpanel);
        float width = getWidth();
        float height = getHeight();
        graphicsJpanel.setFont(new FontUIResource("Arial", Font.PLAIN, 12));
        StructureDiagramGenerator sdg ;
        // layout the molecule
        sdg = new StructureDiagramGenerator();
        sdg.setMolecule(iAtomContainer,false);
        try {
            sdg.generateCoordinates();
        } catch (CDKException e) {
            e.printStackTrace();
        }
        // make generators
        List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();

        generators.add(new BasicGenerator());
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());

        // setup the renderer
        renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        model = renderer.getRenderer2DModel();
        if(molecule.hasHydrogenImplicit())
            model.set(BasicAtomGenerator.ShowExplicitHydrogens.class,true);

        model.set(BasicAtomGenerator.CompactAtom.class, false);
        model.set(BasicAtomGenerator.AtomRadius.class, 5.0);
        model.set(BasicAtomGenerator.CompactShape.class, BasicAtomGenerator.Shape.OVAL);
        model.set(BasicAtomGenerator.KekuleStructure.class, false);
        model.set(BasicBondGenerator.BondWidth.class, 2.0);
        model.set(BasicBondGenerator.BondWidth.class, 2.0);
        model.set(BasicSceneGenerator.FitToScreen.class, true);
        model.set(BasicSceneGenerator.Scale.class,10.0 );
        model.set(BasicSceneGenerator.ShowMoleculeTitle.class, true);
        //((Graphics2D) graphicsJpanel).fill(new Rectangle2D.Double(0, 0, width,height ));

        renderer.paint(iAtomContainer, new AWTDrawVisitor((Graphics2D) graphicsJpanel),
        new Rectangle2D.Double(0, 0, width, height), false);
        paintHerder(graphicsJpanel);

    }
    abstract void paintHerder( Graphics graphicsJpanel);
    double tolerance() {
        if (molecule.getNumberAtoms() < 6)
            return 20;
        if (molecule.getNumberAtoms() < 9)
            return 18;
        if ( molecule.getNumberAtoms() < 13)
            return 15;
        if (molecule.getNumberAtoms() < 18)
            return 10;
        return 5;
    }
}
