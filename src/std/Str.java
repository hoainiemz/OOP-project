package std;

import java.util.Comparator;

public class Str {
    /**
     * convert String str to int
     */
    public static int stoi(String str) {
        int s = 0;
        for (int i = 0; i < str.length(); i++) {
            if ('0' <= str.charAt(i) && str.charAt(i) <= '9') {
                s = s * 10 + str.charAt(i) - '0';
            }
        }
        return s;
    }

    public static int nthIndexOf(String str, int ch, int n) {
        int i = 0;
        for (int j = 0; j < n; j++) {
            i = str.indexOf(ch, i + 1);
            if (i == -1) {
                return -1;
            }
        }
        return i;
    }

    public static String itos(int x) {
        StringBuilder str =  new StringBuilder();
        while (x != 0) {
            str.insert(0, x % 10);
            x /= 10;
        }
        return str.toString();
    }

    class StringComparator implements Comparator<String> {
        @Override
        public int compare(String p1, String p2) {
            return p1.compareTo(p2);
        }
    }
}
