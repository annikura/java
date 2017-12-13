import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reflector {
    public static void printStructure(Class<?> cls) throws IOException {
        List<String> strings = convertClassToStrings(cls);
        PrintWriter writer = new PrintWriter("SomeClass.java", "UTF-8");
        for (String str : strings) {
            writer.println(str);
        }
        writer.close();
    }

    /**
     * Prints the class into a list.
     * @param cls class to be printed.
     * @return a list of strings, line-by-line describing a class represented by a given Class instance
     */
    @NotNull
    static List<String> convertClassToStrings(final @NotNull Class<?> cls) {
        ArrayList<String> result = new ArrayList<>();
        //ToDo: add "extends..", "implements..."
        result.add(cls.toGenericString()+ " {");
        for (Field field : cls.getDeclaredFields()) {
            result.add(field.toGenericString());
        }
        for (Method method : cls.getDeclaredMethods())
            result.add(method.toGenericString());
        result.add("}");
    }


    static String genericParametersToString(TypeVariable<? extends Class<?>>[] typeParameters) {
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
