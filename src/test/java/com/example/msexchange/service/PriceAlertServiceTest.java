package com.example.msexchange.service;

import com.example.msexchange.dao.entity.PriceAlertEntity;
import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.PriceAlertRepository;
import com.example.msexchange.mapper.PriceAlertMapper;
import com.example.msexchange.model.request.PriceAlertRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceAlertServiceTest {

    @InjectMocks
    private PriceAlertService priceAlertService;
    @Mock
    private PriceAlertRepository priceAlertRepository;
    @Mock
    private PriceAlertMapper priceAlertMapper;
    @Mock
    private UserService userService;

    @Test
    public void createPriceAlertTest(){
        var priceAlertRequest = new PriceAlertRequest();
        priceAlertRequest.setUserId(1L);
        var user = new UserEntity();
        user.setId(1L);
        var priceAlert = new PriceAlertEntity();


        when(userService.getUserEntity(user.getId())).thenReturn(user);
        when(priceAlertMapper.toPriceAlertEntity(priceAlertRequest, user)).thenReturn(priceAlert);
        priceAlertService.createPriceAlert(priceAlertRequest);
        verify(priceAlertRepository, times(1)).save(priceAlert);
    }
    @Test
    public void getAllUnProcessedAlertsTest(){
        var alertEntity = new PriceAlertEntity();
        List<PriceAlertEntity> list = new ArrayList<>();
        list.add(alertEntity);
        when(priceAlertRepository.findByExecutedIsFalse()).thenReturn(list);
        priceAlertService.getAllUnProcessedAlerts();
        verify(priceAlertRepository, times(1)).findByExecutedIsFalse();
    }

}
