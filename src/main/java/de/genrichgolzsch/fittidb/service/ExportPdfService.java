package de.genrichgolzsch.fittidb.service;                                   // Service-Paket

import de.genrichgolzsch.fittidb.model.Person;                               // Model Person
import de.genrichgolzsch.fittidb.model.HealthData;                           // Model HealthData

import org.apache.pdfbox.pdmodel.PDDocument;                                 // PDF Dokument
import org.apache.pdfbox.pdmodel.PDPage;                                     // PDF Seite
import org.apache.pdfbox.pdmodel.PDPageContentStream;                        // Schreiben in PDF
import org.apache.pdfbox.pdmodel.common.PDRectangle;                         // Seitenformat
import org.apache.pdfbox.pdmodel.font.PDType1Font;                           // Standard-Font

import java.io.IOException;                                                  // IO Exception
import java.nio.file.Files;                                                  // Datei prüfen
import java.nio.file.Path;                                                   // Pfad
import java.util.List;                                                       // Liste

/**
 * Service für PDF-Export (z. B. Mitgliederliste).
 * Enthält keine GUI-Logik und kein SQL (nur Export).
 */
public class ExportPdfService {                                              // Klassenbeginn

    public void exportPersonsPdf(List<Person> persons, Path targetFile) throws IOException {
        // Exportiert eine einfache Mitgliederliste als PDF

        ensureParentFolderExists(targetFile);                                // Ordner sicherstellen

        try (PDDocument doc = new PDDocument()) {                            // PDF-Dokument erzeugen

            PDPage page = new PDPage(PDRectangle.A4);                        // A4-Seite
            doc.addPage(page);                                               // Seite hinzufügen

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) { // ContentStream öffnen

                float margin = 50;                                           // Rand
                float y = page.getMediaBox().getHeight() - margin;           // Start-Y oben
                float lineHeight = 16;                                       // Zeilenhöhe

                cs.beginText();                                              // Textmodus starten
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);                  // Titel-Font
                cs.newLineAtOffset(margin, y);                               // Cursor setzen
                cs.showText("Mitgliederliste (Persons)");                    // Titel schreiben
                cs.endText();                                                // Textmodus beenden

                y -= 2 * lineHeight;                                         // Abstand nach Titel

                cs.beginText();                                              // Textmodus starten
                cs.setFont(PDType1Font.HELVETICA, 11);                       // Standard-Font
                cs.newLineAtOffset(margin, y);                               // Cursor setzen

                for (Person p : persons) {                                   // Alle Personen durchlaufen
                    String line = formatPersonLine(p);                       // Eine Zeile bauen

                    cs.showText(line);                                       // Zeile schreiben
                    cs.newLineAtOffset(0, -lineHeight);                      // Zeile nach unten
                    y -= lineHeight;                                         // Y aktualisieren

                    if (y < margin) {                                        // Seitenende erreicht?
                        cs.endText();                                        // Textmodus beenden
                        break;                                               // Einfacher Export: stoppt hier
                    }
                }

                cs.endText();                                                // Textmodus beenden
            }

