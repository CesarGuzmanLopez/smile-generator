package com.smiles.Controladores;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.smiles.InertacesGraficas.Substituent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class About extends JDialog {
	public About(GraphicController Pantalla) {
		setTitle("About");
        setType(Type.POPUP);
        setResizable(false);
        setAlwaysOnTop(true);
        setBounds(100, 100, 598, 384);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("About Us.");
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setBounds(73, 50, 439, 221);
        getContentPane().add(lblNewLabel);
	}
}
