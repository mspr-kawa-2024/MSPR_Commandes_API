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
    private String clientsId;
    private String productsId;

    public Command() {
    }

    public Command(String name,
                   LocalDate creationDate,
                   LocalDate updateDate,
                   String clientsId,
                   String productsId) {
        this.name = name;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.clientsId = clientsId;
        this.productsId = productsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientsId;
    }

    public void setClientsId(String clientsId) {
        this.clientsId = clientsId;
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
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }

    public String getProductsId() {
        return productsId;
    }
}
