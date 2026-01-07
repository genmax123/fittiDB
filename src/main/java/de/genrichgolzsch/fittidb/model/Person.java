package de.genrichgolzsch.fittidb.model;              // Paket für Model-Klassen

/**
 * Repräsentiert eine Person (Mitglied) aus der Datenbank.
 * Enthält nur Daten, keine Logik.
 */
public class Person {                                 // Klassenbeginn

    private int personsId;                             // Primary Key aus DB
    private String firstName;                          // Vorname
    private String lastName;                           // Nachname
    private String birthdate;                          // Geburtsdatum (YYYY-MM-DD)
    private String email;                              // E-Mail-Adresse
    private String phoneNr;                            // Telefonnummer
    private String street;                             // Straße
    private String houseNumber;                        // Hausnummer
    private String postalCode;                         // Postleitzahl
    private String city;                               // Stadt

    /**
     * Konstruktor zum Erzeugen eines Person-Objekts.
     */
    public Person(int personsId,
                  String firstName,
                  String lastName,
                  String birthdate,
                  String email,
                  String phoneNr,
                  String street,
                  String houseNumber,
                  String postalCode,
                  String city) {

        this.personsId = personsId;                    // ID setzen
        this.firstName = firstName;                    // Vorname setzen
        this.lastName = lastName;                      // Nachname setzen
        this.birthdate = birthdate;                    // Geburtsdatum setzen
        this.email = email;                            // E-Mail setzen
        this.phoneNr = phoneNr;                        // Telefonnummer setzen
        this.street = street;                          // Straße setzen
        this.houseNumber = houseNumber;                // Hausnummer setzen
        this.postalCode = postalCode;                  // PLZ setzen
        this.city = city;                              // Stadt setzen
    }

    public int getPersonsId() {                         // Getter ID
        return personsId;
    }

    public String getFirstName() {                      // Getter Vorname
        return firstName;
    }

    public String getLastName() {                       // Getter Nachname
        return lastName;
    }

    public String getBirthdate() {                      // Getter Geburtsdatum
        return birthdate;
    }

    public String getEmail() {                          // Getter E-Mail
        return email;
    }

    public String getPhoneNr() {                        // Getter Telefon
        return phoneNr;
    }

    public String getStreet() {                         // Getter Straße
        return street;
    }

    public String getHouseNumber() {                    // Getter Hausnummer
        return houseNumber;
    }

    public String getPostalCode() {                     // Getter PLZ
        return postalCode;
    }

    public String getCity() {                           // Getter Stadt
        return city;
    }
}
