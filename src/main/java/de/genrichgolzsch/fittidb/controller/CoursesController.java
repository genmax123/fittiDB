package de.genrichgolzsch.fittidb.controller;                                   // Controller-Paket

import de.genrichgolzsch.fittidb.model.Course;                                  // Model Course
import de.genrichgolzsch.fittidb.service.CourseService;                         // Course Service
import de.genrichgolzsch.fittidb.service.ExportPdfService;                      // PDF Export

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
import java.nio.file.Path;                                                      // Path
import java.sql.SQLException;                                                   // SQL Exception
import java.time.LocalDate;                                                     // LocalDate
import java.util.List;                                                          // List

/**
 * Controller für courses.fxml
 */
public class CoursesController {                                                // Klassenbeginn

    @FXML private TableView<Course> tvCourses;                                   // TableView
    @FXML private TableColumn<Course, Integer> colId;                            // Column ID
    @FXML private TableColumn<Course, String> colName;                           // Column Name
    @FXML private TableColumn<Course, Integer> colMax;                           // Column Max
    @FXML private TableColumn<Course, String> colDesc;                           // Column Desc
    @FXML private Label lblStatus;                                               // Status Label

    private final CourseService courseService = new CourseService();             // Service Instanz
    private final ExportPdfService pdfService = new ExportPdfService();          // PDF Service

    private final ObservableList<Course> courseList = FXCollections.observableArrayList(); // Datenliste

    @FXML
    private void initialize() {                                                  // JavaFX Init

        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));       // Mapping ID
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));   // Mapping Name
        colMax.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));// Mapping Max
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));  // Mapping Desc

        tvCourses.setItems(courseList);                                          // Liste binden

        onRefresh();                                                             // Erste Datenladung
    }

    @FXML
    private void onRefresh() {                                                   // Refresh Handler
        try {
            List<Course> courses = courseService.getAllCourses();                // DB laden
            courseList.setAll(courses);                                          // Liste setzen
            lblStatus.setText("Geladen: " + courses.size() + " Kurse");          // Status
        } catch (SQLException e) {
            lblStatus.setText("DB-Fehler beim Laden der Kurse.");                // Fehlertext
            e.printStackTrace();                                                 // Debug
        }
    }

    @FXML
    private void onExportCourseOverviewPdf() {                                   // Kursübersicht PDF
        try {
            List<String> lines = courseService.getCourseOverviewLines();         // Überblickszeilen holen
            Path target = Path.of("exports", "courses_overview_" + LocalDate.now() + ".pdf"); // Zielpfad
            pdfService.exportCourseOverviewPdf(lines, target);                   // Export ausführen
            lblStatus.setText("PDF erstellt: " + target.toString());             // Status
        } catch (Exception e) {
            lblStatus.setText("Fehler beim PDF-Export (Kursübersicht).");        // Fehlertext
            e.printStackTrace();                                                 // Debug
        }
    }

    @FXML
    private void onBack() {                                                      // Zurück Handler
        try {
            openDashboard();                                                     // Dashboard öffnen
        } catch (IOException e) {
            lblStatus.setText("Fehler beim Zurückgehen.");                       // Fehlertext
            e.printStackTrace();                                                 // Debug
        }
    }

    private void openDashboard() throws IOException {                            // Dashboard laden

        FXMLLoader loader = new FXMLLoader(                                      // Loader erstellen
                getClass().getResource("/de/genrichgolzsch/fittidb/view/dashboard.fxml")
        );

        Parent root = loader.load();                                             // FXML laden
        Scene scene = new Scene(root, 1000, 700);                                 // Scene erstellen

        Stage stage = (Stage) tvCourses.getScene().getWindow();                  // Stage holen
        stage.setTitle("FittiDB - Dashboard");                                    // Titel setzen
        stage.setScene(scene);                                                   // Scene setzen
        stage.show();                                                            // Anzeigen
    }
}
