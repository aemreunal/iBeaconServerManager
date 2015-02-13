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
import com.aemreunal.view.scenario.region.GetRegionMembersPanel;

public class GetMembersPanel extends CommonTab {

    @Override
    protected void addPanels() {
        addGetBeaconMembersTab();
        addGetRegionMembersTab();
    }

    private void addGetBeaconMembersTab() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetBeaconMembersPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Beacon", commonPanel);
    }

    private void addGetRegionMembersTab() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetRegionMembersPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Region", commonPanel);
    }
}
