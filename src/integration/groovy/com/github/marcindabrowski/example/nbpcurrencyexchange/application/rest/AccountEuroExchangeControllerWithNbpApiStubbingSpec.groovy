package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseIntegrationTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.infrastructure.ability.RestApiAbility
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import spock.lang.Unroll

import java.time.LocalDate

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_03
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_04
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.DEFAULT_ZONE_ID
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.NOW
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@SuppressWarnings("Duplicates")
class AccountEuroExchangeControllerWithNbpApiStubbingSpec extends BaseIntegrationTest implements RestApiAbility {
    private static final int WIRE_MOCK_PORT = nextAvailablePort()

    @DynamicPropertySource
    static void overwriteProperties(DynamicPropertyRegistry registry) {
        registry.add("feign.client.config.nbp.url") { "http://localhost:$WIRE_MOCK_PORT" }
    }

    WireMockServer wireMockServer

    @Unroll
    def "test getAccountEuroBalanceExchangeInfo REST API endpoint - #testCaseName"() {
        setup:
            stubNbpApiWithStatusOK()
        and:
            LocalDate today = LocalDate.ofInstant(NOW, DEFAULT_ZONE_ID)

        when: "call getAccountEuroBalanceExchangeInfo REST API endpoint"
            ResponseEntity<Map> responseEntity = getAccountEuroBalanceExchangeInfo(accountOwner.ownerUniqueId(), accountNumber.ibanNumber())

        then: "the account balance in Euro is returned"
            AccountEuroBalanceExchangeInfoResponseAssertion.assertThat(responseEntity) {
                hasStatusCode(OK)
                hasBalanceInEuro(balanceAfterExchange)
                hasExchangeRateDate(today)
            }

        where:
            testCaseName                       | accountOwner     | accountNumber                      | balanceAfterExchange
            "account owner 01 with account 01" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(466.14)
            "account owner 01 with account 02" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02 | BigDecimal.valueOf(573.35)
            "account owner 02 with account 01" | ACCOUNT_OWNER_02 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 03 with account 01" | ACCOUNT_OWNER_03 | ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 04 with account 01" | ACCOUNT_OWNER_04 | ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(18.65)
    }

    @Unroll
    def "test getAccountEuroBalanceExchangeInfo REST API endpoint - unknown account - #testCaseName"() {
        setup:
            stubNbpApiWithStatusOK()

        when:
            ResponseEntity<Map> responseEntity = getAccountEuroBalanceExchangeInfo(accountOwner.ownerUniqueId(), accountNumber.ibanNumber())

        then:
            AccountEuroBalanceExchangeInfoResponseAssertion.assertThat(responseEntity) {
                hasStatusCode(NOT_FOUND)
                hasErrorMessage(String.format("Account owner: %s, iban: %s not found.", accountOwner.ownerUniqueId(), accountNumber.ibanNumber()))
            }

        where:
            testCaseName                                                  | accountOwner     | accountNumber
            "account owner 01 with not his account number"                | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "account owner 02 with non existing account number"           | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
            "non existing account owner with not his account number"      | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "non existing account owner with not existing account number" | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01
    }

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

    private static void stubNbpApiWithStatusOK() {
        stubFor(getTableAEuroRate()
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("""
                            {
                              "table": "A",
                              "currency": "euro",
                              "code": "EUR",
                              "rates": [
                                {
                                  "no": "222/A/NBP/2021",
                                  "effectiveDate": "2021-11-17",
                                  "mid": 4.6614
                                }
                              ]
                            }""")
                )
        )
    }

    private static void stubNbpApiWithStatus(HttpStatus status) {
        stubFor(getTableAEuroRate()
                .willReturn(aResponse().withStatus(status.value()))
        )
    }

    private static MappingBuilder getTableAEuroRate() {
        return get(urlEqualTo("/api/exchangerates/rates/a/eur"))
    }

    private synchronized static int nextAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort()
        }
    }
}
