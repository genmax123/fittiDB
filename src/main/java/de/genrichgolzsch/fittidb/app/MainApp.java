package de.genrichgolzsch.fittidb.app;


import javafx.application.Application; // Basis-Klasse für jede JavaFX-Anwendung

import javafx.fxml.FXMLLoader; // Lädt FXML-Dateien (GUI-Layout)

import javafx.scene.Scene; // Container für alle UI-Elemente eines Fensters

import javafx.stage.Stage; // Repräsentiert das Hauptfenster

/**
 * Einstiegspunkt der JavaFX-Anwendung.
 * Diese Klasse startet JavaFX und öffnet das erste Fenster (Login).
 */
public class MainApp extends Application {

    /**
     * Diese Methode wird von JavaFX automatisch aufgerufen,
     * sobald die Anwendung startet.
     */
    @Override
    public void start(Stage stage) throws Exception {

        
        FXMLLoader loader = new FXMLLoader( // FXMLLoader lädt die login.fxml aus dem resources-Ordner
                getClass().getResource("/de/genrichgolzsch/fittidb/view/login.fxml")
        );

        
        Scene scene = new Scene(loader.load(), 900, 600); // Scene = Inhalt des Fensters (Breite: 900px, Höhe: 600px)

        
        stage.setTitle("FittiDB"); // Titel des Fensters

        
        stage.setScene(scene); // Scene dem Fenster zuweisen

        // Fenster anzeigen
        stage.show(); // Fenster anzeigen
    }

    /**
     * Klassische main-Methode.
     * Übergibt die Kontrolle an JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
