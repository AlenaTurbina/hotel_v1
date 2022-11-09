package com.hotel_domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Class_apartment")
public class ClassApartment {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_generator")
//    @SequenceGenerator(name="class_generator", sequenceName = "class_apartment_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(name = "place_price")
    private Double placePrice;

}
