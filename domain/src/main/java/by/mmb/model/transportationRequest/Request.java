package by.mmb.model.transportationRequest;

import by.mmb.enams.RequestStatus;
import by.mmb.model.AdditionalParam;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Заявка на перевозку
 *
 * @author andrew.maksimovich
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    private long id;
    private long userId;
    private long cargoId;
    private long cityFrom;
    private long cityTo;
    private RequestStatus status;
    private int countKM;
    @Setter(value = AccessLevel.PRIVATE) private LocalDateTime dateCreate;
    private LocalDateTime dateRefresh;
    private AdditionalParam additionalParam;
}
