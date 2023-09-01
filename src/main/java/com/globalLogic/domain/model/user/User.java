package com.globalLogic.domain.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.globalLogic.domain.model.phone.Phone;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private String name;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private List<Phone> phones;
    private Timestamp created;
    private Timestamp modified;
    @JsonProperty(value = "last_login")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp lastLogin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    @JsonProperty(value = "isactive")
    private boolean isActive;

}
