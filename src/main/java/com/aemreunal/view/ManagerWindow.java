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

public class ManagerWindow extends JFrame {
    private JPanel mainPanel;

    public ManagerWindow() {
        super("iBeacon Server Manager");
        this.mainPanel = new JPanel();
        add(this.mainPanel);
        this.mainPanel.add(new RegisterPanel());
        setWindowAttributes();
    }

    private void setWindowAttributes() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setVisible(true);
    }
}
