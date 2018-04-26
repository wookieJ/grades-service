package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.rest.model.IdGenerator;
import org.rest.model.Student;

import java.util.List;

// TODO - komentarze
public class MongoIdGeneratorDAO extends MongoGenericDAO<IdGenerator, Integer> {
    public int generateStudentIndex() {
        int id = 0;
        Query<IdGenerator> query = super.datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            super.create(new IdGenerator(0, 0, 0));
        } else {
            int newStudentId = query.get().getStudentId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("studentId", newStudentId);
            datastore.findAndModify(query, updateOperations);
            id = newStudentId;
        }
        return id;
    }

    public int generateCourseId() {
        int id = 0;
        Query<IdGenerator> query = super.datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            super.create(new IdGenerator(0, 0, 0));
        } else {
            int newCourseId = query.get().getCourseId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("courseId", newCourseId);

            datastore.findAndModify(query, updateOperations);
            id = newCourseId;
        }
        return id;
    }

    public int generateGradeId() {
        int id = 0;
        Query<IdGenerator> query = super.datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            super.create(new IdGenerator(0, 0, 0));
        } else {
            int newGradeId = query.get().getGradeId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("gradeId", newGradeId);
            datastore.findAndModify(query, updateOperations);
            id = newGradeId;
        }
        return id;
    }

    @Override
    public IdGenerator read(Integer primaryKey) {
        // TODO - napisać logikę
        return null;
    }

    @Override
    public boolean update(IdGenerator updateObject) {
        // TODO - uzupełnić
        return false;
    }

    @Override
    public List<IdGenerator> getAll() {
        final Query<IdGenerator> query = super.datastore.createQuery(IdGenerator.class);
        final List<IdGenerator> ids = query.asList();
        return ids;
    }
}
