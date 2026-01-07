package de.genrichgolzsch.fittidb.controller; // Controller-Paket

import javafx.fxml.FXML; // FXML Annotation
import javafx.fxml.FXMLLoader; // View Loader
import javafx.scene.Parent; // Root Node
import javafx.scene.Scene; // Scene
import javafx.scene.control.Label; // Label
import javafx.stage.Stage; // Stage

import java.io.IOException; // IO Exception

/**
 * Controller für dashboard.fxml
 */
public class DashboardController { // Klassenbeginn

    @FXML
    private Label lblInfo; // Info Label

    @FXML
    private void onLogout() { // Logout-Handler
        try {
            openLogin(); // Login laden
        } catch (IOException e) {
            lblInfo.setText("Fehler beim Logout."); // Fehlertext
            e.printStackTrace(); // Debug-Ausgabe
        }
    }

    @FXML
    private void onOpenCourses() { // Courses öffnen
        try {
            FXMLLoader loader = new FXMLLoader( // Loader erstellen
                    getClass().getResource("/de/genrichgolzsch/fittidb/view/courses.fxml"));

            Parent root = loader.load(); // FXML laden
            Scene scene = new Scene(root, 1100, 750); // Scene erstellen

            Stage stage = (Stage) lblInfo.getScene().getWindow(); // Aktuelles Fenster holen
            stage.setTitle("FittiDB - Courses"); // Titel setzen
            stage.setScene(scene); // Scene setzen
            stage.show(); // Anzeigen

        } catch (IOException e) {
            lblInfo.setText("Fehler beim Laden der Courses-View."); // Fehlertext
            e.printStackTrace(); // Debug
        }
    }

    @FXML
    private void onOpenMemberships() { // Memberships öffnen
        try {
            FXMLLoader loader = new FXMLLoader( // Loader erstellen
                    getClass().getResource("/de/genrichgolzsch/fittidb/view/memberships.fxml"));

            Parent root = loader.load(); // FXML laden
            Scene scene = new Scene(root, 1100, 750); // Scene erstellen

            Stage stage = (Stage) lblInfo.getScene().getWindow(); // Aktuelles Fenster holen
            stage.setTitle("FittiDB - Memberships"); // Titel setzen
            stage.setScene(scene); // Scene setzen
            stage.show(); // Anzeigen

        } catch (IOException e) {
            lblInfo.setText("Fehler beim Laden der Memberships-View."); // Fehlertext
            e.printStackTrace(); // Debug
        }
    }

    private void openLogin() throws IOException { // Login-View öffnen

        FXMLLoader loader = new FXMLLoader( // Loader erstellen
                getClass().getResource("/de/genrichgolzsch/fittidb/view/login.fxml"));

        Parent root = loader.load(); // FXML laden
        Scene scene = new Scene(root, 900, 600); // Scene erstellen

        Stage stage = (Stage) lblInfo.getScene().getWindow(); // Aktuelles Fenster holen
        stage.setTitle("FittiDB - Login"); // Titel setzen
        stage.setScene(scene); // Scene setzen
        stage.show(); // Anzeigen
    }

    @FXML
    private void onOpenPersons() { // Persons öffnen
        try {
            FXMLLoader loader = new FXMLLoader( // Loader erstellen
                    getClass().getResource("/de/genrichgolzsch/fittidb/view/persons.fxml"));

            Parent root = loader.load(); // FXML laden
            Scene scene = new Scene(root, 1100, 750); // Scene erstellen

            Stage stage = (Stage) lblInfo.getScene().getWindow(); // Aktuelles Fenster holen
            stage.setTitle("FittiDB - Persons"); // Titel setzen
            stage.setScene(scene); // Scene setzen
            stage.show(); // Anzeigen

        } catch (IOException e) {
            lblInfo.setText("Fehler beim Laden der Persons-View."); // Fehlertext
            e.printStackTrace(); // Debug
        }
    }

    @FXML
    private void openUserRoles() {
        try {
            FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/de/genrichgolzsch/fittidb/view/user_roles.fxml"));


            Parent root = loader.load();

            de.genrichgolzsch.fittidb.controller.UserRoleController c = loader.getController();

            c.setConnection(
                    de.genrichgolzsch.fittidb.db.DBConnection.getConnection());

            Stage stage = new Stage();
            stage.setTitle("User & Rollen");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            if (lblInfo != null) {
                lblInfo.setText("Fehler beim Öffnen der User-&-Rollen-Übersicht.");
            }
        }
    }

}
