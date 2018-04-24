package org.rest.dao;

/**
 * Databases factory
 */
public abstract class DAOFactory
{
    public abstract MongoStudentDAO getMongoStudentDAO();

    public abstract MongoCourseDAO getMongoCourseDAO();

    public abstract MongoIdGeneratorDAO getMongoIdGeneratorDAO();

    public static DAOFactory getDAOFactory()
    {
        DAOFactory factory = null;
        factory = new MongoDAOFactory();

        return factory;
    }
}