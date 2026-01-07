package de.genrichgolzsch.fittidb.service;                     // Service-Paket

import de.genrichgolzsch.fittidb.dao.MembershipDao;            // DAO für memberships
import de.genrichgolzsch.fittidb.dao.MembershipPlanDao;        // DAO für membership_plans
import de.genrichgolzsch.fittidb.model.Membership;             // Model Membership
import de.genrichgolzsch.fittidb.model.MembershipPlan;         // Model MembershipPlan

import java.sql.SQLException;                                  // SQL-Fehler

/**
 * Service-Klasse für Mitgliedschaften.
 */
public class MembershipService {                               // Klassenbeginn

    private final MembershipDao membershipDao = new MembershipDao();         // Membership-DAO
    private final MembershipPlanDao planDao = new MembershipPlanDao();        // Plan-DAO

    public Membership getMembershipForPerson(int personsId) throws SQLException {
        // Mitgliedschaft einer Person laden
        return membershipDao.getByPersonId(personsId);                        // Delegation an DAO
    }

    public void createMembership(Membership membership) throws SQLException {
        // Neue Mitgliedschaft anlegen

        Membership existing = membershipDao.getByPersonId(membership.getPersonsId());
        // Prüfen, ob bereits eine Membership existiert

        if (existing != null) {                                               // Schon vorhanden?
            throw new IllegalStateException("Person hat bereits eine Mitgliedschaft");
        }

        MembershipPlan plan = planDao.getPlanById(membership.getPlanId());
        // Prüfen, ob der gewählte Plan existiert

        if (plan == null) {                                                   // Plan nicht vorhanden?
            throw new IllegalArgumentException("Mitgliedschaftsplan existiert nicht");
        }

        membershipDao.insertMembership(membership);                            // Membership speichern
    }

    public void changeMembershipPlan(int personsId, int newPlanId) throws SQLException {
        // Mitgliedschaftsplan wechseln

        MembershipPlan plan = planDao.getPlanById(newPlanId);
        // Prüfen, ob neuer Plan existiert

        if (plan == null) {                                                   // Plan ungültig?
            throw new IllegalArgumentException("Neuer Mitgliedschaftsplan existiert nicht");
        }

        membershipDao.updateMembershipPlan(personsId, newPlanId);             // Plan aktualisieren
    }

    public void setMembershipActive(int personsId, boolean active) throws SQLException {
        // Aktivstatus setzen (kündigen / reaktivieren)

        membershipDao.setMembershipActive(personsId, active);                 // Status ändern
    }
}
