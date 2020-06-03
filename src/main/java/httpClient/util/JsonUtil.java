package main.java.httpClient.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * json字符串处理工具类
 */
public class JsonUtil {

    /**
     *
     * @param o
     * @param filter
     * @return
     */
    public static String toJsonString(Object o, PropertyPreFilter filter){
        //JSON导fastjson-1.2.28
        return JSON.toJSONString( o,filter, SerializerFeature.IgnoreErrorGetter );
    }

    /**
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJsonString(String text,Class<T> clazz){
        return JSON.parseObject( text,clazz );
    }

    /**
     *
     * @param o
     * @return
     */
    public static String toJsonString(Object o){
        return JSON.toJSONString( o,SerializerFeature.IgnoreErrorGetter );
    }

    /**
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map<String,Object> map,Class<T> clazz){
        return JSON.parseObject( toJsonString( map ),clazz );
    }

    /**
     * 将json字符串转array
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List< T > fromJsonStringToArray(String text,Class<T> clazz){
        JSONArray array = JSON.parseArray( text );
        final List<T> list = new LinkedList<>(  );
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get( i );
            if(value instanceof JSONObject){
                list.add( fromJsonString( ( (JSONObject) value).toJSONString(),clazz) );
            } else{
                list.add( (T) value);
            }
        }
        return list;
    }
}
