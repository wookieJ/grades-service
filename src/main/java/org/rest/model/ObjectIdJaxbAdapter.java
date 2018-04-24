package org.rest.model;

import org.bson.types.ObjectId;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ObjectIdJaxbAdapter extends XmlAdapter<String, ObjectId> {
    @Override
    public ObjectId unmarshal(String v) throws Exception {
        if (v == null)
            return null;
        return new ObjectId(v);
    }

    @Override
    public String marshal(ObjectId v) throws Exception {
        if (v == null)
            return null;
        return v.toString();
    }
}