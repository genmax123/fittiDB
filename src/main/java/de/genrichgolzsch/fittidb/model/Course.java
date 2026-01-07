package de.genrichgolzsch.fittidb.model;                 // Model-Paket

/**
 * Repräsentiert einen Kurs aus der Tabelle "courses".
 */
public class Course {                                    // Klassenbeginn

    private int courseId;                                // Primary Key
    private String courseName;                           // Kursname
    private int maxParticipants;                         // Maximale Teilnehmerzahl
    private String description;                          // Beschreibung

    public Course(int courseId, String courseName, int maxParticipants, String description) { // Konstruktor
        this.courseId = courseId;                        // ID setzen
        this.courseName = courseName;                    // Name setzen
        this.maxParticipants = maxParticipants;          // Max. Teilnehmer setzen
        this.description = description;                  // Beschreibung setzen
    }

    public int getCourseId() {                           // Getter courseId
        return courseId;                                 // Rückgabe
    }

    public String getCourseName() {                      // Getter courseName
        return courseName;                               // Rückgabe
    }

    public int getMaxParticipants() {                    // Getter maxParticipants
        return maxParticipants;                          // Rückgabe
    }

    public String getDescription() {                     // Getter description
        return description;                              // Rückgabe
    }
}
