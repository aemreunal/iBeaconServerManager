package com.aemreunal.view.user;

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
import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;

public class UserPanel extends JPanel {
    private JTabbedPane tabbedPane;

    public UserPanel() {
        this.tabbedPane = new JTabbedPane();
        addPanels();
        add(tabbedPane);
    }

    private void addPanels() {
        addRegisterPanel();
        addQueryUserPanel();
    }

    private void addRegisterPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new RegisterPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Register", commonPanel);
    }

    private void addQueryUserPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new GetUserPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Get User", commonPanel);
    }

    public static String[][] convertUserJsonToTable(JSONObject user) {
        String[][] userTable = new String[1][ItemTable.USER_TABLE_COL_NAMES.length];
        userTable[0][0] = user.get("userId").toString();
        userTable[0][1] = user.get("username").toString();
        return userTable;
    }
}
