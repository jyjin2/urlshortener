package urlshortener.app.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class YJConverter {
    public static final YJConverter INSTANCE = new YJConverter();

    private static final String BASE_62_CHAR_VER_220721 = "h5xoLq4Y2vU7MuHkIslbXADKcdjG8pR3raZBJOQwi96NTP1CzyVg0FnWSftEme";
    private static final int BASE_62_SIZE = 62;

    private static final Logger LOGGER = LoggerFactory.getLogger(YJConverter.class);
    private YJConverter() {
//        initializeIndexToCharTable();
//        initializeCharToIndexTable();
        initializeIndexToCharRdTable();
        initializeCharToIndexRdTable();
    }

    private static HashMap<Character, Integer> charToIndexTable;
    private static List<Character> indexToCharTable;

    private static HashMap<Character, Integer> charToIndexRdTable;
    private static List<Character> indexToCharRdTable;

    public static void main(String[] args) {

        LOGGER.debug(indexToCharRdTable.toString());
        LOGGER.debug(charToIndexRdTable.toString());

//        LOGGER.debug(charToIndexRdTable.toString());
    }

    private void initializeCharToIndexRdTable() {
        charToIndexRdTable = new HashMap<>();
        for (int i = 0; i < BASE_62_CHAR_VER_220721.length(); i++) {
            charToIndexRdTable.put(indexToCharRdTable.get(i), i);
        }
    }

    private void initializeIndexToCharRdTable() {
        indexToCharRdTable = new ArrayList<>();
        for (int i = 0; i < BASE_62_CHAR_VER_220721.length(); ++i) {
            indexToCharRdTable.add(BASE_62_CHAR_VER_220721.charAt(i));
        }
    }

    private void initializeCharToIndexTable() {
        charToIndexTable = new HashMap<>();
//        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
//        for (int i = 0; i < 26; ++i) {
//            char c = 'a';
//            c += i;
//            charToIndexTable.put(c, i);
//        }
//        for (int i = 26; i < 52; ++i) {
//            char c = 'A';
//            c += (i-26);
//            charToIndexTable.put(c, i);
//        }
//        for (int i = 52; i < 62; ++i) {
//            char c = '0';
//            c += (i - 52);
//            charToIndexTable.put(c, i);
//        }

        charToIndexTable = new HashMap<>();
        for (int i = 0; i < indexToCharTable.size(); i++) {
            charToIndexTable.put(indexToCharTable.get(i), i);
        }
    }

    private void initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = new ArrayList<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            indexToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharTable.add(c);
        }

        Collections.shuffle(indexToCharTable);
    }

    public static String createUniqueID(Long id) {
        List<Integer> base62ID = convertBase10ToBase62ID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for (int digit: base62ID) {
            uniqueURLID.append(indexToCharTable.get(digit));
        }
        return uniqueURLID.toString();
    }

    private static List<Integer> convertBase10ToBase62ID(Long id) {
        List<Integer> digits = new LinkedList<>();
        if(id == 0L) {
            digits.add(0);
        }
        while(id > 0) {
            int remainder = (int)(id % BASE_62_SIZE);
            ((LinkedList<Integer>) digits).addFirst(remainder);
            id /= BASE_62_SIZE;
        }
        return digits;
    }

    public static Long getDictionaryKeyFromUniqueID(String uniqueID) {
        List<Character> base62IDs = new ArrayList<>();
        for (int i = 0; i < uniqueID.length(); ++i) {
            base62IDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
        return dictionaryKey;
    }

    private static Long convertBase62ToBase10ID(List<Character> ids) {
        long id = 0L;
        int exp = ids.size() - 1;
        for (int i = 0; i < ids.size(); ++i, --exp) {
            int base10 = charToIndexTable.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }
        return id;
    }
}
