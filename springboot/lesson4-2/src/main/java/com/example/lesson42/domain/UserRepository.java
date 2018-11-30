package com.example.lesson42.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByName(String name);

    User findUserByNameAndAge(String name,Integer age);

    @Query(value = "select * from User u where u.name=?1",nativeQuery = true)
    User findUser(@Param("name") String name);
}
