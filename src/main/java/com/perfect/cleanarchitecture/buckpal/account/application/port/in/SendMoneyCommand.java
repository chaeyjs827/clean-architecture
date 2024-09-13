package com.perfect.cleanarchitecture.buckpal.account.application.port.in;

import com.perfect.cleanarchitecture.buckpal.account.domain.Account;
import com.perfect.cleanarchitecture.buckpal.account.domain.Money;
import com.perfect.cleanarchitecture.common.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 호출하는
 */
@Value
@EqualsAndHashCode(callSuper = false)
public
class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;

    @NotNull
    private final Account.AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(
            Account.AccountId sourceAccountId,
            Account.AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
