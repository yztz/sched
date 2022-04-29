package top.yztz.sched.utils;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import top.yztz.sched.persistence.FieldName;
import top.yztz.sched.persistence.NestField;

public class ReflectUtils {
    private static final String TAG = "ReflectUtils";


    public static boolean isNestField(Field field) {
        return field.getDeclaredAnnotation(NestField.class) != null;
    }

    public static String getFieldName(Field field) {
        FieldName fieldName = field.getDeclaredAnnotation(FieldName.class);
        if (null != fieldName)
            return fieldName.value();
        else
            return field.getName();
    }

    private static Field getField(Class<?> clazz, String name) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            } else {
                FieldName fieldName = field.getDeclaredAnnotation(FieldName.class);
                NestField nestField = field.getDeclaredAnnotation(NestField.class);
                if (null != nestField) {
                    Field ans = getField(field.getType(), name);
                    if (ans != null) return ans;
                } else if (null != fieldName && name.equals(fieldName.value())) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Method getSetter(Class<?> clazz, Field field) {
        String fieldName = field.getName();
        String setterName = "set" + StringUtils.firstToUpperCase(fieldName);
        try {
            return clazz.getDeclaredMethod(setterName, field.getType());
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "getSetter: method" + setterName + " not found");
            return null;
        }
    }
}
