package com.asdeire.autorent.persistence.util;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class CategoryDeserializer implements JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        return new Category(json.getAsString());
    }
}
