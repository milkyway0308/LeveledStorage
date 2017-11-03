package milkyway.LeveledStorage.Util.GenericsResolver;

import java.util.HashMap;

public class ResolverStorage {
    static {
        resolvers = new HashMap<>();
    }

    private static HashMap<Integer, TypeResolver> resolvers;

    public static void registerResolver(GenericsResolver.ItemType resolver) {
        resolvers.put(resolver.name().hashCode(), resolver.getResolver());
    }

    public static TypeResolver getResolver(int resolved) {
        if (resolvers.size() == 0)
            for (GenericsResolver.ItemType ignored : GenericsResolver.ItemType.values());
        return resolvers.get(resolved);
    }
}
