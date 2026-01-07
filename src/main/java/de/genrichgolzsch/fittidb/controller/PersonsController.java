package de.genrichgolzsch.fittidb.controller;                                  // Controller-Paket

import de.genrichgolzsch.fittidb.model.HealthData;                             // Model HealthData
import de.genrichgolzsch.fittidb.model.Person;                                 // Model Person
import de.genrichgolzsch.fittidb.service.ExportPdfService;                     // PDF Export
import de.genrichgolzsch.fittidb.service.PersonService;                        // Person Service

import javafx.collections.FXCollections;                                       // ObservableList Helper
import javafx.collections.ObservableList;                                      // ObservableList
import javafx.fxml.FXML;                                                       // FXML Annotation
import javafx.fxml.FXMLLoader;                                                 // Loader
import javafx.scene.Parent;                                                    // Parent
import javafx.scene.Scene;                                                     // Scene
import javafx.scene.control.Label;                                             // Label
import javafx.scene.control.TableColumn;                                       // TableColumn
import javafx.scene.control.TableView;                                         // TableView
import javafx.scene.control.cell.PropertyValueFactory;                         // Property Mapping
import javafx.stage.Stage;                                                     // Stage

import java.io.IOException;                                                    // IO Exception
import java.nio.file.Path;                                                     // Path
import java.sql.SQLException;                                                  // SQL Exception
import java.time.LocalDate;                                                    // LocalDate
import java.util.List;                                                         // List

/**
 * Controller für persons.fxml
 */
public class PersonsController {                                               // Klassenbeginn

    @FXML private TableView<Person> tvPersons;                                  // TableView
    @FXML private TableColumn<Person, Integer> colId;                           // Column ID
    @FXML private TableColumn<Person, String> colFirstName;                     // Column FirstName
    @FXML private TableColumn<Person, String> colLastName;                      // Column LastName
    @FXML private TableColumn<Person, String> colEmail;                         // Column Email
    @FXML private TableColumn<Person, String> colCity;                          // Column City
    @FXML private Label lblStatus;                                              // Status Label

    private final PersonService personService = new PersonService();            // Service Instanz
    private final ExportPdfService pdfService = new ExportPdfService();         // PDF Service

    private final ObservableList<Person> personList = FXCollections.observableArrayList(); // Datenliste

    @FXML
    private void initialize() {                                                 // JavaFX Init

        colId.setCellValueFactory(new PropertyValueFactory<>("personsId"));     // Mapping ID
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Mapping firstName
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));   // Mapping lastName
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));      // Mapping email
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));        // Mapping city

        tvPersons.setItems(personList);                                         // Liste an Tabelle binden

        onRefresh();                                                            // Erste Datenladung
    }

    @FXML
    private void onRefresh() {                                                  // Refresh Handler
        try {
            List<Person> persons = personService.getAllPersons();               // DB laden
            personList.setAll(persons);                                         // Liste setzen
            lblStatus.setText("Geladen: " + persons.size() + " Personen");      // Status
        } catch (SQLException e) {
            lblStatus.setText("DB-Fehler beim Laden der Personen.");            // Fehlertext
            e.printStackTrace();                                                // Debug
        }
    }

    @FXML
    private void onExportPersonsPdf() {                                         // Mitgliederliste PDF
        try {
            Path target = Path.of("exports", "persons_" + LocalDate.now() + ".pdf"); // Zielpfad
            pdfService.exportPersonsPdf(personList, target);                    // Export ausführen
            lblStatus.setText("PDF erstellt: " + target.toString());            // Status
        } catch (Exception e) {
            lblStatus.setText("Fehler beim PDF-Export (Liste).");               // Fehlertext
            e.printStackTrace();                                                // Debug
        }
    }

    @FXML
    private void onExportProfilePdf() {                                         // Profil PDF (Auswahl)
        Person selected = tvPersons.getSelectionModel().getSelectedItem();      // Auswahl holen

        if (selected == null) {                                                 // Nichts gewählt?
            lblStatus.setText("Bitte zuerst eine Person auswählen.");           // Hinweis
            return;                                                            // Abbruch
        }

        try {
            HealthData hd = personService.getHealthDataForPerson(selected.getPersonsId()); // HealthData laden

            Path target = Path.of("exports", "profil_" + selected.getPersonsId() + "_" + LocalDate.now() + ".pdf");
            // Zielpfad bauen

            pdfService.exportPersonWithHealthPdf(selected, hd, target);         // Export ausführen
            lblStatus.setText("Profil-PDF erstellt: " + target.toString());     // Status
        } catch (Exception e) {
            lblStatus.setText("Fehler beim PDF-Export (Profil).");              // Fehlertext
            e.printStackTrace();                                                // Debug
        }
    }

    @FXML
    private void onBack() {                                                     // Zurück Handler
        try {
            openDashboard();                                                    // Dashboard öffnen
        } catch (IOException e) {
            lblStatus.setText("Fehler beim Zurückgehen.");                      // Fehlertext
            e.printStackTrace();                                                // Debug
        }
    }

    private void openDashboard() throws IOException {                           // Dashboard laden

        FXMLLoader loader = new FXMLLoader(                                     // Loader erstellen
                getClass().getResource("/de/genrichgolzsch/fittidb/view/dashboard.fxml")
        );

        Parent root = loader.load();                                            // FXML laden
        Scene scene = new Scene(root, 1000, 700);                                // Scene erstellen

        Stage stage = (Stage) tvPersons.getScene().getWindow();                 // Stage holen
        stage.setTitle("FittiDB - Dashboard");                                   // Titel setzen
        stage.setScene(scene);                                                  // Scene setzen
        stage.show();                                                           // Anzeigen
    }
}
