package com.example.foodcourtmicroservice.adapters.driving.http.controller;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.example.foodcourtmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;


    @Operation(summary = "Add un new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish registred!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("")
  //  @PreAuthorize("hasAuthority('OWNER_ROLE')")
    public ResponseEntity<Map<String,String>> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto){
        dishHandler.saveDish(dishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.CREATED_DISH)
        );
    }

    @Operation(summary = "Update dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish updated!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Bad request of update, please verify data",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping ("/")
    //@PreAuthorize("hasAuthority('OWNER_ROLE')")
    public ResponseEntity<Map<String,String>> updateDish(@Valid @RequestBody UpdateDishRequestDto updateDishRequestDto){
        dishHandler.updateDish(updateDishRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.MODIFIED_DISH)
        );
    }

    @Operation(summary = "Enable or disable dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish enable/disable",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishRequestDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PutMapping("/{id}/activate/{enableDisable}")
   // @PreAuthorize("hasAuthority('OWNER_ROLE')")
    public ResponseEntity<DishRequestDto> updateEnableDisableDish(@PathVariable(value = "id")Long dishId, @PathVariable(value = "enableDisable")Long enableDisable){
        dishHandler.updateEnableDisableDish(dishId, enableDisable);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(summary = "Get all dishes by restaurant or category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dishes filtered by restaurant or category",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
  //  @PreAuthorize("hasAuthority('CLIENT_ROLE')")
    @GetMapping("/{idRestaurant}/page/{page}/size/{size}")
    public ResponseEntity<List<DishResponseDto>> getAllDishesByRestaurant(
            @PathVariable(value = "idRestaurant") Long idRestaurante,
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            @RequestParam(value = "category", required = false) Long category
    ) {
        List<DishResponseDto> dishes;
        if (category != null) {
            dishes = dishHandler.findAllByRestaurantIdAndCategory(idRestaurante, category, page, size);
        } else {
            dishes = dishHandler.findAllByRestaurantId(idRestaurante, page, size);
        }
        return ResponseEntity.ok(dishes);
    }


}
