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

import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;

public class UserTab extends CommonTab {

    @Override
    protected void addPanels() {
        addRegisterPanel();
        addGetUserPanel();
        addDeleteUserPanel();
    }

    private void addRegisterPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new RegisterPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Register", commonPanel);
    }

    private void addGetUserPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetUserPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addDeleteUserPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteUserPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertUserJsonToTable(JSONObject user) {
        String[] userTable = new String[ItemTable.USER_TABLE_COL_NAMES.length];
        userTable[0] = user.get("userId").toString();
        userTable[1] = user.get("username").toString();
        return new String[][] { userTable };
    }
}
