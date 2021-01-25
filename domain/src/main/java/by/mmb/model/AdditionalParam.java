package by.mmb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Дополнительные параметры для разнах
 * видов данных
 * Возможность динамического расширения
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AdditionalParam {
    private long id;
    /**
     * т.к сущность будет мапиться на несколько таблиц
     * что бы разделить параметры типов:
     * Параметры пользователя - user_id
     * Параметры груза - cargo_id
     * Параметры заявки - request_id
     */
    private long refId;
    /**
     * ключ это название параметра
     */
    private Map<String, String> mapParam;
}