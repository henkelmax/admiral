package de.maxhenkel.admiral.impl.permissions;

import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.admiral.annotations.RequiresPermissionLevel;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionAnnotationUtil {

    public static <S> List<Permission<S>> getPermissions(Class<?> clazz) {
        return getPermission(Arrays.asList(clazz.getDeclaredAnnotationsByType(RequiresPermission.class)), clazz.getDeclaredAnnotation(RequiresPermissionLevel.class));
    }

    public static <S> List<Permission<S>> getPermissions(Method method) {
        return getPermission(Arrays.asList(method.getDeclaredAnnotationsByType(RequiresPermission.class)), method.getDeclaredAnnotation(RequiresPermissionLevel.class));
    }

    private static <S> List<Permission<S>> getPermission(List<RequiresPermission> requiredPermissions, @Nullable RequiresPermissionLevel requiredPermissionLevel) {
        List<Permission<S>> permissions = new ArrayList<>();
        for (RequiresPermission requiredPermission : requiredPermissions) {
            permissions.add(new PermissionNode<>(requiredPermission.value()));
        }
        if (requiredPermissionLevel != null) {
            permissions.add(new PermissionLevel<>(requiredPermissionLevel.value()));
        }
        return permissions;
    }

}
