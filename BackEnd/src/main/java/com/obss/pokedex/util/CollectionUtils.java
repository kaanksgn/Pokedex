package com.obss.pokedex.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Objects;

@UtilityClass
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> roleNames){
        return Objects.isNull(roleNames) || roleNames.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> roleNames){
        return !isEmpty(roleNames);
    }
}
