package com.asdeire.autorent.persistence.util;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Custom JSON deserializer for converting JSON elements to Category objects.
 */
public class CategoryDeserializer implements JsonDeserializer<Category> {

    /**
     * Deserializes a JSON element to a Category object.
     *
     * @param json    the JSON element to deserialize
     * @param typeOfT the type of the object to deserialize to
     * @param context the deserialization context
     * @return a Category object
     * @throws JsonParseException if an error occurs during deserialization
     */
    @Override
    public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return new Category(json.getAsString());
    }
}
