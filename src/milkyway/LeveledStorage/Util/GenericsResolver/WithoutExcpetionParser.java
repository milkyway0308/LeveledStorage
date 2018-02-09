package milkyway.LeveledStorage.Util.GenericsResolver;

/**
 * Created by Developer_Unlocated on 2017-05-22.
 */
public class WithoutExcpetionParser {
    public static int parse(String toParse, int defaultInteger) {
        try {
            return Integer.parseInt(toParse);
        } catch (Exception ignored) {
        }
        return defaultInteger;
    }
}
