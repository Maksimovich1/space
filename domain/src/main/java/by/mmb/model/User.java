package by.mmb.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class User {

    private String id;
    private LocalDateTime dateReg;
    /**
     * analytic type = 6
     */
    private int status;
    private String login;
    private String password;
    /**
     * analytic type = 4
     */
    private int type;

    private List<AdditionalParam> additionalParam;
}
