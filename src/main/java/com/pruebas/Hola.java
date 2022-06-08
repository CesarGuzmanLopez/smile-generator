package com.pruebas;

import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Hola {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Hola.class.getName());
        logger.log(Level.INFO, "Hola mundo");
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}

class Menu extends JFrame {
    public Menu() {
        super("Menu");
        seleccionarTema();
        setSize(300, 300);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());
        JButton btn1 = new JButton("Boton 1");
        JButton btn2 = new JButton("Boton 2");
        JButton btn3 = new JButton("Boton 3");
        add(btn1);
        add(btn2);
        add(btn3);
    }

    private static void seleccionarTema() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
