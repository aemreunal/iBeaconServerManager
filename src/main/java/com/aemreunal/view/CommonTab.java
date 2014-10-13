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
import org.json.JSONObject;

public abstract class CommonTab extends JPanel {
    protected JTabbedPane tabbedPane;

    public CommonTab() {
        this.tabbedPane = new JTabbedPane();
        this.setLayout(new BorderLayout());
        addPanels();
        add(tabbedPane, BorderLayout.CENTER);
    }

    protected abstract void addPanels();

    protected static String getSubObjectID(String keyName, JSONObject object) {
        if (object == null || object.toString().equals("null")) {
            return "-";
        } else {
            return object.get(keyName + "Id").toString();
        }
    }
}
