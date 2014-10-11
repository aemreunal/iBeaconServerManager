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
    private JTextField addressField;
    private JButton    updateAddressButton;
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
        addressField = new JTextField(15);
        updateAddressButton = new JButton("Update address");
        updateAddressButton.addActionListener(new UpdateActionListener());
        usernameField = new JTextField(15);
        updateUsernameButton = new JButton("Update username");
        updateUsernameButton.addActionListener(new UpdateActionListener());
        passwordField = new JTextField(15);
        updatePasswordButton = new JButton("Update password");
        updatePasswordButton.addActionListener(new UpdateActionListener());
        refreshTextFields();
    }

    private void refreshTextFields() {
        addressField.setText(PrefsManager.getServerAddress());
        usernameField.setText(PrefsManager.getUsername());
        passwordField.setText(PrefsManager.getPassword());
    }

    private void addComponents() {
        JPanel addressPanel = new JPanel(new GridBagLayout());
        addressPanel.add(new JLabel("Server address:"));
        addressPanel.add(addressField);
        addressPanel.add(updateAddressButton);
        addressPanel.setMaximumSize(addressPanel.getPreferredSize());
        this.add(addressPanel);

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
                case "address":
                    PrefsManager.setServerAddress(addressField.getText());
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
