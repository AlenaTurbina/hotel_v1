package com.hotel_domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_kind")
    private RoomKind roomKind;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<OrderBooking> orderBookings;

}
