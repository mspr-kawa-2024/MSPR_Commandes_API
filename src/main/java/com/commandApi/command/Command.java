package com.commandApi.command;

import jakarta.persistence.*;

import java.time.LocalDate;

//for hibernate
@Entity
//for database
@Table
public class Command {

    @Id
    @SequenceGenerator(
            name = "command_sequence",
            sequenceName = "command_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "command_sequence"
    )
    private Long id;
    private String name;
    private LocalDate creationDate;
    private LocalDate updateDate;

    public Command() {
    }

    public Command(Long id,
                   String name,
                   LocalDate creationDate,
                   LocalDate updateDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Command(String name,
                   LocalDate creationDate,
                   LocalDate updateDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", fristName='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}

