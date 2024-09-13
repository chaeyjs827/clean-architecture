package com.perfect.cleanarchitecture.buckpal.account.application.port.out;

import com.perfect.cleanarchitecture.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
