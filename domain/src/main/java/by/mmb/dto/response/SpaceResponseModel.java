package by.mmb.dto.response;

import by.mmb.dto.response.enums.Empty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Общая модель для ответов
 *
 * @author andrew.maksimovich
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpaceResponseModel<T> {

    public final static Empty EMPTY_BODY = Empty.INSTANCE;

    private boolean isSuccess;

    private T responseBody;

    private ErrorBody errorBody;

    public static <E> SpaceResponseModel<E> successOf(E body) {
        SpaceResponseModel<E> eSpaceResponseModel = new SpaceResponseModel<>();
        eSpaceResponseModel.isSuccess = true;
        eSpaceResponseModel.errorBody = null;
        eSpaceResponseModel.responseBody = body;
        return eSpaceResponseModel;
    }

    public static SpaceResponseModel<Empty> successOfEmptyBody() {
        SpaceResponseModel<Empty> eSpaceResponseModel = new SpaceResponseModel<>();
        eSpaceResponseModel.isSuccess = true;
        eSpaceResponseModel.errorBody = null;
        eSpaceResponseModel.responseBody = EMPTY_BODY;
        return eSpaceResponseModel;
    }

    public static SpaceResponseModel<Empty> failOf(ErrorBody errorBody) {
        SpaceResponseModel<Empty> eSpaceResponseModel = new SpaceResponseModel<>();
        eSpaceResponseModel.isSuccess = false;
        eSpaceResponseModel.errorBody = errorBody;
        eSpaceResponseModel.responseBody = EMPTY_BODY;
        return eSpaceResponseModel;
    }
}
