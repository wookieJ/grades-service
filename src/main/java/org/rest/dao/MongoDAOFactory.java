package org.rest.dao;

/**
 * MongoDB DAO factory which return MongoStudentDAO
 */
public class MongoDAOFactory extends DAOFactory {
    @Override
    public MongoStudentDAO getMongoStudentDAO() {
        return new MongoStudentDAO();
    }

    @Override
    public MongoCourseDAO getMongoCourseDAO() {
        return new MongoCourseDAO();
    }

    @Override
    public MongoIdGeneratorDAO getMongoIdGeneratorDAO() {
        return new MongoIdGeneratorDAO();
    }
}