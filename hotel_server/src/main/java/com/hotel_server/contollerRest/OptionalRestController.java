package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.OptionalMapper;
import com.hotel_server.service.OptionalService;
import com.hotel_server.validator.OptionalValidator;
import com.hotel_dto.dto.OptionalDTO;
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
@Tag(name="Optional", description = "Management of Optionals - getting list and creating")
public class OptionalRestController {
    private OptionalService optionalService;
    private OptionalMapper optionalMapper;
    private OptionalValidator optionalValidator;

    @InitBinder(value = "optionalDTO")
    void initOptionalValidator(WebDataBinder binder) {
        binder.setValidator(optionalValidator);
    }

    @Operation(summary = "Getting list of Optionals")
    @GetMapping("/admin/optionals")
    List<OptionalDTO> getAllOptionals() {
        return optionalMapper.toListOptionalDTO(optionalService.getAllOptionals());
    }

    @Operation(summary = "Creating  new optional")
    @PostMapping("/admin/optionals")
    ResponseEntity createOptional(@RequestBody @Valid OptionalDTO optionalDTO) {
        return new ResponseEntity<>(optionalMapper.toOptionalDTO(optionalService.saveOptional(optionalDTO)), HttpStatus.CREATED);
    }

}
