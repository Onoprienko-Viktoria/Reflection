package com.luxoft.reflection;

import java.lang.reflect.*;
import java.util.StringJoiner;

public class ReflectionUtils {
    public static Object create(Class clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    public static void invokeMethodsWithoutParameters(Object object) throws InvocationTargetException, IllegalAccessException {
        Class clazz = object.getClass();
        Method[] methodsArray = clazz.getDeclaredMethods();
        for (Method method : methodsArray) {
            if (method.getParameterCount() == 0) {
                if (!method.canAccess(object)) {
                    method.trySetAccessible();
                }
                method.invoke(object);
            }
        }
    }

    public static String getFinalMethodsSignatures(Object object) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Method method : methods) {
            if (Modifier.isFinal(method.getModifiers())) {
                stringJoiner.add(method.getName());
                for (Parameter parameter : method.getParameters()) {
                    stringJoiner.add(parameter.toString());
                }
            }
        }
        return stringJoiner.toString();
    }

    public static String getNonPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                stringJoiner.add(method.getName());
            }
        }
        return stringJoiner.toString();

    }

    public static String getParentsAndInterfaces(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Class[] interfacesArray = clazz.getInterfaces();
        Class parentClass = clazz.getSuperclass();
        for (Class interfaces : interfacesArray) {
            stringJoiner.add(interfaces.toString());
        }
        stringJoiner.add(parentClass.toString());
        return stringJoiner.toString();
    }

    //       Метод принимает объект и меняет все его приватные поля на их нулевые значение (null, 0, false etc)+
    public static void setPrivateFieldsOnZeroValue(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                if (boolean.class.equals(field.getType())) {
                    field.setBoolean(object, false);
                } else if (int.class.equals(field.getType()) || float.class.equals(field.getType()) || double.class.equals(field.getType()) || long.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                    field.set(object, 0);
                } else if (char.class.equals(field.getType())) {
                    field.setChar(object, '\u0000');
                } else {
                    field.set(object, null);
                }
            }
        }
    }
}
