package de.genrichgolzsch.fittidb.controller;                              // Controller-Paket

import de.genrichgolzsch.fittidb.model.User;                               // User-Model
import de.genrichgolzsch.fittidb.service.AuthService;                      // Login-Service

import javafx.fxml.FXML;                                                   // FXML Annotation
import javafx.fxml.FXMLLoader;                                             // View Loader
import javafx.scene.Parent;                                                // Root Node
import javafx.scene.Scene;                                                 // Scene
import javafx.scene.control.Label;                                         // Label
import javafx.scene.control.PasswordField;                                 // PasswordField
import javafx.scene.control.TextField;                                     // TextField
import javafx.stage.Stage;                                                 // Stage

import java.io.IOException;                                                // IO Exception

/**
 * Controller für login.fxml
 */
public class LoginController {                                             // Klassenbeginn

    @FXML private TextField tfUsername;                                     // Username Feld
    @FXML private PasswordField pfPassword;                                 // Passwort Feld
    @FXML private Label lblStatus;                                          // Status Label

    private final AuthService authService = new AuthService();              // Service Instanz

    @FXML
    private void onLogin() {                                                // Button-Handler

        String username = tfUsername.getText();                             // Username lesen
        String password = pfPassword.getText();                             // Passwort lesen

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            lblStatus.setText("Bitte Username und Passwort eingeben.");     // Hinweis ausgeben
            return;                                                        // Abbruch
        }

        User user = authService.login(username.trim(), password);           // Login prüfen

        if (user == null) {                                                 // Kein Treffer?
            lblStatus.setText("Login fehlgeschlagen oder Benutzer inaktiv."); // Fehlertext
            return;                                                        // Abbruch
        }

        lblStatus.setText("Login erfolgreich: " + user.getUsername());      // Erfolg anzeigen

        try {
            openDashboard();                                                // Nächste View öffnen
        } catch (IOException e) {
            lblStatus.setText("Fehler beim Laden des Dashboards.");         // Fehlertext
            e.printStackTrace();                                            // Debug-Ausgabe
        }
    }

    private void openDashboard() throws IOException {                       // Dashboard laden

        FXMLLoader loader = new FXMLLoader(                                 // Loader erstellen
                getClass().getResource("/de/genrichgolzsch/fittidb/view/dashboard.fxml")
        );

        Parent root = loader.load();                                        // FXML laden
        Scene scene = new Scene(root, 1000, 700);                           // Scene erstellen

        Stage stage = (Stage) tfUsername.getScene().getWindow();            // Aktuelles Fenster holen
        stage.setTitle("FittiDB - Dashboard");                              // Titel setzen
        stage.setScene(scene);                                              // Scene setzen
        stage.show();                                                       // Anzeigen
    }
}
