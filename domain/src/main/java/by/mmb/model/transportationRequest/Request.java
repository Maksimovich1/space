package by.mmb.model.transportationRequest;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Заявка на перевозку
 *
 * @author andrew.maksimovich
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private long id;
    private long userId;
    private long cargoId;
    private int cityFrom;
    private int cityTo;
    private int countKM;
    @Setter(value = AccessLevel.PRIVATE) private LocalDateTime dateCreate;
    private LocalDateTime dateRefresh;
//    private List<AdditionalParam> additionalParams;
}
