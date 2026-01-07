package de.genrichgolzsch.fittidb.model;                   // Model-Paket

/**
 * Repräsentiert eine Mitgliedschaft einer Person.
 */
public class Membership {                                  // Klassenbeginn

    private int membershipId;                               // Primary Key
    private int personsId;                                  // FK zu persons
    private int planId;                                     // FK zu membership_plans
    private String startDate;                               // Startdatum (YYYY-MM-DD)
    private boolean active;                                 // Aktiv-Status

    public Membership(int membershipId,
                      int personsId,
                      int planId,
                      String startDate,
                      boolean active) {                     // Konstruktor
        this.membershipId = membershipId;                   // ID setzen
        this.personsId = personsId;                         // Personen-ID setzen
        this.planId = planId;                               // Plan-ID setzen
        this.startDate = startDate;                         // Startdatum setzen
        this.active = active;                               // Aktiv-Status setzen
    }

    public int getMembershipId() {                          // Getter membershipId
        return membershipId;                                // Rückgabe
    }

    public int getPersonsId() {                             // Getter personsId
        return personsId;                                   // Rückgabe
    }

    public int getPlanId() {                                // Getter planId
        return planId;                                      // Rückgabe
    }

    public String getStartDate() {                           // Getter startDate
        return startDate;                                   // Rückgabe
    }

    public boolean isActive() {                             // Getter active
        return active;                                      // Rückgabe
    }
}
