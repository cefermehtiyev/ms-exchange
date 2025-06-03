package com.example.msexchange.service;

import com.example.msexchange.dao.entity.PriceAlertEntity;
import com.example.msexchange.dao.repository.PriceAlertRepository;
import com.example.msexchange.mapper.PriceAlertMapper;
import com.example.msexchange.model.request.PriceAlertRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PriceAlertService {
    PriceAlertRepository priceAlertRepository;
    PriceAlertMapper priceAlertMapper;
    UserService userService;

    public void createPriceAlert(PriceAlertRequest priceAlertRequest){
        var user = userService.getUserEntity(priceAlertRequest.getUserId());
        var priceAlert = priceAlertMapper.toPriceAlertEntity(priceAlertRequest, user);
        priceAlertRepository.save(priceAlert);
    }

    public List<PriceAlertEntity> getAllUnProcessedAlerts(){
        return priceAlertRepository.findByExecutedIsFalse();
    }
}
