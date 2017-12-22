import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Reflector {
    private static final String OBJECT = "java.lang.Object";

    public static void printStructure(final @NotNull Class<?> cls) {
        List<String> strings = convertClassToStrings(cls);
        try (PrintWriter writer = new PrintWriter(cls.getName() + ".java", "UTF-8")) {
            for (String str : strings) {
                writer.println(str);
            }
        } catch (IOException e) {
            System.out.println("Sorry, some IO error occurred: " + e.getMessage());
        }
    }

    public static void diffClasses(final @NotNull Class<?> cls1, final @NotNull Class<?> cls2) {
        List<String> cls1Strings1 = convertClassToStrings(cls1);
        List<String> cls1Strings2 = convertClassToStrings(cls2);
        List<String> firstDiff =  getFirstHasSecondDoesnt(cls1Strings1, cls1Strings2);
        List<String> secondDiff = getFirstHasSecondDoesnt(cls1Strings2, cls1Strings1);
        if (!firstDiff.isEmpty()) {
            System.out.println("Methods, fields and classes which were declared in the first given class" +
                    " and not declared in the second");
            for (String str : firstDiff)
                System.out.println(str);
        }
        if (!secondDiff.isEmpty()) {
            System.out.println("Methods, fields and classes which were declared in the second given class" +
                    " and not declared in the first");
            for (String str : secondDiff)
                System.out.println(str);
        }
    }

    private static List<String> getFirstHasSecondDoesnt(List<String> cls1Strings1, List<String> cls1Strings2) {
        HashSet<String> set = new HashSet<>();
        set.addAll(cls1Strings1);
        set.removeAll(cls1Strings2);
        return new ArrayList<>(set);
    }

    /**
     * Prints the class into a list.
     * @param cls class to be printed.
     * @return a list of strings, line-by-line describing a class represented by a given Class instance
     */
    @NotNull
    private static List<String> convertClassToStrings(final @NotNull Class<?> cls) {
        ArrayList<String> result = new ArrayList<>();
        result.add(cls.toGenericString() + " " + getStringSuper(cls) + " {");
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

    @NotNull
    private static String joinList(final @NotNull List<String> list) {
        return list.stream().collect(Collectors.joining("\n", "", "\n"));
    }

    @NotNull
    private static List<String> convertInterfaceToStrings(final @NotNull Class<?> cls) {
        List<String> result = new ArrayList<>();
        result.add(String.join(
                " ",
                Modifier.toString(cls.getModifiers()),
                "interface",
                cls.getName(),
                getStringSuper(cls),
                "{"));
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

    @NotNull
    private static List<String> getStringFields(final @NotNull Class<?> cls) {
        List<String> result = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            result.add(convertFieldToString(field));
        }
        return result;
    }

    @NotNull
    private static List<String> getStringDeclaredClasses(final @NotNull Class<?> cls) {
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

    @NotNull
    private static String convertFieldToString(final @NotNull Field field) {
        return String.join(" ",
                Modifier.toString(field.getModifiers()),
                getTypeString(field.getGenericType()),
                field.getName(),
                "=",
                standardValue(field.getType()));
    }

    @NotNull
    private static List<String> addTabs(final @NotNull List<String> list) {
        return list.stream().map(s -> "\t" + s).collect(Collectors.toList());
    }

    @NotNull
    private static List<String> convertMethodToStrings(final @NotNull Method method) {
        List<String> result = new ArrayList<>();
        result.add(getMethodSignature(method) + " {");
        result.add("\treturn " + standardValue(method.getReturnType()) + ";");
        result.add("}");
        return result;
    }

    @NotNull
    private static String getMethodSignature(final @NotNull Method method) {
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

    @NotNull
    private static String standardValue(final @NotNull Class<?> type) {
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

    @NotNull
    private static String getStringSuper(final @NotNull Class<?> cls) {
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

    @NotNull
    private static String getStringSuperclass(final @NotNull Class<?> cls) {
        if (cls.getSuperclass() == null)
            return "";
        String superclass = getTypeString(cls.getGenericSuperclass());
        if (superclass.equals(OBJECT))
            return "";
        return "extends " + superclass;
    }

    @NotNull
    private static String getStringSuperInterfaces(final @NotNull Class<?> cls) {
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

    @NotNull
    private static String getTypeString(final @NotNull Type type) {
        if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            return getTypeString(genericArrayType.getGenericComponentType()) + "[]";
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return getTypeString(parameterizedType.getRawType()) +
                   genericParametersToString(parameterizedType.getActualTypeArguments());
        }
        if (type instanceof  TypeVariable<?>) {
            return getTypeVariableString((TypeVariable<?>) type);
        }
        if (type instanceof WildcardType) {
            return getWildcardString((WildcardType) type);
        }
        return type.getTypeName();
    }

    @NotNull
    private static String getTypeVariableString(final @NotNull TypeVariable<?> typeVariable) {
        StringBuilder result = new StringBuilder(typeVariable.getName());
        boolean putAmpersand = false;
        for (Type bound : typeVariable.getBounds()) {
            if (bound.getTypeName().equals(OBJECT)) {
                continue;
            }
            if (putAmpersand) {
                result.append(" & ");
            } else {
                result.append(" extends ");
                putAmpersand = true;
            }
            result.append(getTypeString(bound));
        }
        return result.toString();
    }

    @NotNull
    private static String getWildcardString(final @NotNull WildcardType wildcardType) {
        StringBuilder result = new StringBuilder("?");
        boolean addSuper = false;
        for (Type lowerBound : wildcardType.getLowerBounds()) {
            if (lowerBound.getTypeName().equals(OBJECT)) {
                return OBJECT;
            }
            assert !addSuper;
            result.append(" super ");
            addSuper = true;
            result.append(getTypeString(lowerBound));
        }

        boolean addExtends = false;
        for (Type upperBound : wildcardType.getUpperBounds()) {
            if (upperBound.getTypeName().equals(OBJECT)) {
                continue;
            }
            if (addExtends) {
                result.append(" & ");
            }
            if (!addExtends) {
                result.append(" extends ");
                addExtends = true;
            }
            result.append(getTypeString(upperBound));
        }
        return result.toString();
    }

    @NotNull
    private static String genericParametersToString(final @NotNull Type[] typeParameters) {
        if (typeParameters.length == 0) {
            return "";
        }
        return Arrays.stream(typeParameters)
                .map(Reflector::getTypeString)
                .collect(Collectors.joining(", ", "<", ">"));
    }
}