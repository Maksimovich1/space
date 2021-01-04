package by.mmb.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private String id;
    private String nickName;
    private String name;
    private String email;

    private List<AdditionalParam> additionalParam;
}
