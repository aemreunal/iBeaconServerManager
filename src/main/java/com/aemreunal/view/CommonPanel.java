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
    private JPanel topPanel;
    private JPanel bottomPanel;

    public CommonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void setTopPanel(JPanel topPanel) {
        if(this.topPanel != null) {
            remove(this.topPanel);
        }
        this.topPanel = topPanel;
        add(this.topPanel);
    }

    public void setBottomPanel(JPanel bottomPanel) {
        if(this.bottomPanel != null) {
            remove(this.bottomPanel);
        }
        this.bottomPanel = bottomPanel;
        add(this.bottomPanel);
    }
}
