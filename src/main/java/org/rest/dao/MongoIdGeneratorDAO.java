package org.rest.dao;

import org.mongodb.morphia.query.Query;
import org.rest.model.IdGenerator;

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
            int courseId = query.get().getCourseId();
            int gradeId = query.get().getGradeId();
            IdGenerator newIdGenerator = new IdGenerator(newStudentId, courseId, gradeId);

            // TODO - findAndModify
            super.datastore.findAndDelete(query);
            super.create(newIdGenerator);

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
            int studentId = query.get().getStudentId();
            int newCourseId = query.get().getCourseId() + 1;
            int gradeId = query.get().getGradeId();
            IdGenerator newIdGenerator = new IdGenerator(studentId, newCourseId, gradeId);

            // TODO - findAndModify
            super.datastore.findAndDelete(query);
            super.create(newIdGenerator);

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
            int studentId = query.get().getStudentId();
            int courseId = query.get().getCourseId();
            int newGradeId = query.get().getGradeId() + 1;
            IdGenerator newIdGenerator = new IdGenerator(studentId, courseId, newGradeId);

            // TODO - findAndModify
            super.datastore.findAndDelete(query);
            super.create(newIdGenerator);

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
