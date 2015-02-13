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
import java.io.InputStream;
import javax.swing.*;
import com.aemreunal.model.PrefsManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class PreferencesTab extends JPanel {
    private JTextField     urlField;
    private JTextField     portField;
    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        saveSettingsButton;
    private JButton        checkCredentialsButton;

    public PreferencesTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents();
        addComponents();
    }

    private void createComponents() {
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
        urlField.setText(PrefsManager.getServerUrl());
        portField.setText(PrefsManager.getServerPort());
        usernameField.setText(PrefsManager.getUsername());
        passwordField.setText(PrefsManager.getPassword());
    }

    private void addComponents() {
        JPanel urlPanel = new JPanel(new GridBagLayout());
        urlPanel.add(new JLabel("Server URL:"));
        urlPanel.add(urlField);
        urlPanel.setMaximumSize(urlPanel.getPreferredSize());
        this.add(urlPanel);

        JPanel portPanel = new JPanel(new GridBagLayout());
        portPanel.add(new JLabel("Server port:"));
        portPanel.add(portField);
        portPanel.setMaximumSize(portPanel.getPreferredSize());
        this.add(portPanel);

        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.add(new JLabel("Username:"));
        usernamePanel.add(usernameField);
        usernamePanel.setMaximumSize(usernamePanel.getPreferredSize());
        this.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordField);
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
        this.add(passwordPanel);

        this.add(saveSettingsButton);
        this.add(checkCredentialsButton);
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
        PrefsManager.setServerUrl(urlField.getText());
        PrefsManager.setServerPort(portField.getText());
        PrefsManager.setUsername(usernameField.getText());
        PrefsManager.setPassword(passwordField.getPassword());
        JOptionPane.showMessageDialog(null, "Settings have been saved.");
    }
}
