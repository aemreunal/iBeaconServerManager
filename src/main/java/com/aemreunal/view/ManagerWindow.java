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
import java.util.prefs.Preferences;
import javax.swing.*;
import com.aemreunal.view.project.ProjectTab;
import com.aemreunal.view.user.UserTab;

public class ManagerWindow extends JFrame {
    public static final Dimension MINIMUM_SIZE = new Dimension(600, 650);
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
//    private Preferences preferences;

    public ManagerWindow(Preferences preferences) {
        super("iBeacon Server Manager");
//        this.preferences = preferences;
        initComponents();
        addPanels();
        setWindowAttributes();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.tabbedPane = new JTabbedPane();
    }

    private void addPanels() {
        this.tabbedPane.addTab("User", new UserTab());
        this.tabbedPane.addTab("Project", new ProjectTab());
        // Other Panels
        // Other Panels
        // Other Panels
        // Other Panels
        this.tabbedPane.addTab("Preferences", new PreferencesTab());
    }

    private void setWindowAttributes() {
        this.mainPanel.add(tabbedPane);
        add(this.mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setVisible(true);
    }
}
