package by.mmb.model;

import java.util.Map;

/**
 * Дополнительные параметры для клиента и пользователя
 * Возможность динамического расширения
 */
public class AdditionalParam {
    private long id;
    /**
     * ключ это название параметра
     */
    private Map<String, String> mapParam;
}
