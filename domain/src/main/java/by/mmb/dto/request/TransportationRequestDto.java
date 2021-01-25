package by.mmb.dto.request;

import by.mmb.model.AdditionalParam;
import by.mmb.model.transportationRequest.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Модель для создания заявки
 *
 * @author andrew.maksimovich
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransportationRequestDto {

    private int cityFrom;
    private int cityTo;
    private int countKM;
    private Cargo cargo;
    private List<AdditionalParam> additionalParams;
}
