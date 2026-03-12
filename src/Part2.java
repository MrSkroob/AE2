import java.lang.reflect.Array;

import javax.print.DocFlavor.STRING;

public class Part2 {
    private static final int ASCII_RANGE = 128;
    private static final int STRING_LENGTH = 6;

    // from Part 2 example:
    public static int hashCode(String s) {
        int hash = 0;

        // gets approximately amount to skip so we
        // sample only three parts of the string. 
        int skip = Math.max(1, s.length() / 3);

        for (int i = 0; i < s.length(); i += skip) {
            hash = (hash * 37) + s.charAt(i);
        }

        return hash;
    }

    public static int randint(int upperBound) {
        // not inclusive
        return (int)(Math.random() * upperBound); 
    }

    private static String[] generateFixedStrings() {
        // FIXME:
        // for a string length of 6, we should get 2 characters.
        int skip = Math.max(1, STRING_LENGTH / 3);
        String[] array = new String[skip + 1];

        for (int i = 0; i < array.length; i++) {
            array[i] = Character.toString(randint(ASCII_RANGE));
        }        

        return array;
    }

    public static String[] hashCodeKiller(int strings, String[] fixedStrings) {
        if (strings < 2) {
            throw new IllegalArgumentException("Strings must be more greater or equal to two.");
        }

        for (String string : fixedStrings) {
            System.out.println(string);
        }
        // Since hashCode samples at three parts of the string, 
        // we only need to ensure those parts of the strings remain identical. 
        String[] array = new String[strings];

        // Populate the array initially with the important strings that the
        // hash function will calculate with.
        int skip = Math.max(1, STRING_LENGTH / 3);
        int fixedStringI = 0;
        for (int i = 0; i < STRING_LENGTH; i += skip) {
            array[i] = fixedStrings[fixedStringI];
            fixedStringI++;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                continue;
            }
            String character = Character.toString(randint(ASCII_RANGE));
            array[i] = character;
        }
        
        return array;
    }


    public static void main(String[] args) {
        String[] strings = hashCodeKiller(5, generateFixedStrings());
        
        // It's certain hashCode will never < 0 since ascii starts from
        // 0 always.
        int prevHashCode = -1;
        for (String string : strings) {
            int hashCode = hashCode(string);
            if (prevHashCode != -1 && hashCode != prevHashCode) {
                System.out.println("Uh oh! hashCodeKiller didn't work :(");
                break;
            }
            prevHashCode = hashCode;
        }
    }
}
