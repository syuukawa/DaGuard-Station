package com.xwings.coin.station.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * json util - via Jackson.
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private static final ObjectWriter writer;

    static {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        writer = mapper.writer();
    }

    /**
     * Parse json string to java object of specified class.
     *
     * @param jsonStr
     * @param clazz
     * @return T
     * @throws IOException
     */
    public static <T> T parseAsObject(String jsonStr, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonStr, clazz);
    }

    /**
     * Parse json string to java object of specified class.
     *
     * @param is
     * @param clazz
     * @return T
     * @throws IOException
     */
    public static <T> T parseAsObject(InputStream is, Class<T> clazz) throws IOException {
        return mapper.readValue(is, clazz);
    }

    /**
     * Parse json string to java object of specified class.
     *
     * @param reader
     * @param clazz
     * @return T
     * @throws IOException
     */
    public static <T> T parseAsObject(Reader reader, Class<T> clazz) throws IOException {
        T obj = mapper.readValue(reader, clazz);
        mapper.readValue(reader, (TypeReference) new TypeReference<String>() {
        }.getType());

        return obj;
    }

    public static <T> T parseAsObject(String src, TypeReference valueTypeRef)
            throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(src, valueTypeRef);
    }

    public static <T> T parseComplexObject(String src, Class<T> parametrized, Class<?>... parameterClasses) throws IOException {
        final JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);

        return mapper.readValue(src, javaType);
    }

    /**
     * Parse object to json string.
     *
     * @param obj
     * @return T
     * @throws IOException
     */
    public static <T> String convertToJson(T obj) throws IOException {
        return writer.writeValueAsString(obj);
    }

    /**
     * Parse object to json string.
     *
     * @param obj
     * @return T
     * @throws IOException
     */
    public static <T> String convertToPrettyJson(T obj) throws IOException {
        return writer.withDefaultPrettyPrinter().writeValueAsString(obj);
    }

    public static List<Map<String, Object>> parseListOfJsonObject(String jsonStr) throws IOException {
        final TypeReference<List<Map<String, Object>>> mapType = new TypeReference<List<Map<String, Object>>>() {
        };

        return mapper.readValue(jsonStr, mapType);
    }

    /**
     * Parse json string as map.
     *
     * @param jsonStr
     * @return Map
     * @throws IOException
     */
    public static Map<String, Object> parseAsMap(String jsonStr) throws IOException {
        return mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * Parse json string as tree.
     *
     * @param jsonStr
     * @return JsonNode
     * @throws IOException
     */
    public static JsonNode parseAsTree(String jsonStr) throws IOException {
        return mapper.readTree(jsonStr);
    }

    /**
     * Parse json tree to object.
     *
     * @param node
     * @param clazz
     * @return T
     * @throws IOException
     */
    public static <T> T treeToValue(JsonNode node, Class<T> clazz) throws IOException {
        return mapper.treeToValue(node, clazz);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        return mapper.convertValue(fromValue, toValueType);
    }

    public static <T> List<T> convertList(List fromValue, Class<T> toValueType) throws IllegalArgumentException {
        final JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, toValueType);

        return mapper.convertValue(fromValue, javaType);
    }

    public static <T> List<T> convertList(String fromValue, Class<T> toValueType) throws IllegalArgumentException {
        final JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, toValueType);

        return mapper.convertValue(fromValue, javaType);
    }
}
