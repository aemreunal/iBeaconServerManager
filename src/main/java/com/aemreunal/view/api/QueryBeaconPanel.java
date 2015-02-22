package com.aemreunal.view.api;

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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.APIManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class QueryBeaconPanel extends JPanel {
    private JTextField uuidField;
    private JTextField majorField;
    private JTextField minorField;
    private JTextField secretField;
    private JButton    queryButton;

    public QueryBeaconPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        QueryActionListener actionListener = new QueryActionListener(tableResponsePanel);
        uuidField = new JTextField(20);
        majorField = new JTextField(20);
        minorField = new JTextField(20);
        secretField = new JTextField(20);
        secretField.addActionListener(actionListener);
        queryButton = new JButton("Query");
        queryButton.addActionListener(actionListener);
    }

    private void addComponents() {
        JPanel uuidPanel = new JPanel(new GridBagLayout());
        uuidPanel.add(new JLabel("UUID:"));
        uuidPanel.add(uuidField);
        uuidPanel.setMinimumSize(uuidPanel.getPreferredSize());
        this.add(uuidPanel);

        JPanel majorPanel = new JPanel(new GridBagLayout());
        majorPanel.add(new JLabel("Major:"));
        majorPanel.add(majorField);
        majorPanel.setMinimumSize(majorPanel.getPreferredSize());
        this.add(majorPanel);

        JPanel minorPanel = new JPanel(new GridBagLayout());
        minorPanel.add(new JLabel("Minor:"));
        minorPanel.add(minorField);
        minorPanel.setMinimumSize(minorPanel.getPreferredSize());
        this.add(minorPanel);

        JPanel secretPanel = new JPanel(new GridBagLayout());
        secretPanel.add(new JLabel("Secret:"));
        secretPanel.add(secretField);
        secretPanel.setMinimumSize(secretPanel.getPreferredSize());
        this.add(secretPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(queryButton);
        buttonPanel.setMinimumSize(buttonPanel.getPreferredSize());
        this.add(buttonPanel);
    }

    private class QueryActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public QueryActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = APIManager.queryBeacon(uuidField.getText().trim(),
                                                                     majorField.getText().trim(),
                                                                     minorField.getText().trim(),
                                                                     secretField.getText().trim());
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] queryResponse = null;
            if (response.getStatus() == 200) {
                queryResponse = APITab.convertQueryJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.API_QUERY_TABLE_COL_NAMES, queryResponse);
        }
    }
}
