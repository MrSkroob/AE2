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
        // math.random max is *close* to 1.0, but no cigar. 
        return (int)(Math.random() * upperBound); 
    }

    private static String[] generateFixedStrings() {
        // FIXME:
        // for a string length of 6, we should get 2 characters.
        int skip = Math.max(1, STRING_LENGTH / 3);

        String[] array = new String[STRING_LENGTH];

        for (int i = 0; i < STRING_LENGTH; i += skip) {
            array[i] = Character.toString((char)randint(ASCII_RANGE));
        }        

        return array;
    }

    public static String[] hashCodeKiller(int strings, String[] fixedStrings) {
        if (strings < 2) {
            throw new IllegalArgumentException("Strings must be more greater or equal to two.");
        }

        // Since hashCode samples at three parts of the string, 
        // we only need to ensure those parts of the strings remain identical. 
        String[] array = new String[strings];

        // Populate the array initially with the important strings that the
        // hash function will calculate with.
        for (int j = 0; j < strings; j++) {
            StringBuilder string = new StringBuilder();
            
            for (int i = 0; i < STRING_LENGTH; i++) {
                if (fixedStrings[i] != null) {
                    // array[i] = fixedStrings[i];
                    string.append(fixedStrings[i]);
                    continue;
                }
                int asciiCode = randint(ASCII_RANGE);
                String character = Character.toString(asciiCode);
                string.append(character);
            }

            array[j] = string.toString();
        }
        
        return array;
    }


    public static void main(String[] args) {
        String[] strings = hashCodeKiller(5, generateFixedStrings());
        
        // It's certain hashCode will never < 0 since ascii starts from
        // 0 always.
        int prevHashCode = -1;
        for (String string : strings) {
            System.out.println(string);
            int hashCode = hashCode(string);
            if (prevHashCode != -1 && hashCode != prevHashCode) {
                throw new RuntimeException("Hashcode returned a non-identical hashcode...");
            }
            prevHashCode = hashCode;
        }
    }
}
