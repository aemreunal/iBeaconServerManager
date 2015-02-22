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
import com.aemreunal.view.TableResponsePanel;
import com.aemreunal.view.scenario.beacon.ModifyBeaconMembersPanel;
import com.aemreunal.view.scenario.region.ModifyRegionMembersPanel;

public class ModifyMembersPanel extends CommonTab {

    @Override
    protected void addPanels() {
        addModifyBeaconMembersTab();
        addModifyRegionMembersTab();
    }

    private void addModifyBeaconMembersTab() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ModifyBeaconMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Beacon", commonPanel);
    }

    private void addModifyRegionMembersTab() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ModifyRegionMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Region", commonPanel);
    }
}
