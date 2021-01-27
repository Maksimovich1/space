package by.mmb.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class User {

    private long idUser;
    private String idKeycloak;
    private LocalDateTime dateReg;
    /**
     * analytic type = 500
     */
    private int status;
    private String login;
    private String password;
    /**
     * analytic type = 400
     */
    private int type;

    private List<AdditionalParam> additionalParam;
}
