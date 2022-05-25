package br.com.sw2you.realmeet.util;

import java.util.List;
import org.apache.logging.log4j.util.Strings;

public final class StringUtils {

    public static String join(List<String> list) {
        return Strings.join(list, ',');
    }
}
