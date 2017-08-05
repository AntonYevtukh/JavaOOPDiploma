package air_tickets.trash;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;

/**
 * Created by Anton on 04.08.2017.
 */
public class jsonParser<T> implements Parser<T> {

    public String toOutputString(T object) {
        return toOutputString(object, 0);
    }

    public String toOutputString(T object, int level) {
        int nextlevel = level++;
        String spaces = "";
        for (int i = 0; i < nextlevel; i++) {
            spaces += "    ";
        }
        String shiftSpaces = spaces + "    ";
        StringJoiner joiner;
        try {
            Class<?> objectClass = object.getClass();
            if (object instanceof Collection<?>) {
                joiner = new StringJoiner(",\n",spaces + "{\n","\n" + spaces + "}");
                Collection collection = (Collection) object;
                for (Object element : collection) {
                    Class<?> elementClass = element.getClass();
                    String objectString;
                    if (elementClass.getName().contains("air_tickets") || element instanceof Collection<?>)
                        objectString = getOutputString(element, elementClass, nextlevel + 1);
                    else
                        objectString = element.toString();
                    joiner.add(objectString);
                }
            }
            else {
                joiner = new StringJoiner(",\n" + shiftSpaces,spaces + "{\n" + shiftSpaces,"\n" + spaces + "}");
                for (Field field : objectClass.getDeclaredFields()) {
                    Class<?> fieldClass = field.getType();
                    if (!field.isAccessible())
                        field.setAccessible(true);

                    if (isPrimitive(field))
                        joiner.add("\"" + field.getName() + "\" : " + String.valueOf(field.get(object)));
                    else if (isPrimitiveShell(field))
                        joiner.add("\"" + field.getName() + "\" : " + field.get(object));
                    else if (field.getType().equals(String.class))
                        joiner.add("\"" + field.getName() + "\" : \"" + field.get(object) + "\"");
                    else {
                        String objectString;
                        if (fieldClass.getName().contains("air_tickets") || (field.get(object) instanceof Collection<?>))
                            objectString = getOutputString(field.get(object), fieldClass, nextlevel);
                        else
                            objectString = field.get(object).toString();
                        joiner.add("\"" + field.getName() + "\" : " + objectString + "\"");
                    }
                }
            }
            return joiner.toString();
        } catch (Exception exc) {
            System.err.println(exc);
            exc.printStackTrace();
        }
        return null;
    }

    private boolean isPrimitive(Field field) {
        Class<?> fieldClass = field.getType();
        if (fieldClass.equals(byte.class) || fieldClass.equals(short.class) || fieldClass.equals(int.class)
                || fieldClass.equals(long.class) || fieldClass.equals(char.class) || fieldClass.equals(boolean.class)
                || fieldClass.equals(float.class) || fieldClass.equals(double.class))
            return true;
        return false;
    }

    private boolean isPrimitiveShell(Field field) {
        Class<?> fieldClass = field.getType();
        if (fieldClass.equals(Byte.class) || fieldClass.equals(Short.class) || fieldClass.equals(Integer.class)
                || fieldClass.equals(Long.class) || fieldClass.equals(Character.class) || fieldClass.equals(Boolean.class)
                || fieldClass.equals(Float.class) || fieldClass.equals(Double.class))
            return true;
        return false;
    }

    private <O> String getOutputString(Object o, Class<O> classToken, int level) {
        //System.err.println("Class " + classToken);
        //System.out.println("In getOutputString");
        jsonParser<O> parser = new jsonParser<O>();
        O castedObject = classToken.cast(o);
        return parser.toOutputString(castedObject, level);
    }
}
