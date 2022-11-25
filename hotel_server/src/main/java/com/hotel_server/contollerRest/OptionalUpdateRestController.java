package com.hotel_server.contollerRest;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;
import com.hotel_dto.mapper.OptionalMapper;
import com.hotel_server.service.OptionalService;
import com.hotel_server.validator.OptionalUpdateValidator;
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
@Tag(name="Optional/Update", description = "Management of Optional - update")
public class OptionalUpdateRestController {
    private OptionalService optionalService;
    private OptionalMapper optionalMapper;
    private OptionalUpdateValidator optionalUpdateValidator;

    @InitBinder(value = "optionalDTO")
    void initOptionalUpdateValidator(WebDataBinder binder) {
        binder.setValidator(optionalUpdateValidator);
    }

    @Operation(summary = "Getting Optional by id")
    @GetMapping(value = "/admin/optionals/{id}")
    OptionalDTO getOptional(@PathVariable UUID id) {
        return optionalMapper.toOptionalDTO(optionalService.getOptionalById(id));
    }

    @Operation(summary = "Updating Optional")
    @PutMapping("/admin/optionals")
    ResponseEntity updateOptional(@RequestBody @Valid OptionalDTO optionalDTO) {
        Optional optional = optionalMapper.toOptional(optionalDTO);
        return new ResponseEntity<>(optionalMapper.toOptionalDTO(optionalService.updateOptional(optional)), HttpStatus.CREATED);
    }

}
