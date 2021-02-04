package by.mmb.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author andrew.maksimovich
 */
@Getter
@AllArgsConstructor
public class Pair<T, D> {
    private T key;
    private D value;
}