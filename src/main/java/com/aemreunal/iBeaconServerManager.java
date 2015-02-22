package com.aemreunal;

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

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.prefs.Preferences;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.aemreunal.view.ManagerWindow;
import com.mashape.unirest.http.Unirest;

public class iBeaconServerManager {
    private static Preferences preferences;

    public static void main(String[] args) throws Exception {
        new iBeaconServerManager();
    }

    public iBeaconServerManager() {
        preferences = Preferences.userRoot().node(this.getClass().getName());

        try {
            createCustomHttpClient();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            System.err.println("Unable to create custom HTTP client!\niBeacon Server Manager will now terminate.");
            e.printStackTrace();
            System.exit(-1);
        }

        Unirest.setDefaultHeader("Accept", "application/json");

        new ManagerWindow();
    }

    /*
     * A custom HTTP client is created in order to accept self-signed
     * Worst case scenario:
     * http://www.java-samples.com/showtutorial.php?tutorialid=210
     */
    private void createCustomHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        SSLContext sslcontext = SSLContexts.custom()
                                           .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                                           .build();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslcontext);
        CloseableHttpClient httpClient = HttpClients.custom()
                                                    .setSSLSocketFactory(socketFactory)
                                                    .build();
        Unirest.setHttpClient(httpClient);
    }

    public static Preferences getPreferences() {
        return preferences;
    }
}
