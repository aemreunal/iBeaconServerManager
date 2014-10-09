package com.aemreunal.view;

/*
 ***************************
 * Copyright (c) 2014      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * emre@aemreunal.com      *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
 */

import java.awt.*;
import javax.swing.*;
import com.aemreunal.view.user.UserPanel;

public class ManagerWindow extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    public ManagerWindow() {
        super("iBeacon Server Manager");
        initComponents();
        addPanels();
        setWindowAttributes();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.tabbedPane = new JTabbedPane();
    }

    private void addPanels() {
        this.tabbedPane.addTab("User", new UserPanel());
    }

    private void setWindowAttributes() {
        this.mainPanel.add(tabbedPane);
        add(this.mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setVisible(true);
    }
}
