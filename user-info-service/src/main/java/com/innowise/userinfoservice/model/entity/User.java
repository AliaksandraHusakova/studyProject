package com.innowise.userinfoservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.repackaged.com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    @CsvBindByName(column = "id")
    private Integer id;

    @Column(name = "username")
    @CsvBindByName(column = "username")
    private String username;

    @Column(name = "password")
    @CsvBindByName(column = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @CsvBindByName(column = "role")
    private Role role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Employee employee;

    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
