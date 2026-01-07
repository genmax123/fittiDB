package de.genrichgolzsch.fittidb.model;               // Paket für Model-Klassen

/**
 * Repräsentiert die Gesundheitsdaten einer Person.
 * 1:1-Beziehung zu persons über persons_id.
 */
public class HealthData {                              // Klassenbeginn

    private int healthId;                              // Primary Key aus DB
    private int personsId;                             // Foreign Key zu persons
    private int heightCm;                              // Körpergröße in cm
    private int weightKg;                              // Gewicht in kg
    private String bmiZone;                            // BMI-Kategorie (normal, overweight, ...)

    /**
     * Konstruktor zum Erzeugen eines HealthData-Objekts.
     */
    public HealthData(int healthId,
                      int personsId,
                      int heightCm,
                      int weightKg,
                      String bmiZone) {

        this.healthId = healthId;                      // Health-ID setzen
        this.personsId = personsId;                    // Personen-ID setzen
        this.heightCm = heightCm;                      // Größe setzen
        this.weightKg = weightKg;                      // Gewicht setzen
        this.bmiZone = bmiZone;                        // BMI-Zone setzen
    }

    public int getHealthId() {                          // Getter Health-ID
        return healthId;
    }

    public int getPersonsId() {                         // Getter Personen-ID
        return personsId;
    }

    public int getHeightCm() {                          // Getter Größe
        return heightCm;
    }

    public int getWeightKg() {                          // Getter Gewicht
        return weightKg;
    }

    public String getBmiZone() {                        // Getter BMI-Zone
        return bmiZone;
    }
}
