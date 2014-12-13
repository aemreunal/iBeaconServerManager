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
    private JTextField urlField;
    private JButton    updateUrlButton;
    private JTextField portField;
    private JButton    updatePortButton;
    private JTextField usernameField;
    private JButton    updateUsernameButton;
    private JTextField passwordField;
    private JButton    updatePasswordButton;

    public PreferencesTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents();
        addComponents();
    }

    private void createComponents() {
        urlField = new JTextField(17);
        updateUrlButton = new JButton("Update URL");
        updateUrlButton.addActionListener(new UpdateActionListener());
        portField = new JTextField(17);
        updatePortButton = new JButton("Update port");
        updatePortButton.addActionListener(new UpdateActionListener());
        usernameField = new JTextField(17);
        updateUsernameButton = new JButton("Update username");
        updateUsernameButton.addActionListener(new UpdateActionListener());
        passwordField = new JTextField(17);
        updatePasswordButton = new JButton("Update password");
        updatePasswordButton.addActionListener(new UpdateActionListener());
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
        urlPanel.add(updateUrlButton);
        urlPanel.setMaximumSize(urlPanel.getPreferredSize());
        this.add(urlPanel);

        JPanel portPanel = new JPanel(new GridBagLayout());
        portPanel.add(new JLabel("Server port:"));
        portPanel.add(portField);
        portPanel.add(updatePortButton);
        portPanel.setMaximumSize(portPanel.getPreferredSize());
        this.add(portPanel);

        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.add(new JLabel("Username:"));
        usernamePanel.add(usernameField);
        usernamePanel.add(updateUsernameButton);
        usernamePanel.setMaximumSize(usernamePanel.getPreferredSize());
        this.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordField);
        passwordPanel.add(updatePasswordButton);
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
        this.add(passwordPanel);
    }

    private class UpdateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (((JButton) e.getSource()).getText().substring(7)) {
                case "url":
                    PrefsManager.setServerUrl(urlField.getText());
                    break;
                case "port":
                    PrefsManager.setServerPort(portField.getText());
                    break;
                case "username":
                    PrefsManager.setUsername(usernameField.getText());
                    break;
                case "password":
                    PrefsManager.setPassword(passwordField.getText());
                    break;
                default:
                    break;
            }
        }
    }
}
