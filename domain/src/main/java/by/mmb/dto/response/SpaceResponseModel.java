package by.mmb.dto.response;

import by.mmb.dto.response.enums.ConstResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Общая модель для ответов
 * В случае когда произошла ошибка в теле будет EMPTY
 * и статус ответа будет false.
 * Если все ОК то будет статус true и тело
 * будет в зависимости от нужды ответа.
 *
 * @author andrew.maksimovich
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpaceResponseModel {

    public final static ConstResponse CONST_RESPONSE_BODY = ConstResponse.EMPTY;

    private boolean isSuccess;

    private Object responseBody;

    private ErrorBody errorBody;

    public static SpaceResponseModel successOf(Object body) {
        SpaceResponseModel eSpaceResponseModel = new SpaceResponseModel();
        eSpaceResponseModel.isSuccess = true;
        eSpaceResponseModel.errorBody = null;
        eSpaceResponseModel.responseBody = body;
        return eSpaceResponseModel;
    }

    public static SpaceResponseModel successOfEmptyBody() {
        SpaceResponseModel eSpaceResponseModel = new SpaceResponseModel();
        eSpaceResponseModel.isSuccess = true;
        eSpaceResponseModel.errorBody = null;
        eSpaceResponseModel.responseBody = CONST_RESPONSE_BODY;
        return eSpaceResponseModel;
    }

    public static SpaceResponseModel failOf(ErrorBody errorBody) {
        SpaceResponseModel eSpaceResponseModel = new SpaceResponseModel();
        eSpaceResponseModel.isSuccess = false;
        eSpaceResponseModel.errorBody = errorBody;
        eSpaceResponseModel.responseBody = CONST_RESPONSE_BODY;
        return eSpaceResponseModel;
    }
}
