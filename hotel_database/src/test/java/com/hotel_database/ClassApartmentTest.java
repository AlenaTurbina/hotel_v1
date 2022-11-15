package com.hotel_database;

import com.hotel_database.model.repository.ClassApartmentRepository;
import com.hotel_domain.model.entity.ClassApartment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ClassApartmentTest {
    @Autowired
    private ClassApartmentRepository classApartmentRepository;

    @Test
    public void shouldSaveClassApartment(){
        ClassApartment classApartment = new ClassApartment();
        classApartment.setName("testA");
        classApartment.setPlacePrice(20.3);
        ClassApartment savedClassApartment = classApartmentRepository.save(classApartment);
        System.out.println(savedClassApartment.getId());
        assertThat(savedClassApartment).usingRecursiveComparison().ignoringFields("id").isEqualTo(classApartment);
    }


}
