package by.mmb.controllers.transportation;

import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.dto.response.ErrorBody;
import by.mmb.model.transportationRequest.Cargo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransportationRequestsControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomServerPort;

    private String messgeForClient = "Некоректные параметры. Проверьте пожалуйста вводимые данные";

    private String baseUrl;
    private URI uri;
    private HttpHeaders headers;

    @SneakyThrows
    @BeforeAll
    void init() {
        baseUrl = "http://localhost:" + randomServerPort + "/api/requests/request/";
        uri = new URI(baseUrl);

        headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
    }

    @SneakyThrows
    @Test
    void create_trans_request_with_illegalArgumentException_cargo_exception() {

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        TransportationRequestDto transportationRequestDto = new TransportationRequestDto(1, 3, 23, new Cargo(), new ArrayList<>());


        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<ErrorBody> response = testRestTemplate.postForEntity(uri, request, ErrorBody.class);

        Assert.assertTrue(response.getStatusCode().is4xxClientError());
        Assert.assertEquals(400, response.getStatusCodeValue());

        ErrorBody body = response.getBody();
        Assert.assertNotNull(body);

        Assert.assertEquals(-9000, body.getCode());
        Assert.assertEquals("Проблемы с валидацией данных", body.getMessage());
        Assert.assertEquals("Не валидные параметры груза!", body.getMessageWithDetails());
        Assert.assertTrue(body.getMessageWithDetails().length() < 40);
        Assert.assertEquals(messgeForClient, body.getMessageForClient());

    }

    @SneakyThrows
    @Test
    void create_trans_request_with_unknown_city_exception() {

//        given(transportationRequestService.createNewRequest(any(TransportationRequestDto.class)))
//                .willReturn(true);

        TransportationRequestDto transportationRequestDto = new TransportationRequestDto(1, 5, 23, new Cargo(), new ArrayList<>());

        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<ErrorBody> response = testRestTemplate.postForEntity(uri, request, ErrorBody.class);

        Assert.assertEquals(400, response.getStatusCodeValue());

        ErrorBody body = response.getBody();
        Assert.assertNotNull(body);

        Assert.assertEquals(-9000, body.getCode());
        Assert.assertNotNull(body.getTime());
        Assert.assertEquals("Переданные города не существуют или недоступны!", body.getMessageWithDetails());
        Assert.assertEquals(messgeForClient, body.getMessageForClient());
    }

    @SneakyThrows
    @Test
    void create_trans_request_without_exception() {

        Cargo cargo = new Cargo();
        cargo.setName("Коробки");
        cargo.setWidth(2);
        cargo.setWeight(5);
        cargo.setLength(6);
        cargo.setHeight(8);

        TransportationRequestDto transportationRequestDto = new TransportationRequestDto(1, 3, 23, cargo, new ArrayList<>());

        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<Long> response = testRestTemplate.postForEntity(uri, request, Long.class);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody() > 0);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
            //        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            //        ObjectMapper objectMapper = new ObjectMapper();
            //        objectMapper.registerModule(new JavaTimeModule());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}