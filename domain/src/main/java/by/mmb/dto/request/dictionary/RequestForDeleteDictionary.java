package by.mmb.dto.request.dictionary;

/**
 * Объект для удаления справочника
 *
 * @author andrew.maksimovich
 */
public class RequestForDeleteDictionary {

    /**
     * ид справочника
     */
    public int idDictionary;
    /**
     * будет ли удаление каскадным, если нет то значений в справочнике быть не должно
     */
    public boolean isCascade;
}
