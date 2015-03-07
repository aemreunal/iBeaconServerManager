package com.aemreunal.view.scenario;

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
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;

public class ScenarioTab extends CommonTab {
    @Override
    protected void addPanels() {
        addCreateScenarioPanel();
        addGetScenarioPanel();
        addGetMembersPanel();
        addModifyMembersPanel();
        addDeleteScenarioPanel();
    }

    private void addCreateScenarioPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new CreateScenarioPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetScenarioPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetScenarioPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addGetMembersPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Members", commonPanel);
    }

    private void addModifyMembersPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ModifyMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Modify", commonPanel);
    }

    private void addDeleteScenarioPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteScenarioPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertScenarioJsonToTable(JSONObject scenario) {
        String[][] scenarioTable = new String[1][ItemTable.SCENARIO_TABLE_COL_NAMES.length];
        parseScenarioJson(scenario, 0, scenarioTable);
        return scenarioTable;
    }

    public static String[][] convertScenarioJsonToTable(JSONArray scenarios) {
        String[][] scenarioTable = new String[scenarios.length()][ItemTable.SCENARIO_TABLE_COL_NAMES.length];
        for (int i = 0; i < scenarios.length(); i++) {
            JSONObject scenario = scenarios.getJSONObject(i);
            parseScenarioJson(scenario, i, scenarioTable);
        }
        return scenarioTable;
    }

    private static void parseScenarioJson(JSONObject scenario, int index, String[][] scenarioTable) {
        scenarioTable[index][0] = scenario.get("scenarioId").toString();
        scenarioTable[index][1] = scenario.get("name").toString();
        scenarioTable[index][2] = scenario.get("description").toString();
        scenarioTable[index][3] = scenario.get("messageShort").toString();
        scenarioTable[index][4] = scenario.get("messageLong").toString();
        scenarioTable[index][5] = scenario.get("url").toString();
        scenarioTable[index][6] = new Date(Long.parseLong(scenario.get("creationDate").toString())).toString();
    }
}
