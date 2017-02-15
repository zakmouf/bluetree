package com.bluetree.util;

import java.text.MessageFormat;

public class MessageUtil {

    public static String msg(String pattern, Object obj) {
        return MessageFormat.format(pattern, new Object[] { obj });
    }

    public static String msg(String pattern, Object obj1, Object obj2) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2 });
    }

    public static String msg(String pattern, Object obj1, Object obj2,
            Object obj3) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2, obj3 });
    }

    public static String msg(String pattern, Object obj1, Object obj2,
            Object obj3, Object obj4) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2, obj3,
                obj4 });
    }

    public static String msg(String pattern, Object obj1, Object obj2,
            Object obj3, Object obj4, Object obj5) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2, obj3,
                obj4, obj5 });
    }

    public static String msg(String pattern, Object obj1, Object obj2,
            Object obj3, Object obj4, Object obj5, Object obj6) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2, obj3,
                obj4, obj5, obj6 });
    }

    public static String msg(String pattern, Object obj1, Object obj2,
            Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        return MessageFormat.format(pattern, new Object[] { obj1, obj2, obj3,
                obj4, obj5, obj6, obj7 });
    }

}