package milkyway.LeveledStorage.Util;

public class PatternCrashReplacer {
    public static String replacePatternCrashItems(String a) {
        return a.replace("<", "\\<")
                .replace(">", "\\>")
                .replace("+", "\\+")
                .replace("-", "\\-")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("|", "\\|")
                .replace("*", "\\*")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("~", "\\~")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace(".", "\\.");
    }
}
