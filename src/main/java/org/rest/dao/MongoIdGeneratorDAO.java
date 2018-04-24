package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.rest.model.IdGenerator;

import java.util.List;

public class MongoIdGeneratorDAO extends MongoGenericDAO<IdGenerator, Integer> {
    public int incrementStudentId() {
        int id = 0;
        Query<IdGenerator> query = super.datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            super.create(new IdGenerator(0, 0));
        } else {
            int newStudentId = query.get().getStudentId() + 1;
            int courseId = query.get().getCourseId();
            IdGenerator newIdGenerator = new IdGenerator(newStudentId, courseId);

            // TODO - findAndModify
            super.datastore.findAndDelete(query);
            super.create(newIdGenerator);

            id = newStudentId;
        }
        return id;
    }

    @Override
    public List<IdGenerator> getAll() {
        final Query<IdGenerator> query = super.datastore.createQuery(IdGenerator.class);
        final List<IdGenerator> ids = query.asList();
        return ids;
    }
}
