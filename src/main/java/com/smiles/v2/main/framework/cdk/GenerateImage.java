package com.smiles.v2.main.framework.cdk;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.standard.StandardGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

import com.smiles.v2.main.domain.models.MoleculesAdded;

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
    private static final Color[] COLORS = {Color.BLUE, Color.GREEN, Color.LIGHT_GRAY, Color.ORANGE, Color.RED,
            Color.YELLOW, Color.PINK, Color.MAGENTA };
    private MoleculeData molecule;
    private String title;
    private IAtomContainer moleculeContainer;

    GenerateImage(final MoleculeData moleculeData, final String title) {
        this.molecule = moleculeData;
        this.title = title;
        moleculeContainer = molecule.getMoleculeContainer();
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
        Rectangle screen = new Rectangle(0, 0, width, height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        AtomContainerRenderer renderer = generateRender(containerMolecules, screen);

        int locateFirst = paintSubstitutes(renderer);
        repaintMainMolecule(renderer, locateFirst);
        renderer.paint(containerMolecules, new AWTDrawVisitor(graphics2D), screen, false);
        paintTitle(width, height, graphics2D);

        return image;
    }

    private AtomContainerRenderer generateRender(IAtomContainer molecules, Rectangle screen) { //UNCHECK
        // make generators
        List<IGenerator<IAtomContainer>> generators = new ArrayList<>();
        generators.add(new BasicSceneGenerator());
        generators.add(new StandardGenerator(new JLabel().getFont()));
        generators.add(new BasicSceneGenerator());
        // the renderer needs to have a toolkit-specific font manager
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        renderer.getRenderer2DModel().set(StandardGenerator.Highlighting.class,
                StandardGenerator.HighlightStyle.Colored);
        renderer.getRenderer2DModel().set(BasicSceneGenerator.FitToScreen.class, true);
        renderer.getRenderer2DModel().set(BasicSceneGenerator.ShowMoleculeTitle.class, true);
        renderer.getRenderer2DModel().set(BasicSceneGenerator.BackgroundColor.class, Color.white);

        renderer.setup(molecules, screen);
        return renderer;
    }

    private void paintTitle(final int width, final int height, Graphics2D graphics2D) { //UNCHECK
        graphics2D.setColor(Color.black);
        Font font = new Font("Arial", Font.BOLD, 30);// UNCHECK
        graphics2D.setFont(font);
        String smile = molecule.absoluteSmile();
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        int x = (width - metrics.stringWidth(smile)) / 2;
        if (title != null) {
            graphics2D.drawString(title, x, height - 30);// UNCHECK
        }
    }

    private void repaintMainMolecule(AtomContainerRenderer renderer, int primer) { //UNCHECK
        for (int i = 0; primer != 0 && i < primer; i++) {
            IAtom mol = moleculeContainer.getAtom(i);
            for (IBond a : mol.bonds()) {
                if (a.getOther(mol).getIndex() < primer) {
                    a.setProperty(StandardGenerator.HIGHLIGHT_COLOR, null);
                }
            }
            renderer.getRenderer2DModel().setHighlightedAtom(mol);
        }
    }

    private int paintSubstitutes(AtomContainerRenderer renderer) { //UNCHECK
        int j = 0;
        int primer = 0;
        for (MoleculesAdded molAdd : molecule.getListMoleculesAdded()) {
            if (j == 0) primer = molAdd.getLocate();
            int actual = molAdd.getLocate();
            Color color = COLORS[j++ % COLORS.length];
            for (int i = molAdd.getLocate(); i < moleculeContainer.getAtomCount(); i++) {
                IAtom mol = moleculeContainer.getAtom(i);
                mol.setProperty(StandardGenerator.HIGHLIGHT_COLOR, color);
                for (IBond a : mol.bonds()) {
                    if (a.getOther(mol).getIndex() > actual || a.getOther(mol).getIndex() < primer) {
                        a.setProperty(StandardGenerator.HIGHLIGHT_COLOR, color);
                        renderer.getRenderer2DModel().setHighlightedBond(a);
                    }
                }
                renderer.getRenderer2DModel().setHighlightedAtom(mol);
            }
        }
        return primer;
    }

}
