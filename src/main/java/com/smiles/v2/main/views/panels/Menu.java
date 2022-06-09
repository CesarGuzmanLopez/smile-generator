package com.smiles.v2.main.views.panels;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menu extends JMenuBar {

    public Menu() {
        super();
        JMenu menuFile = new JMenu("File");
        add(menuFile);
        JMenu menuHelp = new JMenu("Help");
        add(menuHelp);
    }

}
