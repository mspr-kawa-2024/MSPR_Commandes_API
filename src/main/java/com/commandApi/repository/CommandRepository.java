package com.commandApi.repository;

import com.commandApi.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    // select * from client where email = ?
    //@Query("SELECT s FROM s WHERE s.email = ?1")
    Optional<Command> findByName(String name);


}


