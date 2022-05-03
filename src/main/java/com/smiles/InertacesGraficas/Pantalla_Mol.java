package com.smiles.InertacesGraficas;

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

import javax.swing.JPanel;
import javax.vecmath.Point2d;

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

import com.smiles.ParchesCDK.TestMoleculeFactory;

public class Pantalla_Mol extends JPanel {
    /**
     * @return
     * 
     */
    public String smile;
    
    public IAtomContainer mol = TestMoleculeFactory.makeEthylPropylPhenantren();
    public ArrayList<Integer> Selec;
    public ArrayList<Point2d> CordenadasReales;
    
    private int numSus;
    private double k=1;
    int x;
    int y;
    int NumExplicito;
    public Pantalla_Mol(IAtomContainer mol,ArrayList<Integer> Select, int x, int y) {
        numSus= 0xFFFFFF;
        this.mol = mol;
        Selec=Select;
    //    Selec.removeAll(Selec);
        this.x=x;
        this.y=y;
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
                    if (x.distance(a) < 8*k         && mol.getAtomCount()>1) {
                    //    System.out.println("Le diste a: " + mol.getAtom(i).getSymbol());
                        if (Selec.contains(i)) {
                    //        System.out.println("Se Quita");
                            Selec.remove((Object) i);
                        } else {
                    //        System.out.println("Se Pone");
                            Selec.add(i);
                        }
                    }
                    i++;
                }
                repaint();
                /*
                 * Graphics2D g2D = (Graphics2D) getGraphics(); myRectangle = new
                 * Rectangle2D.Double(0, 0, 10, 10);
                 * 
                 * g2D.setPaint(Color.red); g2D.fill(myRectangle);
                 * 
                 * g2D.dispose(); repaint();
                 */

            }
        });
        definek();

        
    }

    public Pantalla_Mol(IAtomContainer mol,ArrayList<Integer> Select,int x) {
        numSus=x;
        this.mol = mol;
        
        Selec = Select;
        CordenadasReales = new ArrayList<Point2d>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Point2d a = MiPos();
                if (mol.getAtomCount() <= 1) {
                    return;
                }
                if (NumExplicito <= 1) {
                    System.out.println("No hay nada");
                    return;
                }
                System.out.print(".");
                
                int i = 0;
                for (Point2d x : CordenadasReales) {
                    if (x.distance(a) < 8 && mol.getAtomCount()>1) {
                //        System.out.println("Le diste a: " + mol.getAtom(i).getSymbol());
                        
                        if (Selec.contains(i)) {
                        //    System.out.println("Se Quita");
                            Selec.remove((Object) i);
                        } else {
                            if(Selec.size()<numSus) {
                        //        System.out.println("Se Pone");
                                Selec.add(i);
                            }else {
                                Selec.remove(Selec.size()-1);
                    //            System.out.println("Se Pone");
                                Selec.add(i);                            
                            }
                        }
                    }
                    i++;
                }
                repaint();
                /*
                 * Graphics2D g2D = (Graphics2D) getGraphics(); myRectangle = new
                 * Rectangle2D.Double(0, 0, 10, 10);
                 * 
                 * g2D.setPaint(Color.red); g2D.fill(myRectangle);
                 * 
                 * g2D.dispose(); repaint();
                 */
                definek();

            }
        });
        
    }
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        try {
            // CN(C(C1=O)C)(=N1C=CC=CC=1)
            // mol.removeAtom( mol.getAtom(mol.getAtomCount()-1));
            List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
            StandardGenerator genera = new StandardGenerator(new Font("Arial", Font.BOLD, 14));
            generators.add(genera);
//            generators.add(new AtomNumberGenerator());
            generators.add(new BasicSceneGenerator());
            // generators.add(new StandardGenerator(new Font("Arial", Font.BOLD, 10)));
            // generators.add(new BasicAtomGenerator());
            // generators.add();
            AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
            Rectangle drawArea = new Rectangle(x, y);
            renderer.setZoom(k);
            renderer.setup(mol, drawArea);
            
            Point2d temp;
            CordenadasReales.removeAll(CordenadasReales);
            for (int i = 0; i < mol.getAtomCount(); i++) {
                temp = renderer.toScreenCoordinates(mol.getAtom(i).getPoint2d().x, mol.getAtom(i).getPoint2d().y);
                CordenadasReales.add(temp);
                // System.out.println(mol.getAtom(i).getSymbol()+" "+(
                // mol.getAtom(i).getPoint2d())+" Cordenadas Reales "+temp);
            }

            // System.out.println("-Mi Centro del modelo respecto al 0 del modelo: "+
            // renderer.getModelCenter());
            // System.out.println("-Mi Centro respecto a la matalla : "+
            // renderer.toScreenCoordinates(-0.938,0.5441));

            /*
             * Image image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB); g2 =
              */
             definek();
            Graphics2D g2D = (Graphics2D) g2;
 
            g2D.setStroke(new BasicStroke(2));
            renderer.paint(mol, new AWTDrawVisitor(g2D));
            if(mol.getAtomCount()>1)
                for (int x :Selec ) {
                    GeneralPath myPolygon = new GeneralPath();
                    myPolygon.moveTo(CordenadasReales.get(x).x-(0*k), CordenadasReales.get(x).y-10*k);
                    myPolygon.moveTo(CordenadasReales.get(x).x-0*k, CordenadasReales.get(x).y-10);
                    myPolygon.lineTo(CordenadasReales.get(x).x-10*k, CordenadasReales.get(x).y-10*k);
                    myPolygon.lineTo(CordenadasReales.get(x).x+0*k, CordenadasReales.get(x).y+10*k);
                    myPolygon.lineTo(CordenadasReales.get(x).x+10*k, CordenadasReales.get(x).y-0*k);
                    g2D.setPaint(new Color(Color.green.getRGB()*3));
                //    myPolygon.lineTo(CordenadasReales.get(x).x+10*k, CordenadasReales.get(x).y+10*k);
                //    myPolygon.moveTo(CordenadasReales.get(x).x-(6*k), CordenadasReales.get(x).y-6*k);

                    myPolygon.closePath();
            //        Double myRectangle = new Rectangle2D.Double( CordenadasReales.get(x).x-6*k, CordenadasReales.get(x).y-6*k, 12*k, 12*k);
                    g2D.fill(myPolygon);
            // ImageIO.write((RenderedImage)g2D, "PNG",new File("RenderMolecule.png"));
                    }
        } catch (Exception e) {

        }

    }

    private static final long serialVersionUID = 1L;

    public void addg2(String smile) throws Exception {
        k=1;
        if(Selec.size()>0) {
        Selec.removeAll(Selec);
        CordenadasReales.removeAll(CordenadasReales);
        }this.smile = smile;
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        mol = sp.parseSmiles(smile);
        sdg.setMolecule(mol);
        sdg.generateCoordinates();
        mol = sdg.getMolecule();

        NumExplicito=mol.getAtomCount();
        Selec.removeAll(Selec);
        if(mol.getAtomCount()==1) {
            Selec.removeAll(Selec);
            Selec.add(0);
            
        }
        repaint();

    }
    public void addg2(String smile,IAtomContainer mol,ArrayList<Integer> Select) throws Exception {
        
        this.mol= mol;
        this.Selec=Select;
        this.smile = smile;
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
    //    mol.removeAllElements();
        this.mol = sp.parseSmiles(smile);
        sdg.setMolecule(this.mol);
        sdg.generateCoordinates();
        this.mol = sdg.getMolecule();
        mol=this.mol;
    //    System.out.println("Mol");
        NumExplicito=mol.getAtomCount();
        if(mol.getAtomCount()==1) {
            Selec.removeAll(Selec);
            Selec.add(0);
        }
        repaint();
        definek();

    }
    private Point2d MiPos() {
        Point2d d = new Point2d(getMousePosition().getX(), getMousePosition().getY());
        return d;
    }
     public void definek(float k) {
        this.k=k;
    }
    void definek() {
        k=1;try {
        if(mol.getAtomCount()<6)
            k=1.5;
        if(mol.getAtomCount()>9)
            k=0.7;
        if(mol.getAtomCount()>13)
            k=.5;
        }catch(Exception e) {}
        
    }
    public IAtomContainer ReturnPrin() {
        return mol;
    }
}
