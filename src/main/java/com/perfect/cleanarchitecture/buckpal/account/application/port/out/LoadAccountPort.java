package com.perfect.cleanarchitecture.buckpal.account.application.port.out;

import com.perfect.cleanarchitecture.buckpal.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);
}
