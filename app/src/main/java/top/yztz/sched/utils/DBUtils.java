package top.yztz.sched.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DBUtils {
    private static final String TAG = "DBUtils";

    /**
     * 自动对象解析器
     * 要求目标字段与记录同名或者使用FieldName与NestField注解
     * 要求对象类必须含有一个无参构造函数
     */
    public static <T> T parseCursor(Cursor cursor, Class<T> c) {
        try {
            String[] columns = cursor.getColumnNames();
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < columns.length; i++) list.add(i);
            T ans = c.newInstance();
            Queue<Object> queue = new ArrayDeque<>();
            queue.offer(ans);
            while (!queue.isEmpty() && !list.isEmpty()) {
                Object obj = queue.poll();
                Class<?> clazz = obj.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    if (ReflectUtils.isNestField(field)) {
                        field.setAccessible(true);
                        Object n;
                        if (null == (n = field.get(obj))) { // 未初始化
                            n = field.getType().newInstance();
                            field.set(obj, n);
                        }
                        queue.offer(n);
                        continue;
                    }
                    for (int idx : list) {
                        if (columns[idx].equals(ReflectUtils.getFieldName(field))) {
                            Method method = ReflectUtils.getSetter(clazz, field);
                            if (null != method) {
                                method.invoke(obj, getObject(cursor, idx));
                                break;
                            }
                            list.remove(idx);
                        }
                    }

                }
            }

            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ContentValues buildCourse(String name, String teacher, int day, String place, int start, int end, int week) {
        ContentValues ans = new ContentValues();
        ans.put("cname", name);
        ans.put("cteacher", teacher);
        ans.put("cplace", place);
        ans.put("cstart", start);
        ans.put("cend", end);
        ans.put("cweek", week);
        ans.put("cday", day);
        return ans;
    }

    private static Object getObject(Cursor cursor, int idx) {
        Object param = null;
        switch (cursor.getType(idx)) {
            case Cursor.FIELD_TYPE_BLOB:
                param = cursor.getBlob(idx);
                break;
            case Cursor.FIELD_TYPE_FLOAT:
                param = cursor.getFloat(idx);
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                param = cursor.getInt(idx);
                break;
            case Cursor.FIELD_TYPE_STRING:
                param = cursor.getString(idx);
                break;
            case Cursor.FIELD_TYPE_NULL:
                break;
        }

        return param;
    }

}
