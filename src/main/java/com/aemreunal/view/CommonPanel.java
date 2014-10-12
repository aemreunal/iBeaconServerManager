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

import javax.swing.*;

public class CommonPanel extends JPanel {
    public CommonPanel(JPanel topPanel, JPanel bottomPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(topPanel);
        add(bottomPanel);
        topPanel.setMaximumSize(topPanel.getPreferredSize());
    }
}
