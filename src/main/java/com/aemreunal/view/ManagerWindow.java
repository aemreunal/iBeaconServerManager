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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.*;
import com.aemreunal.view.api.APITab;
import com.aemreunal.view.beacon.BeaconTab;
import com.aemreunal.view.prefs.PreferencesTab;
import com.aemreunal.view.project.ProjectTab;
import com.aemreunal.view.region.RegionTab;
import com.aemreunal.view.scenario.ScenarioTab;
import com.aemreunal.view.user.UserTab;
import com.mashape.unirest.http.Unirest;

public class ManagerWindow extends JFrame {
    private JPanel      mainPanel;
    private JTabbedPane tabbedPane;

    public ManagerWindow() {
        super("iBeacon Server Manager");
        initComponents();
        addPanels();
        setWindowAttributes();
    }

    private void initComponents() {
        this.mainPanel = new JPanel(new BorderLayout());
        this.tabbedPane = new JTabbedPane();
    }

    private void addPanels() {
        this.tabbedPane.addTab("User", new UserTab());
        this.tabbedPane.addTab("Project", new ProjectTab());
        this.tabbedPane.addTab("Beacon", new BeaconTab());
        this.tabbedPane.addTab("Region", new RegionTab());
        this.tabbedPane.addTab("Scenario", new ScenarioTab());
        this.tabbedPane.addTab("API", new APITab());
        this.tabbedPane.addTab("Preferences", new PreferencesTab());
    }

    private void setWindowAttributes() {
        setWindowCloseOperation();

        this.setLayout(new BorderLayout());

        this.mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(this.mainPanel, BorderLayout.CENTER);

        this.setMinimumSize(this.getPreferredSize());
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setWindowCloseOperation() {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exiting...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    stopUnirest();
                    System.exit(0);
                }
            }
        };
        this.addWindowListener(exitListener);
    }

    public void stopUnirest() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
