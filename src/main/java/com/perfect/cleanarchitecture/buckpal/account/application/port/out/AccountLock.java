package com.perfect.cleanarchitecture.buckpal.account.application.port.out;

import com.perfect.cleanarchitecture.buckpal.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
