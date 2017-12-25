import com.sun.org.apache.regexp.internal.RE;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.sql.Ref;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class ReflectorTest {
    public void checkEquals(Class<?> cls, String... strs) {
        int cnt = 0;
        for (String str : Reflector.convertClassToStrings(cls)) {
            assert cnt < strs.length;
            assertEquals(strs[cnt], str);
            cnt++;
        }
        assertEquals(strs.length, cnt);
    }

    @Test
    public void printEmptyClass() {
        class EmptyClass {
        }
        Reflector.printStructure(EmptyClass.class);
        File file = new File("ReflectorTest$1EmptyClass.java");
        file.delete();
    }

    @Test
    public void checkEmptyClass() {
        class EmptyClass {
        }
        checkEquals(EmptyClass.class,
                "class ReflectorTest$2EmptyClass {",
                "}");
    }

    @Test
    public void checkIntVariable() {
        class SingleInt {
            int var = 99;
        }
        checkEquals(SingleInt.class,
                "class ReflectorTest$1SingleInt {",
                "\t int var = 0;",
                "}");
    }

    @Test
    public void checkBooleanVariable() {
        class SingleBoolean {
            boolean var = false;
        }
        checkEquals(SingleBoolean.class,
                "class ReflectorTest$1SingleBoolean {",
                "\t boolean var = true;",
                "}");
    }

    @Test
    public void checkPrimitiveArrayVariable() {
        class SinglePrimitiveArray {
            char[] arr = new char[10];
        }
        checkEquals(SinglePrimitiveArray.class,
                "class ReflectorTest$1SinglePrimitiveArray {",
                "\t char[] arr = null;",
                "}");
    }


    @Test
    public void checkObjectArrayVariable() {
        class SingleObjectArray {
            Integer[] arr = new Integer[10];
        }
        checkEquals(SingleObjectArray.class,
                "class ReflectorTest$1SingleObjectArray {",
                "\t java.lang.Integer[] arr = null;",
                "}");
    }

    @Test
    public void checkTemplateArrayVariable() {
        class SingleTemplateArray<T> {
            T[] arr = (T[]) new Object[10];
        }
        checkEquals(SingleTemplateArray.class,
                "class ReflectorTest$1SingleTemplateArray<T> {",
                "\t T[] arr = null;",
                "}");
    }

    @Test
    public void checkObjectVariable() {
        class SingleObject {
            Integer arr = new Integer(10);
        }
        checkEquals(SingleObject.class,
                "class ReflectorTest$1SingleObject {",
                "\t java.lang.Integer arr = null;",
                "}");
    }

    @Test
    public void checkVariableModifiers() {
        class VariableWithModifiers {
            private static final char variable = 'a';
        }
        checkEquals(VariableWithModifiers.class,
                "class ReflectorTest$1VariableWithModifiers {",
                "\tprivate static final char variable = 'x';",
                "}");
    }

    @Test
    public void checkClassWithModifier() {
        abstract class ClassWithModifier {
        }
        checkEquals(ClassWithModifier.class,
                "abstract class ReflectorTest$1ClassWithModifier {",
                "}");
    }

    @Test
    public void checkSingleParameterSimpleGenericClass() {
        class SingleParameterSimpleGenericClass<P> {
        }
        checkEquals(SingleParameterSimpleGenericClass.class,
                "class ReflectorTest$1SingleParameterSimpleGenericClass<P> {",
                "}");
    }

    @Test
    public void checkSimpleGenericClass() {
        class SimpleGenericClass<T, Y> {
        }
        checkEquals(SimpleGenericClass.class,
                "class ReflectorTest$1SimpleGenericClass<T, Y> {",
    "}");
    }

    @Test
    public void checkExtendingGeneric() {
        class ExtendingGenericClass<T extends Throwable & Comparator> {
        }
        checkEquals(ExtendingGenericClass.class,
                "class ReflectorTest$1ExtendingGenericClass<T extends java.lang.Throwable & java.util.Comparator> {",
                "}");
    }

    @Test
    public void checkClassExtendingObject() {
        class ClassExtendingObject<E extends Object> {
        }
        checkEquals(ClassExtendingObject.class,
                "class ReflectorTest$1ClassExtendingObject<E> {",
                "}");
    }

    @Test
    public void simpleIntMethod() {
        class SimpleIntMethod {
            int mth() { return 10; }
        }
        checkEquals(SimpleIntMethod.class,
                "class ReflectorTest$1SimpleIntMethod {",
                "\t  int mth() {\n\t\treturn 0;\n\t}\n",
                "}");
    }


    @Test
    public void simpleVoidMethod() {
        class SimpleVoidMethod {
            void mth() { return; }
        }
        checkEquals(SimpleVoidMethod.class,
                "class ReflectorTest$1SimpleVoidMethod {",
                "\t  void mth() {\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void simpleObjectMethod() {
        class SimpleObjectMethod {
            Integer mth() {
                return 5;
            }
        }
        checkEquals(SimpleObjectMethod.class,
                "class ReflectorTest$1SimpleObjectMethod {",
                "\t  java.lang.Integer mth() {\n\t\treturn null;\n\t}\n",
                "}");
    }

    @Test
    public void methodWithModifiers() {
        class MethodWithModifiers {
            public final void mth() {
            }
        }
        checkEquals(MethodWithModifiers.class,
                "class ReflectorTest$1MethodWithModifiers {",
                "\tpublic final  void mth() {\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void genericMethod() {
        class GenericMethod {
            <T extends Object & Comparable> void mth() {}
        }
        checkEquals(GenericMethod.class,
                "class ReflectorTest$1GenericMethod {",
                "\t <T extends java.lang.Comparable> void mth() {\n\t\treturn ;\n\t}\n",
                "}");
    }

    static class InnerInterface {
        public static interface Int {
            public static int var = 0;
            void mth();
        }
    }

    @Test
    public void innerInterface() {
        checkEquals(InnerInterface.class,
                "static class ReflectorTest$InnerInterface {",
                "\tpublic abstract static interface " +
                        "ReflectorTest$InnerInterface$Int  {" +
                        "\n\t\tpublic static final int var = 0;" +
                        "\n\t\tpublic abstract  void mth();" +
                        "\n\t}\n",
                "}");
    }

    @Test
    public void manyVariables() {
        class ManyVariables {
            int var;
            int var2;
        }
        checkEquals(ManyVariables.class,
                "class ReflectorTest$1ManyVariables {",
                "\t int var = 0;",
                "\t int var2 = 0;",
                "}");
    }

    @Test
    public void innerClass() {
        class InnerClass {
            class Int {
                int var;
                void mth() {};
            }
        }
        checkEquals(InnerClass.class,
                "class ReflectorTest$1InnerClass {",
                "\tclass " +
                        "ReflectorTest$1InnerClass$Int {" +
                        "\n\t\t int var = 0;" +
                        "\n\t\t  void mth() {" +
                        "\n\t\treturn ;" +
                        "\n\t}\n" +
                        "\n\t}\n",
                "}");
    }

    @Test
    public void extendedClass() {
        class ExtendedClass extends HashSet {
        }
        checkEquals(ExtendedClass.class,
                "class ReflectorTest$1ExtendedClass extends java.util.HashSet {",
                "}");
    }


    @Test
    public void implementingClass() {
        class ImplementingClass implements Comparable {
            @Override
            public int compareTo(@NotNull Object o) {
                return 0;
            }
        }
        checkEquals(ImplementingClass.class,
                "class ReflectorTest$1ImplementingClass implements java.lang.Comparable {",
                "\tpublic  int compareTo(java.lang.Object param0) {\n\t\treturn 0;\n\t}\n",
                "}");
    }

    @Test
    public void extendedWithObjectClass() {
        class ObjectExtendedClass extends Object {
        }
        checkEquals(ObjectExtendedClass.class,
                "class ReflectorTest$1ObjectExtendedClass {",
                "}");
    }

    @Test
    public void printDiffOfEqual() {
        class Empty1 {}
        class Empty2 {}
        Reflector.diffClasses(Empty1.class, Empty2.class);
    }


    @Test
    public void printDiffOfDifferent() {
        class NotEqual1 { int varOfFirst; }
        class NotEqual2 { int varOfSecond; }
        Reflector.diffClasses(NotEqual1.class, NotEqual2.class);
    }

    @Test
    public void compareEqualEmpty() {
        class Equal1 {}
        class Equal2 {}
        List<String> result = new ArrayList<>();
        result.add("class ReflectorTest$1Equal1 {");
        assertEquals(result,
                Reflector.getFirstHasSecondDoesnt(
                        Reflector.convertClassToStrings(Equal1.class),
                        Reflector.convertClassToStrings(Equal2.class))
        );
    }

    @Test
    public void methodWithParameters() {
        class MethodWithParameters {
            <T extends Comparable> void mth(Collection<? super T> c, int a) {}
        }
        checkEquals(MethodWithParameters.class,
                "class ReflectorTest$1MethodWithParameters {",
                "\t <T extends java.lang.Comparable> void mth(" +
                        "java.util.Collection<? super T extends java.lang.Comparable> param0, " +
                        "int param1" +
                        ") {\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void syntheticMethod() {
        class SyntheticMethod {
            void mth() {
                assert true;
            }
        }
        checkEquals(SyntheticMethod.class,
                "class ReflectorTest$1SyntheticMethod {",
                "\t  void mth() {\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void superWildcard() {
        class SuperWildcard {
            <T> void mth(Collection<? super T> c) { }
        }
        checkEquals(SuperWildcard.class,
                "class ReflectorTest$1SuperWildcard {",
                "\t <T> void mth(java.util.Collection<? super T> param0) {" +
                        "\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void extendsWildcard() {
        class ExtendsWildcard<T> {
            void mth(Collection<? extends T> c) {}
        }
        checkEquals(ExtendsWildcard.class,
                "class ReflectorTest$1ExtendsWildcard<T> {",
                "\t  void mth(java.util.Collection<? extends T> param0) {" +
                        "\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void wildcardExtendsObject() {
        class WildcardExtendsObject {
            void mth(Collection<? extends Object> c) {}
        }
        checkEquals(WildcardExtendsObject.class,
                "class ReflectorTest$1WildcardExtendsObject {",
                "\t  void mth(java.util.Collection<?> param0) {" +
                        "\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void wildcardSuperObject() {
        class WildcardSuperObject {
            void mth(Collection<? super Object> c) {}
        }
        checkEquals(WildcardSuperObject.class,
                "class ReflectorTest$1WildcardSuperObject {",
                "\t  void mth(java.util.Collection<java.lang.Object> param0) {" +
                        "\n\t\treturn ;\n\t}\n",
                "}");
    }

    @Test
    public void diffReflectorWithItself() throws ClassNotFoundException, MalformedURLException {
        Reflector.printStructure(Reflector.class);

        File sourceFile = new File("Reflector.java");
        File sourceByteCode = new File("Reflector.class");
        sourceFile.deleteOnExit();
        sourceByteCode.deleteOnExit();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getAbsolutePath());

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {
                new File(".")
                        .toURI()
                        .toURL() });
        Class<?> cls = Class.forName("Reflector", true, classLoader);

        assertEquals(new ArrayList<>(), Reflector.getFirstHasSecondDoesnt(
                Reflector.convertClassToStrings(cls),
                Reflector.convertClassToStrings(Reflector.class))
        );

        assertEquals(new ArrayList<>(), Reflector.getFirstHasSecondDoesnt(
                Reflector.convertClassToStrings(Reflector.class),
                Reflector.convertClassToStrings(cls))
        );
    }
}