package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseIntegrationTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.infrastructure.ability.RestApiAbility
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import spock.lang.Unroll

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND

class AccountEuroExchangeControllerWithNbpApiStubbingSpec extends BaseIntegrationTest implements RestApiAbility {
    private static final int WIRE_MOCK_PORT = nextAvailablePort()

    @DynamicPropertySource
    static void overwriteProperties(DynamicPropertyRegistry registry) {
        registry.add("feign.client.config.nbp.url") { "http://localhost:$WIRE_MOCK_PORT" }
    }

    WireMockServer wireMockServer

    @Unroll
    def "test checkAccountBalanceExchangeInfo - NBP API returns #testCaseName and our API returns 404 Not Found"() {
        setup:
            stubNbpApiWithStatus(httpStatus)

        when:
            ResponseEntity<Map> responseEntity = getAccountEuroBalanceExchangeInfo(ACCOUNT_OWNER_01.ownerUniqueId(), ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01.ibanNumber())

        then:
            AccountEuroBalanceExchangeInfoResponseAssertion.assertThat(responseEntity) {
                hasStatusCode(NOT_FOUND)
                hasErrorMessage("Euro exchange rate not found.")
            }

        where:
            testCaseName           | httpStatus
            BAD_REQUEST.toString() | BAD_REQUEST
            NOT_FOUND.toString()   | NOT_FOUND
    }

    def setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(WIRE_MOCK_PORT))
        wireMockServer.start()
        configureFor(wireMockServer.port())
    }

    def cleanup() {
        wireMockServer.stop()
    }

    private static void stubNbpApiWithStatus(HttpStatus status) {
        stubFor(get(urlEqualTo("/api/exchangerates/rates/a/eur"))
                .willReturn(aResponse().withStatus(status.value()))
        )
    }

    private synchronized static int nextAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort()
        }
    }
}
