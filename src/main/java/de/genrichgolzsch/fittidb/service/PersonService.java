package de.genrichgolzsch.fittidb.service;                     // Service-Schicht

import de.genrichgolzsch.fittidb.dao.PersonDao;                // DAO für persons
import de.genrichgolzsch.fittidb.dao.HealthDataDao;            // DAO für health_data
import de.genrichgolzsch.fittidb.model.Person;                 // Model Person
import de.genrichgolzsch.fittidb.model.HealthData;             // Model HealthData

import java.sql.SQLException;                                  // SQL-Fehler
import java.util.List;                                         // Liste

/**
 * Service-Klasse für Personen.
 * Stellt sicher, dass Person und HealthData logisch zusammengehören.
 */
public class PersonService {                                   // Klassenbeginn

    private final PersonDao personDao = new PersonDao();       // Person-DAO
    private final HealthDataDao healthDataDao = new HealthDataDao(); // HealthData-DAO

    public List<Person> getAllPersons() throws SQLException {  // Alle Personen laden
        return personDao.getAllPersons();                       // Delegation an DAO
    }

    public HealthData getHealthDataForPerson(int personsId) throws SQLException {
        return healthDataDao.getByPersonId(personsId);         // HealthData zur Person laden
    }

    /**
     * Legt eine neue Person inklusive HealthData an.
     * Beide Datensätze gehören fachlich zusammen.
     */
    public void createPersonWithHealthData(Person person, HealthData healthData)
            throws SQLException {

        personDao.insertPerson(person);                         // Person in DB einfügen
        healthDataDao.insertHealthData(healthData);             // HealthData einfügen
    }

    /**
     * Aktualisiert Gesundheitsdaten einer Person.
     */
    public void updateHealthData(HealthData healthData) throws SQLException {
        healthDataDao.updateHealthData(healthData);             // Update über DAO
    }
}
