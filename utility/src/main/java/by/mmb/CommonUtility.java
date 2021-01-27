package by.mmb;

import by.mmb.exception.AppsException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author andrew.maksimovich
 */
public final class CommonUtility {
    private CommonUtility() throws AppsException {
        throw new AppsException(() -> "Нельзя создать объект этого класса!");
    }

    public static LocalDateTime convert(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
