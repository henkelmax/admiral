package de.maxhenkel.admiral.impl;

import javax.annotation.Nullable;

public class PermissionLevelManager {

    private static PermissionLevelAccessor<?> accessor;

    public static void setPermissionLevelAccessor(PermissionLevelAccessor<?> a) {
        accessor = a;
    }

    @Nullable
    public static <S> PermissionLevelAccessor<S> getPermissionLevelAccessor() {
        return (PermissionLevelAccessor<S>) accessor;
    }

    public interface PermissionLevelAccessor<S> {
        boolean hasPermissionLevel(S source, int level);
    }

}
