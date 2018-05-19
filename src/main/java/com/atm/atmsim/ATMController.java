package com.atm.atmsim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4201"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/atm")
public class ATMController {

    private ATMRepository repository;

    /*
     * Class Constructor
     *
     * We pass the ATMRepository to the constructor so that dependency injection instantiates
     * this class for us. Hence the @AutoWired tag, this is what makes DI work with spring.
     *
     */
    @Autowired
    public void ATMService(final ATMRepository repository) {

        this.repository = repository;

    }

    /*
     * Retrieve all account
     *
     * This method retrieves all records in the database and returns a collection of type
     * List.
     *
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {

        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);

    }

    /*
     * Create a new account
     *
     * This method creates a new record. It uses the @RequestBody annotation which parses
     * the body of the request and marshals it as a Account object.
     *
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {

        return new ResponseEntity<>(repository.save(account), HttpStatus.OK);

    }

    @PostMapping(path = "/{accountNumber}/deposit/{amount}")
    public ResponseEntity<Account> addFunds(@PathVariable("accountNumber") Long accountNumber, @PathVariable("amount") BigDecimal amount) {

        try {

            Optional<Account> account = repository.findByAccountNumber(accountNumber);

            if (account.isPresent()) {

                Account currentAccount = account.get();

                currentAccount.setBalance(currentAccount.getBalance().add(amount));

                return new ResponseEntity<>(repository.save(currentAccount), HttpStatus.OK);

            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (EmptyResultDataAccessException ex) {

            return new ResponseEntity<>(new Account(), HttpStatus.NOT_FOUND);

        }

    }

    @PostMapping(path = "/{accountNumber}/withdraw/{amount}")
    public ResponseEntity<Account> withdrawFunds(@PathVariable("accountNumber") Long accountNumber, @PathVariable("amount") BigDecimal amount) {

        try {

            Optional<Account> account = repository.findByAccountNumber(accountNumber);

            if (account.isPresent()) {

                Account currentAccount = account.get();

                currentAccount.setBalance(currentAccount.getBalance().add(amount));

                return new ResponseEntity<>(repository.save(currentAccount), HttpStatus.OK);

            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (EmptyResultDataAccessException ex) {

            return new ResponseEntity<>(new Account(), HttpStatus.NOT_FOUND);

        }

    }

    /*
     * Delete an account
     *
     * This method deletes a record by the id passed in the url path (i.e.: http://localhost:8081/atm/123).
     *
     * If the record does not exist matching the same id we catch the exception and then turn around
     * and return an http status code of 404.
     *
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {

        try {

            repository.deleteById(id);

            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (EmptyResultDataAccessException ex) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }

    /*
     * Get current balance for account
     *
     * If the account does not exist matching the same id we catch the exception and then turn around
     * and return an http status code of 404.
     *
     */
    @GetMapping(path = "/{accountNumber}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountNumber") Long accountNumber) {

        try {

            Optional<Account> account = repository.findByAccountNumber(accountNumber);

            if (account.isPresent()) {

                return new ResponseEntity<>(account.get(), HttpStatus.OK);

            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (EmptyResultDataAccessException ex) {

            return new ResponseEntity<>(new Account(), HttpStatus.NOT_FOUND);

        }

    }
}
