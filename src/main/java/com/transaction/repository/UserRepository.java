package com.transaction.repository;

import com.transaction.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    Optional<UserEntity> findByUserId(String userId);

    @Query("SELECT u FROM UserEntity u WHERE "+
    "(:userId IS NULL OR u.userId = :userId) AND"+
    "(:name IS NULL OR u.name = :name) AND"+
    "(:email IS NULL OR u.email = :email) AND"+
    "(:registeredAt IS NULL OR u.registeredAt = :registeredAt)"
    )
    List<UserEntity> search(
            @Param("userId") String userId,
            @Param("name") String name,
            @Param("email") String email,
            @Param("registeredAt") Timestamp registeredAt
    );
}
