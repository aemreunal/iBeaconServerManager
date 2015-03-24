package com.aemreunal.view.response.table;

/*
 ***************************
 * Copyright (c) 2015      *
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

import java.util.Comparator;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// BEACONS_TABLE_COL_NAMES

// "Beacon ID",
// "UUID",
// "Major",
// "Minor",
// "X Coor.",
// "Y Coor.",
// "Designated?",
// "Scenario ID",
// "Description",
// "Creation Date"

public class BeaconTableSorter extends TableRowSorter {
    public BeaconTableSorter(TableModel model) {
        super(model);
        for (int i = 0; i < ItemTable.BEACONS_TABLE_COL_NAMES.length; i++) {
            setComparator(i, new IntComparator());
            this.setSortable(i, true);
        }
    }

    private class IntComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }
}
