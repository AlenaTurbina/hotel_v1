package com.hotel_domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private String name;
    @Column(name = "place_price")
    private Double placePrice;

}
