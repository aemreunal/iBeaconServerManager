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

public abstract class CommonTab extends JPanel {
    protected JTabbedPane tabbedPane;

    public CommonTab() {
        this.tabbedPane = new JTabbedPane();
        addPanels();
        add(tabbedPane);
    }

    protected abstract void addPanels();
}
