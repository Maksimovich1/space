package by.mmb.controllers.transportation;

import by.mmb.dto.request.TransportationRequestDto;
import by.mmb.dto.response.ErrorBody;
import by.mmb.dto.response.SpaceResponseModel;
import by.mmb.dto.response.enums.Empty;
import by.mmb.model.AdditionalParam;
import by.mmb.model.transportationRequest.Cargo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

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
    private String baseUrlForDelete;
    private URI uriCreate;
    private URI uriDelete;
    private HttpHeaders headers;

    ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @BeforeAll
    void init() {
        baseUrl = "http://localhost:" + randomServerPort + "/api/requests/request/";
        baseUrlForDelete = "http://localhost:" + randomServerPort + "/api/requests/delete/";
        uriCreate = new URI(baseUrl);
        uriDelete = new URI(baseUrlForDelete);

        headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Test
    void create_trans_request_with_illegalArgumentException_cargo_exception() {

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        TransportationRequestDto transportationRequestDto = createTransportationRequestDto(new Cargo(), 3);


        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<SpaceResponseModel> response = testRestTemplate.postForEntity(uri, request, SpaceResponseModel.class);

        Assert.assertTrue(response.getStatusCode().is4xxClientError());
        Assert.assertEquals(400, response.getStatusCodeValue());

        SpaceResponseModel body = response.getBody();
        Assert.assertNotNull(body);
        Assert.assertFalse(body.isSuccess());

        ErrorBody errorBody = body.getErrorBody();
        Assert.assertNotNull(errorBody);

        Assert.assertEquals(-9000, errorBody.getCode());
        Assert.assertEquals("Проблемы с валидацией данных", errorBody.getMessage());
        Assert.assertEquals("Не валидные параметры груза!", errorBody.getMessageWithDetails());
        Assert.assertTrue(errorBody.getMessageWithDetails().length() < 40);
        Assert.assertEquals(messgeForClient, errorBody.getMessageForClient());

    }

    @SneakyThrows
    @Test
    void create_trans_request_with_unknown_city_exception() {

        TransportationRequestDto transportationRequestDto = createTransportationRequestDto(new Cargo(), 5);

        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<SpaceResponseModel> response = testRestTemplate.postForEntity(uriCreate, request, SpaceResponseModel.class);

        Assert.assertEquals(400, response.getStatusCodeValue());

        SpaceResponseModel body = response.getBody();
        Assert.assertNotNull(body);
        Assert.assertFalse(body.isSuccess());

        ErrorBody errorBody = body.getErrorBody();
        Assert.assertNotNull(errorBody);

        Assert.assertEquals(-9000, errorBody.getCode());
        Assert.assertNotNull(errorBody.getTime());
        Assert.assertEquals("Переданные города не существуют или недоступны!", errorBody.getMessageWithDetails());
        Assert.assertEquals(messgeForClient, errorBody.getMessageForClient());
    }

    @SneakyThrows
    @Test
    void create_trans_request_without_exception() {

        Cargo cargo = createCargo("Коробки");

        TransportationRequestDto transportationRequestDto = createTransportationRequestDto(cargo, 3);

        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(transportationRequestDto, headers);

        ResponseEntity<SpaceResponseModel> response = testRestTemplate.postForEntity(uriCreate, request, SpaceResponseModel.class);


        Assert.assertNotNull(response);
        SpaceResponseModel body = response.getBody();
        Assert.assertNotNull(body);
        Long idReq = objectMapper.convertValue(body.getResponseBody(), new TypeReference<>() {
        });
        Assert.assertTrue(idReq > 0);
    }


    @Test
    void delete_trans_request_without_any_exceptions() {
        Cargo cargo = createCargo("Болты");
        TransportationRequestDto requestDto = createTransportationRequestDto(cargo, 3);

        HttpEntity<TransportationRequestDto> request = new HttpEntity<>(requestDto, headers);

        ResponseEntity<SpaceResponseModel> response = testRestTemplate.postForEntity(uriCreate, request, SpaceResponseModel.class);

        Assert.assertNotNull(response);
        SpaceResponseModel body = response.getBody();
        Assert.assertNotNull(body);

        Assert.assertTrue(body.isSuccess());

        Long idReq = objectMapper.convertValue(body.getResponseBody(), new TypeReference<>() {
        });

        ResponseEntity<SpaceResponseModel> deleteResponse =
                testRestTemplate.exchange(
                        uriDelete + Long.toString(idReq),
                        HttpMethod.DELETE,
                        null,
                        SpaceResponseModel.class);

        Assert.assertNotNull(deleteResponse);
        Assert.assertNotNull(deleteResponse.getBody());
        Assert.assertEquals(200, deleteResponse.getStatusCodeValue());

        Assert.assertTrue(deleteResponse.getBody().isSuccess());
    }

    @Test
    void delete_trans_request_by_not_existing_key_exception() {
        ResponseEntity<SpaceResponseModel> deleteResponse =
                testRestTemplate.exchange(
                        uriDelete + Integer.toString(0),
                        HttpMethod.DELETE,
                        null,
                        SpaceResponseModel.class);

        Assert.assertNotNull(deleteResponse);
        SpaceResponseModel body = deleteResponse.getBody();
        Assert.assertNotNull(body);
        Assert.assertEquals(400, deleteResponse.getStatusCodeValue());
        Assert.assertNotNull(body.getResponseBody());
        Assert.assertEquals(-9000, body.getErrorBody().getCode());

        Assert.assertFalse(deleteResponse.getBody().isSuccess());
    }

    @Test
    void get_trans_request_by_not_existing_key_exception() {

        ResponseEntity<SpaceResponseModel> response = testRestTemplate.getForEntity(
                baseUrl + 0,
                SpaceResponseModel.class);

        SpaceResponseModel body = response.getBody();
        Assert.assertNotNull(body);


        ErrorBody errorBody = body.getErrorBody();
        Assert.assertEquals(-9000, errorBody.getCode());
        Assert.assertNotNull(errorBody);

        Empty responseBody = objectMapper.convertValue(body.getResponseBody(), new TypeReference<>() {
        });
        Assert.assertNotNull(responseBody);

        boolean success = body.isSuccess();
        Assert.assertFalse(success);
    }

    private TransportationRequestDto createTransportationRequestDto(Cargo cargo, int cityTo) {
        return new TransportationRequestDto(1, cityTo, 23, cargo, new AdditionalParam());
    }

    private Cargo createCargo(String name) {
        Cargo cargo = new Cargo();
        cargo.setName(name);
        cargo.setWidth(2);
        cargo.setWeight(5);
        cargo.setLength(6);
        cargo.setHeight(8);
        return cargo;
    }
}