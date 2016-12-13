package cn.world.liuhui.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liuhui on 2016/6/19.
 */
public class GsonUtil {
    /**
     * @param json json字符串
     * @param clazz
     * @param <T>
     * @return
     * @brief 将JSON转为实体
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {

        Gson gson = new Gson();
        try {
            T t = gson.fromJson(json, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param obj
     * @return
     * @brief 将一个对象装为Json格式的字符串
     */
    public static String bean2json(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 把json字符串解析成集合
     * params: new TypeToken<List<AppInfo>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }
}
