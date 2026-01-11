package com.example.msexchange.controller;

import com.example.msexchange.configuration.SecurityConfig;
import com.example.msexchange.model.request.BalanceUpdateDto;
import com.example.msexchange.model.request.CoinTransactionRequest;
import com.example.msexchange.service.AuthService;
import com.example.msexchange.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;

    private static final String PAYMENT_PATH = "/v1/payments";


    @Test
    public void topUpBalanceTest()throws Exception{
        var request = """
                {
                 "userId": 21,
                 "amount": 800000
                }
                """;


        mockMvc.perform(post("/v1/payments/top-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(paymentService, times(1)).topUpBalance(any(BalanceUpdateDto.class));
        verifyNoMoreInteractions(paymentService);
    }
    @Test
    public void purchaseCoin() throws Exception{
        var coinRequest = new CoinTransactionRequest();
        coinRequest.setCoinName("Bitcoin");
        coinRequest.setUserId(1L);
        coinRequest.setCoinQuantity(BigDecimal.valueOf(1.5));

        var request = """
                {
                 "coinName": "Bitcoin",
                 "userId": 1,
                 "coinQuantity": 1.5
                }
                """;

        mockMvc.perform(post(PAYMENT_PATH + "/purchase-coin")
                        .content(request)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());

        verify(paymentService, times(1)).purchaseCoin(coinRequest);
        verifyNoMoreInteractions(paymentService);
    }
    @Test
    public void sellCoin() throws Exception{
        //given
        var coinRequest = new CoinTransactionRequest();
        coinRequest.setCoinName("Bitcoin");
        coinRequest.setUserId(1L);
        coinRequest.setCoinQuantity(BigDecimal.valueOf(1.5));
        var request = """
                {
                 "coinName": "Bitcoin",
                 "userId": 1,
                 "coinQuantity": 1.5
                }
                """;

        //then
        mockMvc.perform(post(PAYMENT_PATH + "/sell-coin")
                .content(request)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).sellCoin(coinRequest);
        verifyNoMoreInteractions(paymentService);
    }
}
