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
import com.aemreunal.view.scenario.beacon.GetBeaconMembersPanel;

public class GetMembersPanel extends CommonTab {

    @Override
    protected void addPanels() {
        addGetBeaconMembersTab();
//        addGetBeaconGroupMembersTab();
    }

    private void addGetBeaconMembersTab() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new GetBeaconMembersPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Beacon", commonPanel);
    }
}
