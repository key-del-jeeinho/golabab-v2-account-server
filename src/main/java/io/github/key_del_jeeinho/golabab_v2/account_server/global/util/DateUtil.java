package io.github.key_del_jeeinho.golabab_v2.account_server.global.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static LocalDate now() {
        return LocalDate.now();
    }

    public static Date localToDate(LocalDate localDate) {
        return Date.from(
                now()
                        .atStartOfDay(ZONE_ID)
                        .toInstant()
        );
    }
}
