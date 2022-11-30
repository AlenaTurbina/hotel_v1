package com.hotel_server.contollerRest;

import com.hotel_dto.dto.ClassApartmentDto;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_server.validator.ClassApartmentUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="ClassApartment/Update", description = "Management of Class apartment - update")
public class ClassApartmentUpdateRestController {
    private ClassApartmentService classApartmentService;
    private ClassApartmentUpdateValidator classApartmentUpdateValidator;
    private ClassApartmentMapper classApartmentMapper;

    @InitBinder(value = "classApartmentDTO")
    void initClassApartmentUpdateValidator(WebDataBinder binder) {
        binder.setValidator(classApartmentUpdateValidator);
    }

    @Operation(summary = "Getting Class apartment by id")
    @GetMapping(value = "/admin/classApartments/{id}")
    ClassApartmentDto getClassApartment(@PathVariable String id) {
        return classApartmentMapper.toClassApartmentDto(classApartmentService.getClassApartmentById(UUID.fromString(id)));
    }

    @Operation(summary = "Updating Class apartment")
    @PutMapping("/admin/classApartments")
    ResponseEntity updateClassApartment(@RequestBody @Valid ClassApartmentDto classApartmentDTO) {
        var classApartment = classApartmentMapper.toClassApartment(classApartmentDTO);
        return new ResponseEntity<>(classApartmentMapper
                .toClassApartmentDto(classApartmentService.updateClassApartment(classApartment)), HttpStatus.CREATED);
    }



}
