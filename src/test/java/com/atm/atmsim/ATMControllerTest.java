package com.atm.atmsim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ATMControllerTest extends AbstractTestNGSpringContextTests {

    private static int ACCOUNTS_MAX = 10;

    @Autowired
    private TestRestTemplate restTemplate;
    private ArrayList<Account> accounts = new ArrayList<>();

    @Test
    public void testCreateAccounts() {

        for (int i = 0; i < ACCOUNTS_MAX; i++) {

            Account newAccount = new Account();

            newAccount.setBalance(NumbersUtil.newRandomBigDecimal(new Random(), 2));

            newAccount.setClosed(NumbersUtil.newRandomBoolean());

            ResponseEntity<Account> entity = this.restTemplate.exchange(

                    "/atm",
                    HttpMethod.POST,
                    new HttpEntity<>(newAccount),
                    new ParameterizedTypeReference<Account>() {

                    });

            accounts.add(entity.getBody());

            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(entity.getBody().getBalance()).isEqualTo(newAccount.getBalance());
            assertThat(entity.getBody().isClosed()).isEqualTo(newAccount.isClosed());

        }

    }


    @Test
    public void testDeleteAccount() {

//        this.restTemplate.delete("/atm/" + accountFixture.getAccountNumber());

//        ResponseEntity<String> entity = this.restTemplate.getForEntity("/atm/" + accountFixture.getAccountNumber(), String.class);

//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testDepositFunds() {

        for (int i = 0; i < accounts.size(); i++) {

            ResponseEntity<Account> entity = this.restTemplate.postForEntity(
                    "/atm/" + accounts.get(i).getAccountNumber() + "/deposit/5.00",
                    HttpMethod.POST,
                    Account.class);

            Account updatedAccount = entity.getBody();

            assertThat(updatedAccount.getBalance().doubleValue()).isEqualTo(accounts.get(i).getBalance().add(new BigDecimal(5)).doubleValue());

            accounts.set(i, updatedAccount);

        }

    }

    @Test
    public void testWithdrawFunds() {

        for (int i = 0; i < accounts.size(); i++) {

            ResponseEntity<Account> entity = this.restTemplate.postForEntity(
                    "/atm/" + accounts.get(i).getAccountNumber() + "/withdraw/5.00",
                    HttpMethod.GET,
                    Account.class);

            Account updatedAccount = entity.getBody();

            assertThat(updatedAccount.getBalance().doubleValue()).isEqualTo(accounts.get(i).getBalance().subtract(new BigDecimal(5)));

        }
    }
}
