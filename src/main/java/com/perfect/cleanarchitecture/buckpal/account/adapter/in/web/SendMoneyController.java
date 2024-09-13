package com.perfect.cleanarchitecture.buckpal.account.adapter.in.web;

import com.perfect.cleanarchitecture.buckpal.account.application.port.in.SendMoneyCommand;
import com.perfect.cleanarchitecture.buckpal.account.application.port.in.SendMoneyUseCase;
import com.perfect.cleanarchitecture.buckpal.account.domain.Account;
import com.perfect.cleanarchitecture.buckpal.account.domain.Money;
import com.perfect.cleanarchitecture.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@WebAdapter
class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount) {

        SendMoneyCommand command = new SendMoneyCommand(
                new Account.AccountId(sourceAccountId),
                new Account.AccountId(targetAccountId),
                Money.of(amount));

        sendMoneyUseCase.sendMoney(command);
    }

}
