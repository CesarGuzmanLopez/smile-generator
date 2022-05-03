package com.smiles.Controladores;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;


import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;

import com.smiles.Generadores.Derivados;
import com.smiles.InertacesGraficas.AddSubs;
import com.smiles.InertacesGraficas.Pantalla_Mol;
import com.smiles.InertacesGraficas.Substients;

import java.awt.Button;
import java.awt.Window.Type;
import java.awt.ScrollPane;
import java.awt.Label;
import java.awt.Component;
import javax.swing.Box;

public class ControladorGrafico {

    public static ArrayList<Integer> Selec;
    public static IAtomContainer MolPrin;
    public static ArrayList<ArrayList<Integer>> SelecSust;
    public static ArrayList<IAtomContainer> MolSust;
    public static String Mensaje;
    private JFrame frame;
    Substients Nuevos = new Substients(SelecSust);
    Pantalla_Mol panel_3;
    public ScrollPane scrollPane_3;
    Vector<JCheckBox> Sustituto;
    public JPanel panel_2;
    JPanel panel;

    /**
     * Launch the application.
     * 
     * @throws InvalidSmilesException
     */
    public static void main(String[] args) throws InvalidSmilesException {
        Selec = new ArrayList<Integer>();
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        MolPrin = sp.parseSmiles("C1CCNCC1");
        SelecSust = new ArrayList<ArrayList<Integer>>();
        MolSust = new ArrayList<IAtomContainer>();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladorGrafico window = new ControladorGrafico();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ControladorGrafico() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setType(Type.POPUP);
        frame.setResizable(false);
        frame.setBounds(100, 100, 838, 443);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(18, 71, 315, 315);
        panel.add(scrollPane_1);
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(557, 58, 260, 260);
        Pantalla_Mol panel_1 = new Pantalla_Mol(MolPrin, Selec, scrollPane_1.getWidth(), scrollPane_1.getHeight());
        panel_3 = new Pantalla_Mol(MolPrin, Selec, scrollPane_2.getWidth(), scrollPane_2.getHeight());

        scrollPane_1.setViewportView(panel_1);
        panel_1.setBackground(Color.white);

        llena_panel();
        // scrollPane.setViewportView(panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));

        panel.add(scrollPane_2);
        scrollPane_2.setViewportView(panel_3);
        panel_3.setBackground(Color.WHITE);

        Button btnGo_1 = new Button("Generate");
        scrollPane_3 = new ScrollPane();
        scrollPane_3.setBounds(370, 75, 169, 254);
        panel.add(scrollPane_3);
        scrollPane_3.add(panel_2);

        btnGo_1.setBounds(650, 370, 89, 23);
        panel.add(btnGo_1);

        AddSubs Add = new AddSubs(Nuevos, this);
        Add.setVisible(false);

        JSpinner spinner = new JSpinner();
        spinner.setBounds(485, 335, 54, 20);
        panel.add(spinner);
        spinner.setValue(1);

        JLabel lblRsubstitutions = new JLabel(" r-substitutions:");
        lblRsubstitutions.setForeground(Color.BLUE);
        lblRsubstitutions.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRsubstitutions.setBounds(370, 335, 110, 20);
        panel.add(lblRsubstitutions);

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(null, "CANONICAL SMILE INPUT: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setBounds(40, 15, 258, 50);
        panel.add(panel_4);
        panel_4.setLayout(null);

        JTextField textPane = new JTextField();
        textPane.setBounds(30, 19, 138, 20);
        panel_4.add(textPane);

        Button btnGo = new Button("Draw");
        btnGo.setBounds(174, 18, 74, 23);
        panel_4.add(btnGo);

        JLabel lblFuunctionalsGroups = new JLabel("Functional groups:");
        lblFuunctionalsGroups.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFuunctionalsGroups.setBounds(370, 45, 110, 14);
        panel.add(lblFuunctionalsGroups);

        Button button = new Button("+");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // String primerNumero = JOptionPane.showInputDialog("Smile");
                /// JOptionPane.showInternalConfirmDialog(null, null, primerNumero, 3, 4);
                Add.setVisible(true);
                // boolean res=(JOptionPane.YES_OPTION== JOptionPane.showConfirmDialog(null,
                // "Implicit Hydrogens", "New Subs", JOptionPane.YES_NO_OPTION,
                // JOptionPane.QUESTION_MESSAGE));
                // if (Nuevos.AddSubs(primerNumero,primerNumero + ((!res)?" Explicit
                // Hydrogens":" Implicit Hydrogens"),res)){

                // }
            }
        });

        button.setBounds(496, 42, 43, 23);
        panel.add(button);

        Button button_1 = new Button("Save as");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fc = new JFileChooser();

                int seleccion = fc.showSaveDialog(fc);

                File archivo = fc.getSelectedFile();

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().exists()) {
                        int response;
                        response = JOptionPane.showConfirmDialog(null,
                                fc.getSelectedFile().toString() + " exists. Overwrite?", "Confirm Save",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                    textField_1.setText(archivo.getPath());
                }
            }
        });
        button_1.setBounds(356, 370, 70, 22);
        panel.add(button_1);

        textField_1 = new JTextField();
        textField_1.setBounds(432, 370, 174, 20);
        panel.add(textField_1);

        Label label = new Label("");
        label.setEnabled(false);
        label.setAlignment(Label.CENTER);
        label.setForeground(Color.DARK_GRAY);
        label.setBounds(746, 371, 62, 22);
        panel.add(label);
        Component verticalGlue = Box.createVerticalGlue();
        verticalGlue.setBounds(344, 389, 7, -368);
        panel.add(verticalGlue);

        Component verticalGlue_1 = Box.createVerticalGlue();
        verticalGlue_1.setBounds(549, 11, -204, 344);
        panel.add(verticalGlue_1);
        btnGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());

                    try {
                        MolPrin = sp.parseSmiles(textPane.getText().replaceAll(" ", ""));
                        Selec.removeAll(Selec);
                    } catch (InvalidSmilesException e1) {
                        e1.printStackTrace();
                    }
                    panel_1.addg2(textPane.getText().replaceAll(" ", ""), panel_3.ReturnPrin(), Selec);
                    panel_1.repaint();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    if(textPane.getText().contains("@"))
                        JOptionPane.showMessageDialog(null,"Change @ by #" );
                    e.printStackTrace();
                }

            }
        });
        btnGo_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                if (textField_1.getText().length() < 4) {
                    JOptionPane.showMessageDialog(null, "Select file");
                    panel.repaint();
                    return;
                }
                int x = (int) spinner.getValue();
                if (x > Selec.size()) {
                    JOptionPane.showMessageDialog(null,"The number of r-substitutions is greather than selected items.");
                    panel.repaint();
                    new Exception("No se puede El numero de R sustituyentes es mayor a los Seleccionados");
                    return;
                }
    
                int p=0;
                for(JCheckBox i: Sustituto) {
                    if(i.isSelected()) {
                        if(SelecSust.get(p).size()<1) {
                            JOptionPane.showMessageDialog(null,"No tags selected in "+ Nuevos.SubsName.get(p));
                            return;
                        }
                    }
                    p++;
                }
                label.setText("");
                Derivados pol = new Derivados(x, MolPrin, Selec, MolSust, SelecSust, Nuevos.HydroImpli, Sustituto);
                FileWriter fw;
                try {

                    if (textField_1.getText() == "")
                        textField_1.setText("Default.txt");
                    fw = new FileWriter(textField_1.getText());
                    PrintWriter bw = new PrintWriter(fw);
                    SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Absolute);
                    bw.println(generator.create(MolPrin));
                    for (String Con : pol.Smiles)
                        bw.println(Con);
                    fw.close();
                    bw.close();
                    fw = null;
                    bw = null;
                    label.setText("Sucessfull");
                } catch (IOException e) {
                    e.printStackTrace();
                    label.setText("Finished");
                } catch (CDKException e) {
                    e.printStackTrace();
                    label.setText("Finished");
                }
            }
        });
    }

    public void llena_panel() {

        SelecSust = new ArrayList<ArrayList<Integer>>();
        MolSust = new ArrayList<IAtomContainer>();

        Sustituto = new Vector<JCheckBox>();
        panel_2 = new JPanel();

        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));

        int i = 0;
        panel_2.removeAll();
        try {
            for (String n : Nuevos.SubsName) {
                int j = i;
                JCheckBox X = new JCheckBox(n);
                String smile = Nuevos.MOLSmile.get(i);
                Sustituto.add(X);
                panel_2.add(Sustituto.elementAt(i));
                SelecSust.add(new ArrayList<Integer>());
                SmilesParser sp1 = new SmilesParser(DefaultChemObjectBuilder.getInstance());
                MolSust.add(sp1.parseSmiles(smile));
                // System.out.println("" + smile);
                X.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent arg0) {
                        try {
                            panel_3.addg2(smile, MolSust.get(j), SelecSust.get(j));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // System.out.println("Como");
                        panel_3.repaint();
                    }
                });
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    JFileChooser paintChooser = new JFileChooser();
    private JTextField textField_1;
}