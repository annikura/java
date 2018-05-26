 import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test method annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    /**
     * Test can be ignored by setting this field to non-empty string stating cause of being ignored.
     * @return "" is method isn't ignored, cause of ignore otherwise
     */
    String ignored() default "";

    /**
     * If test is expecting exception to be thrown, expected field can
     * be set to the Class of the exception which is expected.
     * @return expected exception or NoException.class if no exception is thrown.
     */
    Class<? extends Exception> expected() default NoException.class;
}

 /**
  * Exception class indicating that no exception really is ecpected.
  */
 class NoException extends Exception { }
