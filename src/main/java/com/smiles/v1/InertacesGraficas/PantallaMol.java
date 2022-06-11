package com.smiles.v1.InertacesGraficas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;


import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.standard.StandardGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.TestMoleculeFactory;

import javax.swing.JPanel;
import javax.vecmath.Point2d;

public class PantallaMol extends JPanel {
    /**
     * @return
     *
     */
    public String smile;

    private IAtomContainer molecule = TestMoleculeFactory.makeEthylPropylPhenantren();
    public List<Integer> Selec;
    public List<Point2d> CordenadasReales;

    private double k = 1;
    int x;
    int y;
    int NumExplicito;

    public PantallaMol(IAtomContainer mol, ArrayList<Integer> Select, int x, int y) {
        this.molecule = mol;
        Selec = Select;
        this.x = x;
        this.y = y;
        CordenadasReales = new ArrayList<Point2d>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Point2d a = MiPos();
                if (NumExplicito == 1) {
                    System.out.println("No hay nada");
                    return;
                }

                int i = 0;

                for (Point2d x : CordenadasReales) {
                    if (x.distance(a) < 8 * k && mol.getAtomCount() > 1) {
                        if (Selec.contains(i)) {
                            Selec.remove((Object) i);
                        } else {
                            Selec.add(i);
                        }
                    }
                    i++;
                }
                repaint();
            }
        });
        defineK();

    }

    public PantallaMol(IAtomContainer mol, ArrayList<Integer> select,
            int x) {
        this.molecule = mol;
        Point2d a = MiPos();
        Selec = select;
        CordenadasReales = new ArrayList<Point2d>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (NumExplicito <= 1 || mol.getAtomCount() <= 1)
                    return;

                int i = 0;
                for (Point2d cordenada : CordenadasReales) {
                    if (cordenada.distance(a) < 8 ) {
                        if (Selec.contains(i))
                            Selec.remove((Object) i);
                        else
                            Selec.add(i);
                    }
                    i++;
                }
                repaint();
                defineK();
            }
        });

    }
    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        try {
            List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
            StandardGenerator genera = new StandardGenerator(new Font("Arial", Font.BOLD, 14));
            generators.add(genera);
            generators.add(new BasicSceneGenerator());
            AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
            Rectangle drawArea = new Rectangle(x, y);
            renderer.setZoom(k);
            try {
                renderer.setup(molecule, drawArea);
            } catch (Exception e) {
                e.printStackTrace();

            }
            Point2d temp;
            CordenadasReales.removeAll(CordenadasReales);
            for (int i = 0; i < molecule.getAtomCount(); i++) {
                temp = renderer.toScreenCoordinates(molecule.getAtom(i).getPoint2d().x, molecule.getAtom(i).getPoint2d().y);
                CordenadasReales.add(temp);
            }
            defineK();
            Graphics2D g2D = (Graphics2D) g2;

            g2D.setStroke(new BasicStroke(2));
            renderer.paint(molecule, new AWTDrawVisitor(g2D));
            if (molecule.getAtomCount() > 1)
                for (int x : Selec) {
                    GeneralPath myPolygon = new GeneralPath();
                    myPolygon.moveTo(CordenadasReales.get(x).x - (0 * k), CordenadasReales.get(x).y - 10 * k);
                    myPolygon.moveTo(CordenadasReales.get(x).x - 0 * k, CordenadasReales.get(x).y - 10);
                    myPolygon.lineTo(CordenadasReales.get(x).x - 10 * k, CordenadasReales.get(x).y - 10 * k);
                    myPolygon.lineTo(CordenadasReales.get(x).x + 0 * k, CordenadasReales.get(x).y + 10 * k);
                    myPolygon.lineTo(CordenadasReales.get(x).x + 10 * k, CordenadasReales.get(x).y - 0 * k);
                    g2D.setPaint(new Color(Color.green.getRGB() * 3));
                    myPolygon.closePath();
                    g2D.fill(myPolygon);
                }
        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

    private static final long serialVersionUID = 1L;

    public void addg2(String smile) throws Exception {
        k = 1;
        if (Selec.size() > 0) {
            Selec.removeAll(Selec);
            CordenadasReales.removeAll(CordenadasReales);
        }
        this.smile = smile;
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        molecule = sp.parseSmiles(smile);
        sdg.setMolecule(molecule);
        sdg.generateCoordinates();
        molecule = sdg.getMolecule();

        NumExplicito = molecule.getAtomCount();
        Selec.removeAll(Selec);
        if (molecule.getAtomCount() == 1) {
            Selec.removeAll(Selec);
            Selec.add(0);
        }
        repaint();

    }

    public void addg2(String smile, IAtomContainer mol, ArrayList<Integer> Select) throws Exception {

        this.molecule = mol;
        this.Selec = Select;
        this.smile = smile;
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        this.molecule = sp.parseSmiles(smile);
        sdg.setMolecule(this.molecule);
        sdg.generateCoordinates();
        this.molecule = sdg.getMolecule();
        mol = this.molecule;
        NumExplicito = mol.getAtomCount();
        if (mol.getAtomCount() == 1) {
            Selec.removeAll(Selec);
            Selec.add(0);
        }
        repaint();
        defineK();

    }

    private Point2d MiPos() {
        return new Point2d(getMousePosition().getX(), getMousePosition().getY());

    }

    public void defineK(float k) {
        this.k = k;
    }

    void defineK() {
        k = 1;
        try {
            if (molecule.getAtomCount() < 6)
                k = 1.5;
            if (molecule.getAtomCount() > 9)
                k = 0.7;
            if (molecule.getAtomCount() > 13)
                k = .5;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public IAtomContainer returnPrincipalMolecule() {
        return molecule;
    }
}
