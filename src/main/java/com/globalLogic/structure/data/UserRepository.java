package com.globalLogic.structure.data;


import com.globalLogic.structure.entity.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity u SET u.isActive = :isActive, u.modified = :modified, u.name = :name, u.password = :password, u.phones = :phones  WHERE u.email = :email")
    int updateUser(@Param("email") String email, @Param("isActive") boolean isActive, @Param("modified") Timestamp modified,
                          @Param("name") String name, @Param("password") String password, @Param("phones") String phones);

}
