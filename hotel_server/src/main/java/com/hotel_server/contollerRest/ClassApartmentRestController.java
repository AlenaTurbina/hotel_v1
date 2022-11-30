package com.hotel_server.contollerRest;

import com.hotel_dto.dto.ClassApartmentDto;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_server.validator.ClassApartmentValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="ClassApartment", description = "Management of Class apartment - getting lists and creating")
public class ClassApartmentRestController {
    private ClassApartmentService classApartmentService;
    private ClassApartmentValidator classApartmentValidator;
    private ClassApartmentMapper classApartmentMapper;

    @InitBinder(value = "classApartmentDTO")
    void initClassApartmentValidator(WebDataBinder binder) {
        binder.setValidator(classApartmentValidator);
    }

    @Operation(summary = "Getting list of Class apartments")
    @GetMapping("/admin/classApartments")
    List<ClassApartmentDto> getAllClassApartments() {
        return classApartmentMapper.toListClassApartmentDto(classApartmentService.getAllClassApartments());
    }

    @Operation(summary = "Creating a new Class apartment")
    @PostMapping("/admin/classApartments")
    ResponseEntity createClassApartment(@RequestBody @Valid ClassApartmentDto classApartmentDto) {
        return new ResponseEntity<>(classApartmentMapper
                .toClassApartmentDto(classApartmentService.saveClassApartment(classApartmentDto)), HttpStatus.CREATED);
    }

    @Operation(summary = "Getting list of Class apartments, if room with such Class apartment is exist")
    @GetMapping("/uniqueClassApartmentsFromRooms")
    List<ClassApartmentDto> showUniqueClassApartmentsFromRooms() {
        return classApartmentMapper.toListClassApartmentDto(classApartmentService.getListUniqueClassApartmentsFromRooms());
    }




}
