package com.aemreunal.view.prefs;

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
import com.aemreunal.model.PrefsManager;

public class PreferencesTab extends JPanel {
    private JCheckBox      securedCheckBox;
    private JTextField     urlField;
    private JTextField     portField;
    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        saveSettingsButton;
    private JButton        checkCredentialsButton;

    public PreferencesTab() {
        setLayout(new GridBagLayout());
        createComponents();
        addComponents();
    }

    private void createComponents() {
        securedCheckBox = new JCheckBox("Use HTTPS");
        urlField = new JTextField(17);
        portField = new JTextField(17);
        usernameField = new JTextField(17);
        passwordField = new JPasswordField(17);
        saveSettingsButton = new JButton("Save settings");
        saveSettingsButton.setActionCommand("save");
        saveSettingsButton.addActionListener(new UpdateActionListener());
        checkCredentialsButton = new JButton("Test credentials");
        checkCredentialsButton.setActionCommand("test");
        checkCredentialsButton.addActionListener(new UpdateActionListener());
        refreshTextFields();
    }

    private void refreshTextFields() {
        securedCheckBox.setSelected(PrefsManager.getSecured());
        urlField.setText(PrefsManager.getServerUrl());
        portField.setText(PrefsManager.getServerPort());
        usernameField.setText(PrefsManager.getUsername());
        passwordField.setText(PrefsManager.getPassword());
    }

    private void addComponents() {
        addLabelAndComponent(null, securedCheckBox, 0);
        addLabelAndComponent(new JLabel("Server URL:"), urlField, 1);
        addLabelAndComponent(new JLabel("Server port:"), portField, 2);
        addLabelAndComponent(new JLabel("Username:"), usernameField, 3);
        addLabelAndComponent(new JLabel("Password:"), passwordField, 4);
        addLabelAndComponent(null, saveSettingsButton, 5);
        addLabelAndComponent(null, checkCredentialsButton, 6);
    }

    private void addLabelAndComponent(JLabel label, JComponent component, int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.weighty = 0.0;

        if (label != null) {
            constraints.anchor = GridBagConstraints.LINE_END;
            constraints.fill = GridBagConstraints.NONE;
            constraints.gridx = 0;
            constraints.weightx = 0.0;
            add(label, constraints);
        }

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.weightx = 1.0;
        add(component, constraints);
    }

    private class UpdateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveSettings();
            if (e.getActionCommand().equals("test")) {
                PrefsManager.testCredentials();
            }
        }
    }

    private void saveSettings() {
        PrefsManager.setSecured(securedCheckBox.isSelected());
        PrefsManager.setServerUrl(urlField.getText());
        PrefsManager.setServerPort(portField.getText());
        PrefsManager.setUsername(usernameField.getText());
        PrefsManager.setPassword(passwordField.getPassword());
        JOptionPane.showMessageDialog(null, "Settings have been saved.");
    }
}
