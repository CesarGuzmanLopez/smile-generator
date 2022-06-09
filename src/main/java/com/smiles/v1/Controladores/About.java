package com.smiles.v1.Controladores;


import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import java.awt.Color;

public class About extends JDialog {
	public About() {
		setTitle("About");
        setType(Type.POPUP);
        setResizable(false);
        setAlwaysOnTop(true);
        setBounds(100, 100, 598, 384);
        getContentPane().setLayout(null);
        /**
         *
         * Aquí puedes cambiar el texto de la ventana
         */
        
        JLabel lblNewLabel = new JLabel("About Us.");
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setBounds(73, 50, 439, 221);
        getContentPane().add(lblNewLabel);
	}
}
