import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reflector {
    public static void main(String[] args) {
        printStructure(Reflector.class);
    }

    public static void printStructure(Class<?> cls) {
        List<String> strings = convertClassToStrings(cls);
        try (PrintWriter writer = new PrintWriter(cls.getName() + ".java", "UTF-8")) {
            for (String str : strings) {
                writer.println(str);
            }
        } catch (IOException e) {
            System.out.println("Sorry, some IO error occurred: " + e.getMessage());
        }
    }

    /**
     * Prints the class into a list.
     * @param cls class to be printed.
     * @return a list of strings, line-by-line describing a class represented by a given Class instance
     */
    @NotNull
    static List<String> convertClassToStrings(final @NotNull Class<?> cls) {
        ArrayList<String> result = new ArrayList<>();
        result.add(cls.toGenericString() + " " + getStringSuper(cls) + " {");
        //Todo: add support of extends/implements
        for (Field field : cls.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            result.add("\t" + convertFieldToString(field) + ";");
        }
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isSynthetic()) {
                continue;
            }
            result.add(joinList(addTabs(convertMethodToStrings(method))));
        }
        result.addAll(getStringDeclaredClasses(cls));
        result.add("}");
        return result;
    }

    private static String joinList(List<String> list) {
        return list.stream().collect(Collectors.joining("\n", "", "\n"));
    }

    private static List<String> convertInterfaceToStrings(Class<?> cls) {
        List<String> result = new ArrayList<>();
        result.add(String.join(
                " ",
                Modifier.toString(cls.getModifiers()),
                "interface",
                cls.getName(),
                getStringSuper(cls),
                "{"));
        //Todo: add support of extends;
        result.addAll(addTabs(getStringFields(cls)));
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isSynthetic()) {
                continue;
            }
            result.add(getMethodSignature(method) + ";");
        }
        result.addAll(getStringDeclaredClasses(cls));
        return result;
    }

    private static List<String> getStringFields(Class<?> cls) {
        List<String> result = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            result.add(convertFieldToString(field));
        }
        return result;
    }

    private static List<String> getStringDeclaredClasses(Class<?> cls) {
        List<String> result = new ArrayList<>();
        for (Class<?> inClass : cls.getDeclaredClasses()) {
            if (cls.isSynthetic()) {
                continue;
            }
            List<String> inClassStrings;
            if (cls.isInterface()) {
                inClassStrings =  convertInterfaceToStrings(inClass);
            } else {
                inClassStrings = convertClassToStrings(inClass);
            }
            result.add(joinList(addTabs(inClassStrings)));
        }
        return result;
    }

    private static String convertFieldToString(Field field) {
        return String.join(" ",
                Modifier.toString(field.getModifiers()),
                getTypeString(field.getGenericType()),
                field.getName(),
                "=",
                standardValue(field.getType()));
    }

    private static List<String> addTabs(List<String> list) {
        return list.stream().map(s -> "\t" + s).collect(Collectors.toList());
    }

    private static List<String> convertMethodToStrings(Method method) {
        List<String> result = new ArrayList<>();
        result.add(getMethodSignature(method) + " {");
        result.add("\treturn " + standardValue(method.getReturnType()) + ";");
        result.add("}");
        return result;
    }

    private static String getMethodSignature(Method method) {
        StringBuilder result = new StringBuilder(String.join(" ",
                Modifier.toString(method.getModifiers()),
                genericParametersToString(method.getTypeParameters()),
                getTypeString(method.getGenericReturnType()),
                method.getName())
        );
        result.append('(');
        int cnt = 0;
        for (Type type : method.getGenericParameterTypes()) {
            if (cnt != 0) {
                result.append(", ");
            }
            result.append(getTypeString(type));
            result.append(' ');
            result.append("param").append(cnt);
            cnt++;
        }
        result.append(')');
        return result.toString();
    }

    private static String standardValue(Class<?> type) {
        if (!type.isPrimitive())
            return "null";
        if (type.equals(Boolean.TYPE))
            return "true";
        if (type.equals(Character.TYPE))
            return "'x'";
        if (
            type.equals(Byte.TYPE)     ||
            type.equals(Short.TYPE)    ||
            type.equals(Integer.TYPE)  ||
            type.equals(Long.TYPE)     ||
            type.equals(Float.TYPE)    ||
            type.equals(Double.TYPE)
           )
            return "0";
        if (type.equals(Void.TYPE))
            return "";
        assert false;
        return "";
    }

    private static String getStringSuper(Class<?> cls) {
        String superClass = getStringSuperclass(cls);
        String superInterfaces = getStringSuperInterfaces(cls);
        if (superClass.isEmpty()) {
            return superInterfaces;
        }
        if (superInterfaces.isEmpty()) {
            return superClass;
        }
        return superClass + ", " + superInterfaces;
    }

    private static String getStringSuperclass(Class<?> cls) {
        if (cls.getSuperclass() == null)
            return "";
        return "extends " +  getTypeString(cls.getGenericSuperclass());
    }

    private static String getStringSuperInterfaces(Class<?> cls) {
        if (cls.getGenericInterfaces().length == 0)
            return "";
        StringBuilder result = new StringBuilder("implements ");
        boolean putComma = false;
        for (Type interf : cls.getGenericInterfaces()) {
            if (putComma) {
                result.append(interf);
            }
            result.append(getTypeString(interf));
            putComma = true;
        }
        return result.toString();
    }

    private static String getTypeString(Type type) {
        return type.getTypeName();
    }

    static String genericParametersToString(TypeVariable<?>[] typeParameters) {
        ArrayList<String> result = new ArrayList<>();
        if (typeParameters.length == 0) {
            return "";
        }
        for (TypeVariable parameter : typeParameters) {
            Type[] types = parameter.getBounds();
            if (types.length == 0) {
                result.add(parameter.getName());
                continue;
            }
            //ToDo: replace with checking for wildcards, parametrized values, etc.
            result.add(Arrays.stream(types)
                    .map(Type::getTypeName)
                    .collect(Collectors.joining(
                            " & ",
                            parameter.getName() + " extends ",
                            "")));
        }
        return result.stream().collect(Collectors.joining(", ", "<", ">"));
    }
}