package com.yx.appcore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/23
 * @time 5:13 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class GsonUtil {

    private static class GsonHolder {
        //通用版GSON
//      private static final Gson INSTANCE = new Gson();
        private static final Gson INSTANCE = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")//格式化时间
                .create();
    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static Gson getGsonInstance() {
        return GsonHolder.INSTANCE;
    }

    /**
     * 将对象转成json格式字符串
     *
     * @param object
     * @return
     */
    public static String getJsonfromObj(Object object) {
        String str = getGsonInstance().toJson(object);
        return str;
    }

    /**
     * 将json字符串转成特定的class的对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
        return getGsonInstance().fromJson(jsonString, clazz);
    }


    /**
     * json字符串转成list
     *
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
        ////根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
        List<T> list = getGsonInstance().fromJson(jsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    /**
     * json字符串转成map
     * @param jsonString
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> jsonToMaps(String jsonString) {
        Map<String, T> map = getGsonInstance().fromJson(jsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * json字符串转成list中有map的
     * @param jsonString
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonString) {
        List<Map<String, T>> list = getGsonInstance().fromJson(jsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
        return list;
    }


}
