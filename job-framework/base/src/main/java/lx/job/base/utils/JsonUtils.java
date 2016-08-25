package lx.job.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lxliu@iflytek.com on 2016/8/25.
 */
public class JsonUtils {
    public static <T> String toJson(T obj){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj);
    }

    public static <T> String toJson(T obj,Class<T> valueTpye){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj,valueTpye);
    }

    public static <T> String toJson(T obj,Type typeOfT){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj,typeOfT);
    }

    public static <T> T toObject(String json,Class<T> valueType){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(json,valueType);
    }

    public static <T> T toObject(String json,Type typeOfT){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(json,typeOfT);
    }

    public static <T> T toObjectWithAdapter(String json,Type typeOfT){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(int.class, IntAdapter)
                .registerTypeAdapter(Integer.class, IntAdapter)
                .registerTypeAdapter(Double.class, DoubleAdapter)
                .registerTypeAdapter(double.class, DoubleAdapter)
                .registerTypeAdapter(Long.class, LongAdapter)
                .registerTypeAdapter(long.class, LongAdapter)
                .registerTypeAdapter(float.class, FloatAdapter)
                .registerTypeAdapter(Float.class, FloatAdapter)
                .serializeNulls().create();
        return gson.fromJson(json,typeOfT);
    }

    public static Map<String,String> toMap(String json){
        return toObject(json,Map.class);
    }

    public interface NumberConverter{
        Number operation(String obj);
    }

    private static class NumberTypeAdapter extends TypeAdapter<Number> {

        private NumberConverter converter;
        public NumberTypeAdapter(NumberConverter converter) {
            this.converter = converter;
        }
        @Override
        public void write(JsonWriter out, Number value)
                throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return converter.operation(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    }

    public static NumberTypeAdapter IntAdapter = new NumberTypeAdapter(Integer::parseInt);
    public static NumberTypeAdapter DoubleAdapter = new NumberTypeAdapter(Double::parseDouble);
    public static NumberTypeAdapter LongAdapter = new NumberTypeAdapter(Long::parseLong);
    public static NumberTypeAdapter FloatAdapter = new NumberTypeAdapter(Float::parseFloat);

}
