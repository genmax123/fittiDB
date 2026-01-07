package de.genrichgolzsch.fittidb.model;                   // Model-Paket

/**
 * Repräsentiert ein Mitgliedschaftsmodell aus der Tabelle "membership_plans".
 */
public class MembershipPlan {                               // Klassenbeginn

    private int planId;                                      // Primary Key
    private String planName;                                 // Planname (Pro/Ultimate)
    private double monthlyPrice;                             // Monatlicher Preis
    private String description;                              // Beschreibung

    public MembershipPlan(int planId, String planName, double monthlyPrice, String description) { // Konstruktor
        this.planId = planId;                                // ID setzen
        this.planName = planName;                            // Name setzen
        this.monthlyPrice = monthlyPrice;                    // Preis setzen
        this.description = description;                      // Beschreibung setzen
    }

    public int getPlanId() {                                 // Getter planId
        return planId;                                       // Rückgabe
    }

    public String getPlanName() {                            // Getter planName
        return planName;                                     // Rückgabe
    }

    public double getMonthlyPrice() {                        // Getter monthlyPrice
        return monthlyPrice;                                 // Rückgabe
    }

    public String getDescription() {                         // Getter description
        return description;                                  // Rückgabe
    }
}
