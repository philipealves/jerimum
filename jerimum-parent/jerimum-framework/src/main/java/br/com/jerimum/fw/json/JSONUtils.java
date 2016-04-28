package br.com.jerimum.fw.json;

import java.lang.reflect.Type;
import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

/**
 * Utility class for JSON operations.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public final class JSONUtils {

    private JSONUtils() {

    }

    public static String serialize(Object object) {
        return serialize(null, object);
    }

    public static String serialize(Type type, Object object) {
    	Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        if (type == null) {
            return gson.toJson(object);
        }
        return gson.toJson(object, type);
    }

    public static <T> T deserialize(Class<T> returnType, String jsonText) {

        TypeToken<T> typeToken = new TypeToken<T>() {
        };
        return deserialize(typeToken, jsonText);
    }

    public static <T> T deserialize(TypeToken<T> typeToken, String jsonText) {

        Type type = typeToken.getType();
        return deserialize(type, jsonText);
    }

    public static <T> T deserialize(Type type, String jsonText) {

        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        return gson.fromJson(jsonText, type);
    }
    
    public static <T> T deserialize(TypeToken<T> typeToken, String jsonText, Class<?> deserializerType, JsonDeserializer<?> deserializer) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
    	gsonBuilder.registerTypeAdapter(deserializerType, deserializer);
    	return gsonBuilder.create().fromJson(jsonText, typeToken.getType());
    }

}
