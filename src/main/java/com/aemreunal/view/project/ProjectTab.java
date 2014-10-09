package com.aemreunal.view.project;

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
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;

public class ProjectTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateProjectPanel();
    }

    private void addCreateProjectPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new CreateProjectPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    public static String[][] convertProjectCreateJsonToTable(JSONObject createdProject) {
        String[][] projectTable = new String[1][ItemTable.PROJECT_TABLE_COL_NAMES.length];
        projectTable[0][0] = createdProject.get("projectId").toString();
        projectTable[0][1] = createdProject.get("name").toString();
        projectTable[0][2] = createdProject.get("description").toString();
        projectTable[0][3] = "";
        return projectTable;
    }
}
