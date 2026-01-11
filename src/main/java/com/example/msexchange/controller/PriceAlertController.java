package com.example.msexchange.controller;

import com.example.msexchange.model.request.PriceAlertRequest;
import com.example.msexchange.service.PriceAlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/alerts")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PriceAlertController {
    PriceAlertService priceAlertService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createPriceAlert(@Valid @RequestBody PriceAlertRequest priceAlertRequest){
        priceAlertService.createPriceAlert(priceAlertRequest);
    }
}