            doc.save(targetFile.toFile());                                   // PDF speichern
        }
    }

    public void exportPersonWithHealthPdf(Person person, HealthData healthData, Path targetFile)
            throws IOException {
        // Exportiert eine Person + Gesundheitsdaten als PDF

        ensureParentFolderExists(targetFile);                                // Ordner sicherstellen

        try (PDDocument doc = new PDDocument()) {                            // PDF-Dokument erzeugen

            PDPage page = new PDPage(PDRectangle.A4);                        // A4-Seite
            doc.addPage(page);                                               // Seite hinzufügen

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) { // ContentStream öffnen

                float margin = 50;                                           // Rand
                float y = page.getMediaBox().getHeight() - margin;           // Start-Y oben
                float lineHeight = 18;                                       // Zeilenhöhe

                writeLine(cs, margin, y, PDType1Font.HELVETICA_BOLD, 16,
                        "Mitgliedsprofil");                                  // Titel schreiben
                y -= 2 * lineHeight;                                         // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA_BOLD, 12,
                        "Person");                                           // Abschnittstitel
                y -= lineHeight;                                             // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                        "ID: " + person.getPersonsId());                     // ID
                y -= lineHeight;                                             // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                        "Name: " + person.getFirstName() + " " + person.getLastName()); // Name
                y -= lineHeight;                                             // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                        "E-Mail: " + safe(person.getEmail()));               // Email
                y -= lineHeight;                                             // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                        "Telefon: " + safe(person.getPhoneNr()));            // Telefon
                y -= 2 * lineHeight;                                         // Abstand

                writeLine(cs, margin, y, PDType1Font.HELVETICA_BOLD, 12,
                        "Health Data");                                      // Abschnittstitel
                y -= lineHeight;                                             // Abstand

                if (healthData == null) {                                    // Keine Daten?
                    writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                            "Keine Gesundheitsdaten vorhanden.");            // Hinweis
                } else {                                                     // Daten vorhanden
                    writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                            "Groesse (cm): " + healthData.getHeightCm());    // Größe
                    y -= lineHeight;                                         // Abstand

                    writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                            "Gewicht (kg): " + healthData.getWeightKg());    // Gewicht
                    y -= lineHeight;                                         // Abstand

                    writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                            "BMI-Zone: " + safe(healthData.getBmiZone()));   // BMI-Zone
                }
            }

            doc.save(targetFile.toFile());                                   // PDF speichern
        }
    }

    private void writeLine(PDPageContentStream cs, float x, float y,
                           PDType1Font font, int fontSize, String text) throws IOException {
        // Schreibt genau eine Zeile an Position x/y

        cs.beginText();                                                      // Textmodus starten
        cs.setFont(font, fontSize);                                          // Font setzen
        cs.newLineAtOffset(x, y);                                            // Cursor setzen
        cs.showText(text);                                                   // Text schreiben
        cs.endText();                                                        // Textmodus beenden
    }

    private String formatPersonLine(Person p) {                               // Eine Zeile bauen
        return p.getPersonsId() + " | " +                                    // ID
               p.getLastName() + ", " + p.getFirstName() + " | " +           // Name
               safe(p.getEmail()) + " | " +                                  // Email
               safe(p.getCity());                                            // Stadt
    }

    private String safe(String s) {                                           // Null-sicher
        return (s == null) ? "" : s;                                         // Null -> leer
    }

    private void ensureParentFolderExists(Path targetFile) throws IOException {
        // Stellt sicher, dass der Zielordner existiert

        Path parent = targetFile.getParent();                                 // Parent holen
        if (parent != null && !Files.exists(parent)) {                        // Ordner fehlt?
            Files.createDirectories(parent);                                  // Ordner erstellen
        }
    }
    public void exportCourseOverviewPdf(List<String> lines, Path targetFile) throws IOException {
    // Exportiert eine Kursübersicht als PDF (Usecase 3)

    ensureParentFolderExists(targetFile);                                         // Ordner sicherstellen

    try (PDDocument doc = new PDDocument()) {                                     // PDF-Dokument erstellen

        PDPage page = new PDPage(PDRectangle.A4);                                 // A4-Seite erstellen
        doc.addPage(page);                                                        // Seite hinzufügen

        try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {       // Schreibstream öffnen

            float margin = 50;                                                    // Seitenrand
            float y = page.getMediaBox().getHeight() - margin;                    // Startposition oben
            float lineHeight = 16;                                                // Zeilenhöhe

            writeLine(cs, margin, y, PDType1Font.HELVETICA_BOLD, 16,
                    "Kursuebersicht");                                            // Titel schreiben
            y -= 2 * lineHeight;                                                  // Abstand nach Titel

            writeLine(cs, margin, y, PDType1Font.HELVETICA_BOLD, 11,
                    "Kurs | Belegung | Beschreibung");                            // Kopfzeile
            y -= lineHeight;                                                      // Abstand nach Kopfzeile

            for (String line : lines) {                                           // Alle Zeilen durchlaufen
                if (y < margin) {                                                 // Seitenende erreicht?
                    break;                                                        // PoC: stoppt am Seitenende
                }

                writeLine(cs, margin, y, PDType1Font.HELVETICA, 11,
                        line);                                                    // Zeile schreiben
                y -= lineHeight;                                                  // Nächste Zeile
            }
        }

        doc.save(targetFile.toFile());                                            // PDF speichern
    }
}

}
