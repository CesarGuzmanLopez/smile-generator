package com.smiles.v2.main.framework.cdk.views;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.framework.cdk.MoleculeData;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;
import javax.vecmath.Point2d;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

@SuppressWarnings("java:S1948")
abstract class JpanelDrawMoleculeAbstract extends JPanel {
    private IAtomContainer iAtomContainer;
    private Molecule molecule;
    private AtomContainerRenderer renderer;

    protected JpanelDrawMoleculeAbstract(final Molecule molecule) {
        setBackground(Color.white);
        this.molecule = molecule;
        final MoleculeData moleculeData = (MoleculeData) molecule.getMoleculeData();
        this.iAtomContainer = moleculeData.getMoleculeContainer();
    }

    /**
     * @return the molecule painted.
     */
    protected Molecule getMolecule() {
        return molecule;
    }

    /**
     * @param atom to find.
     * @return point of a atom.
     */
    protected Point2d pointToScreen(final IAtom atom) {
        return renderer.toScreenCoordinates(atom.getPoint2d().x, atom.getPoint2d().y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics graphicsJpanel) {
        super.paintComponent(graphicsJpanel);
        final float width = getWidth();
        final float height = getHeight();
        graphicsJpanel.setFont(new FontUIResource("Arial", Font.PLAIN, 12));
        final StructureDiagramGenerator sdg;
        // layout the molecule
        sdg = new StructureDiagramGenerator();
        sdg.setMolecule(iAtomContainer, false);
        try {
            sdg.generateCoordinates();
        } catch (CDKException e) {
            e.printStackTrace();
        }
        // make generators
        final List<IGenerator<IAtomContainer>> generators = new ArrayList<>();

        generators.add(new BasicGenerator());
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());
        renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        RendererModel model;
        model = renderer.getRenderer2DModel();
        model.set(BasicAtomGenerator.ShowExplicitHydrogens.class, true);
        model.set(BasicAtomGenerator.ShowEndCarbons.class, true);
        model.set(BasicAtomGenerator.CompactAtom.class, false);
        model.set(BasicAtomGenerator.AtomRadius.class, 0.5);
        model.set(BasicAtomGenerator.CompactShape.class, BasicAtomGenerator.Shape.OVAL);
        model.set(BasicAtomGenerator.KekuleStructure.class, false);
        model.set(BasicBondGenerator.BondWidth.class, 1.0);
        model.set(BasicSceneGenerator.FitToScreen.class, true);
        model.set(BasicSceneGenerator.ShowMoleculeTitle.class, true);
        model.set(BasicSceneGenerator.BackgroundColor.class, Color.white);
        renderer.paint(iAtomContainer, new AWTDrawVisitor((Graphics2D) graphicsJpanel),
                new Rectangle2D.Double(0, 0, width, height), false);
        paintHerder(graphicsJpanel);

    }

    /**
     * Paint the header of the molecule.
     *
     * @param graphicsJpanel
     */
    abstract void paintHerder(Graphics graphicsJpanel);

    /**
     * if the tolerance is lower if the number of atoms is greater.
     *
     * @return the tolerance of the molecule selected.
     */
    protected double tolerance() {
        if (molecule.getNumberAtoms() < 6) {
            return 20;
        }
        if (molecule.getNumberAtoms() < 9) {
            return 18;
        }
        if (molecule.getNumberAtoms() < 13) {
            return 15;
        }
        if (molecule.getNumberAtoms() < 18) {
            return 10;
        }
        return 5;
    }
}
