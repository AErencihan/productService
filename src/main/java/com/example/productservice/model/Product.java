package com.example.productservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String productName;

    @CreatedDate
    private LocalDateTime createTime;

    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> images = new ArrayList<>();

    public List<Image> getImages() {
        return images;
    }
}











