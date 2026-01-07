package de.genrichgolzsch.fittidb.controller;                                   // Controller-Paket

import de.genrichgolzsch.fittidb.model.Membership;                              // Model Membership
import de.genrichgolzsch.fittidb.model.Person;                                  // Model Person
import de.genrichgolzsch.fittidb.service.MembershipService;                     // Membership Service
import de.genrichgolzsch.fittidb.service.PersonService;                         // Person Service

import javafx.collections.FXCollections;                                        // ObservableList Helper
import javafx.collections.ObservableList;                                       // ObservableList
import javafx.fxml.FXML;                                                        // FXML Annotation
import javafx.fxml.FXMLLoader;                                                  // Loader
import javafx.scene.Parent;                                                     // Parent
import javafx.scene.Scene;                                                      // Scene
import javafx.scene.control.Label;                                              // Label
import javafx.scene.control.TableColumn;                                        // TableColumn
import javafx.scene.control.TableView;                                          // TableView
import javafx.scene.control.cell.PropertyValueFactory;                          // Property Mapping
import javafx.stage.Stage;                                                      // Stage

import java.io.IOException;                                                     // IO Exception
import java.sql.SQLException;                                                   // SQL Exception
import java.util.ArrayList;                                                     // ArrayList
import java.util.List;                                                          // List

/**
 * Controller für memberships.fxml
 */
public class MembershipsController {                                             // Klassenbeginn

    @FXML private TableView<Membership> tvMemberships;                            // TableView
    @FXML private TableColumn<Membership, Integer> colMembershipId;               // Column membership_id
    @FXML private TableColumn<Membership, Integer> colPersonsId;                  // Column persons_id
    @FXML private TableColumn<Membership, Integer> colPlanId;                     // Column plan_id
    @FXML private TableColumn<Membership, String> colStartDate;                   // Column start_date
    @FXML private TableColumn<Membership, Boolean> colActive;                     // Column active
    @FXML private Label lblStatus;                                                // Status Label

    private final MembershipService membershipService = new MembershipService();  // Membership Service
    private final PersonService personService = new PersonService();              // Person Service

    private final ObservableList<Membership> membershipList = FXCollections.observableArrayList(); // Datenliste

    @FXML
    private void initialize() {                                                   // JavaFX Init

        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId")); // Mapping ID
        colPersonsId.setCellValueFactory(new PropertyValueFactory<>("personsId"));       // Mapping Person ID
        colPlanId.setCellValueFactory(new PropertyValueFactory<>("planId"));             // Mapping Plan ID
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));       // Mapping Startdate
        colActive.setCellValueFactory(new PropertyValueFactory<>("active"));             // Mapping Active

        tvMemberships.setItems(membershipList);                                    // Liste binden

        onRefresh();                                                               // Erste Datenladung
    }

    @FXML
    private void onRefresh() {                                                     // Refresh Handler
        try {
            List<Person> persons = personService.getAllPersons();                  // Alle Personen laden
            List<Membership> result = new ArrayList<>();                           // Ergebnisliste

            for (Person p : persons) {                                             // Jede Person durchgehen
                Membership m = membershipService.getMembershipForPerson(p.getPersonsId()); // Membership holen
                if (m != null) {                                                   // Nur wenn vorhanden
                    result.add(m);                                                  // Zur Liste hinzufügen
                }
            }

            membershipList.setAll(result);                                         // Tabelle setzen
            lblStatus.setText("Geladen: " + result.size() + " Memberships");       // Status
        } catch (SQLException e) {
            lblStatus.setText("DB-Fehler beim Laden der Memberships.");            // Fehlertext
            e.printStackTrace();                                                   // Debug
        }
    }

    @FXML
    private void onBack() {                                                        // Zurück Handler
        try {
            openDashboard();                                                       // Dashboard öffnen
        } catch (IOException e) {
            lblStatus.setText("Fehler beim Zurückgehen.");                         // Fehlertext
            e.printStackTrace();                                                   // Debug
        }
    }

    private void openDashboard() throws IOException {                              // Dashboard laden

        FXMLLoader loader = new FXMLLoader(                                        // Loader erstellen
                getClass().getResource("/de/genrichgolzsch/fittidb/view/dashboard.fxml")
        );

        Parent root = loader.load();                                               // FXML laden
        Scene scene = new Scene(root, 1000, 700);                                   // Scene erstellen

        Stage stage = (Stage) tvMemberships.getScene().getWindow();                // Stage holen
        stage.setTitle("FittiDB - Dashboard");                                      // Titel setzen
        stage.setScene(scene);                                                     // Scene setzen
        stage.show();                                                              // Anzeigen
    }
}
