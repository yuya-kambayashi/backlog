package com.bluejeanssystems.backlog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Getter
@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

}
