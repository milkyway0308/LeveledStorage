package milkyway.LeveledStorage.Util;

public class StringUtil {
    public static int extractIntegers(String text) {
        class FormattingException extends RuntimeException {
            private FormattingException(String ex, char exChar) {
                super("Character " + exChar + " at " + ex + " is not number");
            }
        }

        class OverflowException extends RuntimeException {
            private OverflowException(String conversion) {
                super("Number format string " + conversion + " is overflowed");
            }
        }
        if(text.length() <= 0)
            return 0;
        int stack = 0;
        boolean minus = false;
        char c = text.charAt(0);
        if (c == '-') {
            minus = true;
            c = 1;
        } else if (c == '+')
            c = 1;
        else c = 0;

        for (int i = c; i < text.length(); i++) {

            c = text.charAt(i);

            if (c >= '0' && c <= '9') {
                int j = c - 48;
                stack = (stack * 10) - j;
            } else
                throw new FormattingException(text, c);
            if (stack > 0)
                throw new OverflowException(text);
        }
        return minus ? stack : -stack;
    }

    public static long extractLong(String text) {
        class FormattingException extends RuntimeException {
            private FormattingException(String ex, char exChar) {
                super("Character " + exChar + " at " + ex + " is not number");
            }
        }

        class OverflowException extends RuntimeException {
            private OverflowException(String conversion) {
                super("Number format string " + conversion + " is overflowed");
            }
        }
        if(text.length() <= 0)
            return 0;
        long stack = 0;
        boolean minus = false;
        char c = text.charAt(0);
        if (c == '-') {
            minus = true;
            c = 1;
        } else if (c == '+')
            c = 1;
        else c = 0;

        for (int i = c; i < text.length(); i++) {

            c = text.charAt(i);

            if (c >= '0' && c <= '9') {
                int j = c - 48;
                stack = (stack * 10) - j;
            } else
                throw new FormattingException(text, c);
            if (stack > 0)
                throw new OverflowException(text);
        }
        return minus ? stack : -stack;
    }


}
