package com.commandApi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

//for hibernate
@Entity
//for database
@Table(name = "command")
public class Order {

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
    private LocalDate creationDate;
    private LocalDate updateDate;
    private String clientsId;
    private String productsId;

    public Order() {
    }

    public Order(
                 LocalDate creationDate,
                 LocalDate updateDate,
                 String clientsId,
                 String productsId) {
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.clientsId = clientsId;
        this.productsId = productsId;
    }

    public Order(Long id,
                 LocalDate creationDate,
                 LocalDate updateDate,
                 String clientsId,
                 String productsId) {
        this.id = id;
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
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }

    public String getProductsId() {
        return productsId;
    }

    public String getClientsId() {
        return clientsId;
    }

    public void setProductsId(String productsId1) {
        this.productsId = productsId1;
    }
}
