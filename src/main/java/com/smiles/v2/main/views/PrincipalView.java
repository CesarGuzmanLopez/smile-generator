package com.smiles.v2.main.views;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import com.smiles.v2.main.views.panels.Menu;
import com.smiles.v2.main.views.panels.MoleculePanel;
import com.smiles.v2.main.views.panels.OptionPanel;

public final class PrincipalView extends javax.swing.JFrame {
    private MoleculePanel MoleculePanelPrincipal;
    private OptionPanel optionPanel;
    private MoleculePanel moleculePreviewPanel;
    private JTextField textFieldSmile;
    private JButton smileButton;
    private JButton generateButton;
    private JLabel textFieldSavePath;

    public PrincipalView() {
        super("Smile generator");
        setSize(850, 550);
        setMinimumSize( new Dimension(750, 450));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        initialize();
        setVisible(true);
    }
    private void initialize() {
        setJMenuBar(new Menu());
        setLayout(new GridBagLayout());
        initializeEntrySmile            (0, 0, 1, 0);
        initializeMoleculePanelPrincipal(0, 1, 1, 1);
        initializeOptionPanel           (1, 1, 0, 1);
        initializeMoleculePreviewPanel  (2, 1, 1, 1);
        initializeActionGenerator       (0, 3, 3, 1);
    }
    private void initializeEntrySmile(int gridx, int gridy, double weightx, double weighty) {
        JPanel panelSmile = new JPanel();
        panelSmile.setLayout(new GridBagLayout());
        panelSmile.setBorder(javax.swing.BorderFactory.createTitledBorder("Entry"));

        GridBagConstraints gbcPanel = new GridBagConstraints();

        JLabel labelSmile = new JLabel("Smile: ");
        panelSmile.add(labelSmile,gbcPanel);

        textFieldSmile = new JTextField();
        textFieldSmile.setPreferredSize(new Dimension(300, 30));
        textFieldSmile.setMinimumSize(new Dimension(100, 30));
        panelSmile.add(textFieldSmile,gbcPanel);

        smileButton = new JButton("Draw");
        panelSmile.add(smileButton,gbcPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth =2;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new java.awt.Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        add(panelSmile, gbc);
    }
    private void initializeMoleculePanelPrincipal(int gridx, int gridy, double weightx, double weighty) {
        MoleculePanelPrincipal = new MoleculePanel();
        MoleculePanelPrincipal.setPreferredSize(new Dimension(300, 300));
        MoleculePanelPrincipal.setMaximumSize(new Dimension(400, 400));
        MoleculePanelPrincipal.setMinimumSize(new Dimension(200, 200));
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.CENTER;
        add(MoleculePanelPrincipal,gbc);
    }
    private void initializeOptionPanel(int gridx, int gridy, double weightx, double weighty) {
        optionPanel = new OptionPanel();
        optionPanel.setPreferredSize(new Dimension(200, 100));
        optionPanel.setMaximumSize(new Dimension(250, 100));
        optionPanel.setMinimumSize(new Dimension(190, 100));
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.NORTH;
        add(optionPanel,gbc);
    }
    private void initializeMoleculePreviewPanel(int gridx, int gridy, double weightx, double weighty) {
        moleculePreviewPanel = new MoleculePanel();
        moleculePreviewPanel.setPreferredSize(new Dimension(200, 200));
        moleculePreviewPanel.setMaximumSize(new Dimension(300, 300));
        moleculePreviewPanel.setMinimumSize(new Dimension(180, 180));
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.CENTER;
        add(moleculePreviewPanel,gbc);
    }
    private void initializeActionGenerator(int gridx, int gridy, double weightx, double weighty) {
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridBagLayout());
        panelAction.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));
        GridBagConstraints gbcPanel = new GridBagConstraints();

        JLabel labelSaveAs = new JLabel("save in: ");
        panelAction.add(labelSaveAs,gbcPanel);

        gbcPanel.ipadx = 5;
        textFieldSavePath = new JLabel("'no selected'");
        panelAction.add(textFieldSavePath,gbcPanel);

        generateButton = new JButton("Generate");
        panelAction.add(generateButton,gbcPanel);

        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth =4;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(panelAction,gbc);
    }
}
