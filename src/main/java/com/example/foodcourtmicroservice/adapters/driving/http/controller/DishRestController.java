package com.example.foodcourtmicroservice.adapters.driving.http.controller;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.example.foodcourtmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;


    @Operation(summary = "Add un new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish registrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("")
    public ResponseEntity<Map<String,String>> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto){
        dishHandler.saveDish(dishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.CREATED_DISH)
        );
    }

    @Operation(summary = "Update dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish modificado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de modificacion, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping ("/Update-dish")
    public ResponseEntity<Map<String,String>> updateDish(@Valid @RequestBody UpdateDishRequestDto updateDishRequestDto){
        dishHandler.updateDish(updateDishRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.MODIFIED_DISH)
        );
    }
}
