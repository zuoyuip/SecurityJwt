package org.zuoyu.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * JSON的工具包.
 *
 * @author zuoyu
 * @program jwt
 * @create 2019-10-16 11:20
 **/
public class JsonUtil {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private JsonUtil() {
  }

  /**
   * Bean转换为Json字符串
   *
   * @param o - bean
   * @return Json字符串
   */
  public static String beanToJsonString(Object o) {
    try {
      return OBJECT_MAPPER.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Json字符串转换为Bean
   *
   * @param jsonString - Json字符串
   * @param beanType - bean
   * @param <T> - 类型
   * @return T
   */
  public static <T> T jsonStringToBean(String jsonString, Class<T> beanType) {
    try {
      return OBJECT_MAPPER.readValue(jsonString, beanType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Json字符串转Collection
   * @param jsonString - Json字符串
   * @param beanType - bean
   * @param <T> - 类型
   * @return - Collection<T>
   */
  public static <T> Collection<T> jsonStringToCollection(String jsonString, Class<T> beanType) {
    CollectionType collectionType = OBJECT_MAPPER.getTypeFactory()
        .constructCollectionType(Collection.class, beanType);
    try {
      return OBJECT_MAPPER.readValue(jsonString, collectionType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Json字符串转Set
   * @param jsonString - Json字符串
   * @param beanType - bean
   * @param <T> - 类型
   * @return - Set<T>
   */
  public static <T> Set<T> jsonStringToSet(String jsonString, Class<T> beanType) {
    CollectionType collectionType = OBJECT_MAPPER.getTypeFactory()
        .constructCollectionType(Set.class, beanType);
    try {
      return OBJECT_MAPPER.readValue(jsonString, collectionType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Json字符串转List
   * @param jsonString - Json字符串
   * @param beanType - bean
   * @param <T> - 类型
   * @return - List<T>
   */
  public static <T> List<T> jsonStringToList(String jsonString, Class<T> beanType) {
    CollectionType collectionType = OBJECT_MAPPER.getTypeFactory()
        .constructCollectionType(List.class, beanType);
    try {
      return OBJECT_MAPPER.readValue(jsonString, collectionType);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Object转Json字符串
   * @param o - Object
   * @return - Json字符串
   */
  public static String objectToJsonString(Object o){
    try {
      return OBJECT_MAPPER.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }


}
