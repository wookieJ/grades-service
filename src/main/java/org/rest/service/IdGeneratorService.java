package org.rest.service;

import org.rest.dao.DAOFactory;
import org.rest.dao.MongoIdGeneratorDAO;

/**
 * Service which provides unique id generation
 */
public class IdGeneratorService {
    /**
     * Generating new unique student index
     *
     * @return new unique index value
     */
    public int generateStudentIndex() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoIdGeneratorDAO mongoIdGeneratorDAO = factory.getMongoIdGeneratorDAO();
        return mongoIdGeneratorDAO.generateStudentIndex();
    }

    /**
     * Generating new unique course id
     *
     * @return new unique id value
     */
    public int generateCourseId() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MongoIdGeneratorDAO mongoIdGeneratorDAO = factory.getMongoIdGeneratorDAO();
        return mongoIdGeneratorDAO.generateCourseId();
    }
}
