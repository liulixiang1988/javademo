package liulx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import spark.ResponseTransformer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class JsonUtil {
    private static GsonBuilder builder = new GsonBuilder();

    private static final NumberTypeAdapter IntAdapter = new NumberTypeAdapter(Integer::parseInt);
    private static final NumberTypeAdapter DoubleAdapter = new NumberTypeAdapter(Double::parseDouble);
    private static final NumberTypeAdapter LongAdapter = new NumberTypeAdapter(Long::parseLong);
    private static final NumberTypeAdapter FloatAdapter = new NumberTypeAdapter(Float::parseFloat);

    static {
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls();
    }

    public static <T> String toJson(T object) {
        return builder.create().toJson(object);
    }

    public static <T> String toJson(T object, Class<T> valueTpye) {
        return builder.create().toJson(object);
    }

    public static <T> String toJson(T object, Type typeOfT) {
        return builder.create().toJson(object, typeOfT);
    }

    public static <T> T toObject(String json, Class<T> aClass) {
        return builder.create().fromJson(json, aClass);
    }

    public static <T> T toObject(String json, Type typeOfT) {
        return builder.create().fromJson(json, typeOfT);
    }

    public static Map<String,String> toMap(String json){
        final Type type = new TypeToken<Map<String, String>>() {}.getType();
        return toObject(json, type);
    }

    public static <T> T toObjectWithAdapter(String json,Type typeOfT){
        builder
                .registerTypeAdapter(int.class, IntAdapter)
                .registerTypeAdapter(Integer.class, IntAdapter)
                .registerTypeAdapter(Double.class, DoubleAdapter)
                .registerTypeAdapter(double.class, DoubleAdapter)
                .registerTypeAdapter(Long.class, LongAdapter)
                .registerTypeAdapter(long.class, LongAdapter)
                .registerTypeAdapter(float.class, FloatAdapter)
                .registerTypeAdapter(Float.class, FloatAdapter)
                .serializeNulls().create();
        return builder.create().fromJson(json,typeOfT);
    }


    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    private interface NumberConverter{
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
}
