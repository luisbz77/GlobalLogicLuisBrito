package com.globalLogic.structure.mapper;

import com.globalLogic.domain.model.user.User;
import com.globalLogic.structure.entity.user.UserEntity;


public interface UserMapper {

    UserEntity toEntity(User user);

    User toModel(UserEntity userEntity);

}
