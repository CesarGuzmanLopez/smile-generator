package com.smiles.v2.main.framework.cdk;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.exception.CDKException;
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
import org.openscience.cdk.renderer.generators.standard.StandardGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

/**
 * Class CDKMolecule.
 *
 * @author Cesar G G L
 * @version 1.0
 * @since 1.0
 * @Charset: UTF-8
 *
 */
public class GenerateImage {
    private MoleculeData molecule;
    private String title;
    GenerateImage(final MoleculeData moleculeData,String title) {
        this.molecule = moleculeData;
        this.title = title;
    }

    /**
     * Generate Molecule of MoleculeData.
     *
     * @param width  of image.
     * @param height of image.
     * @return BufferedImage.
     *
     */
    public BufferedImage getImage(final int width, final int height) {

        final StructureDiagramGenerator sdg;
        // layout the molecule
        sdg = new StructureDiagramGenerator();
        sdg.setAlignMappedReaction(true);
        IAtomContainer containerMolecules = molecule.getMoleculeContainer();
        containerMolecules.setTitle(molecule.absoluteSmile());
        sdg.setMolecule(containerMolecules, false);
        try {
            sdg.generateCoordinates();
        } catch (CDKException e) {
            throw new RuntimeException(e.getMessage());
        }
        // make generators
        final List<IGenerator<IAtomContainer>> generators = new ArrayList<>();
        final StandardGenerator genera = new StandardGenerator(new Font("Arial", Font.BOLD, 0));

        generators.add(new BasicGenerator());
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());
        if (molecule.getMolecule().atomCount() == 1) {
            generators.add(genera);
        }
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        RendererModel model;
        model = renderer.getRenderer2DModel();
        model.getParameter(BasicAtomGenerator.ShowExplicitHydrogens.class).setValue(true);
        model.set(BasicAtomGenerator.ShowEndCarbons.class, true);
        model.set(BasicAtomGenerator.CompactAtom.class, false);
        model.set(BasicAtomGenerator.AtomRadius.class, 0.5); // UNCHECK
        model.set(BasicAtomGenerator.CompactShape.class, BasicAtomGenerator.Shape.OVAL);
        model.set(BasicAtomGenerator.KekuleStructure.class, false);
        model.set(BasicBondGenerator.BondWidth.class, 2.0);// UNCHECK
        model.set(BasicSceneGenerator.FitToScreen.class, true);
        model.set(BasicSceneGenerator.BackgroundColor.class, Color.white);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        renderer.paint(containerMolecules, new AWTDrawVisitor(graphics2D), new Rectangle2D.Double(0, 0, width, height),
                false);
        // TODO model.setHighlightedBond(highlightedBond(containerMolecules));

        graphics2D.setColor(Color.black);
        Font font = new Font("Arial", Font.BOLD, 30);// UNCHECK
        graphics2D.setFont(font);
        String smile = molecule.absoluteSmile();
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        int x = (width - metrics.stringWidth(smile)) / 2;
        if(title != null) graphics2D.drawString(title, x, height - 30);

        return image;
    }

}
