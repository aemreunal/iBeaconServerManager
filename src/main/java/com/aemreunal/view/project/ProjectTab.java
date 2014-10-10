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

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;

public class ProjectTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateProjectPanel();
        addGetProjectPanel();
        addDeleteProjectPanel();
    }

    private void addCreateProjectPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new CreateProjectPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetProjectPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new GetProjectPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addDeleteProjectPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new DeleteProjectPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertProjectCreateJsonToTable(JSONObject createdProject) {
        String[][] projectTable = new String[1][ItemTable.PROJECT_TABLE_COL_NAMES.length];
        projectTable[0][0] = createdProject.get("projectId").toString();
        projectTable[0][1] = createdProject.get("name").toString();
        projectTable[0][2] = createdProject.get("description").toString();
        projectTable[0][3] = "";
        return projectTable;
    }

    public static String[][] convertProjectJsonToTable(JSONObject project) {
        String[][] projectTable = new String[1][ItemTable.PROJECT_TABLE_COL_NAMES.length];
        projectTable[0][0] = project.get("projectId").toString();
        projectTable[0][1] = project.get("name").toString();
        projectTable[0][2] = project.get("description").toString();
        projectTable[0][3] = new Date(Long.parseLong(project.get("creationDate").toString())).toString();
        return projectTable;
    }

    public static String[][] convertProjectsJsonToTable(JSONArray projects) {
        String[][] projectTable = new String[projects.length()][ItemTable.PROJECT_TABLE_COL_NAMES.length];
        for (int i = 0; i < projects.length(); i++) {
            JSONObject project = projects.getJSONObject(i);
            projectTable[i][0] = project.get("projectId").toString();
            projectTable[i][1] = project.get("name").toString();
            projectTable[i][2] = project.get("description").toString();
            projectTable[i][3] = new Date(Long.parseLong(project.get("creationDate").toString())).toString();
        }
        return projectTable;
    }
}
