package com.rest.vehicle.controller;

import com.rest.vehicle.dto.VehicleDto;
import com.rest.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name="Vehicle", description="CRUD-a controller that works with vehicles")
public class VehicleCrudController {
    private final VehicleService vehicleService;

    public VehicleCrudController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful receipt list of the vehicles",
                    content = {
                            @Content(schema = @Schema(implementation = Page.class))
                    }
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )}
    )
    @GetMapping(params = {"page", "size", "sort"}, produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<VehicleDto>> findAllVehicle(@RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size,
                                                    @RequestParam(value = "sort") String sort) {

        return ResponseEntity.ok(vehicleService.findAllVehicle(page, size, sort));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful receipt of the vehicle",
                    content = {
                            @Content(schema = @Schema(implementation = VehicleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "vehicle not found",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )}
    )
    @GetMapping(value = "/{id}", produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable("id") String id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "successful created the vehicle",
                    content = {
                            @Content(schema = @Schema(implementation = VehicleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "409",
                    description = "Vehicle already exists",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )}
    )
    @PostMapping(produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VehicleDto> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        VehicleDto vehicleDtoResponse = vehicleService.createVehicle(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDtoResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(schema = @Schema(implementation = VehicleDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Vehicle not found",
                    content = @Content
            ),
            @ApiResponse(responseCode = "409",
                    description = "Vehicle already exists",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )}
    )
    @PutMapping(value = "/{id}", produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<VehicleDto> updateVehicleById(@PathVariable("id") String id, @Valid @RequestBody VehicleDto vehicleDto) {
        VehicleDto vehicleDtoResponse = vehicleService.updateVehicle(id, vehicleDto);
        return ResponseEntity.ok(vehicleDtoResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Vehicle not found",
                    content = @Content
            )}
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable("id") String id) {
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<Void>( HttpStatus.OK );
    }

}
