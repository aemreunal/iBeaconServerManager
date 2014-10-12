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

import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.ResponsePanel;
import com.aemreunal.view.scenario.beacon.ModifyBeaconMembersPanel;
import com.aemreunal.view.scenario.beaconGroup.ModifyBeaconGroupMembersPanel;

public class ModifyMembersPanel extends CommonTab {

    @Override
    protected void addPanels() {
        addModifyBeaconMembersTab();
        addModifyBeaconGroupMembersTab();
    }

    private void addModifyBeaconMembersTab() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new ModifyBeaconMembersPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Beacon", commonPanel);
    }

    private void addModifyBeaconGroupMembersTab() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new ModifyBeaconGroupMembersPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Beacon Group", commonPanel);
    }
}
