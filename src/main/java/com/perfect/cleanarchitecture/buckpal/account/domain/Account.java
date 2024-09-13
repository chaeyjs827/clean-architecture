package com.perfect.cleanarchitecture.buckpal.account.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Account 엔티티는 실제 계좌의 현재 스냅숏을 제공합니다.
 *
 *
 */


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Account {

    private AccountId id;
    private Money baselineBalance;  // 계정의 기준 잔액. 활동창의 첫 번째 활동 전 계정 잔액
    private ActivityWindow activityWindow;  // 해당 계정의 최신 활동

    // id 없이 Entity 생성(아직 persist 되지 않은 엔티티)
    public static Account withoutId(
            Money baselineBalance,
            ActivityWindow activityWindow
    ) {
        return new Account(null, baselineBalance, activityWindow);
    }

    // id 포함 Entity 생성(persisted 된 엔티티를 재구성 할 떄 사용)
    public static Account withId(
        AccountId accountId,
        Money baselineBalance,
        ActivityWindow activityWindow
    ) {
        return new Account(accountId, baselineBalance, activityWindow);
    }

    public Optional<AccountId> getId() {
        return Optional.ofNullable(this.id);
    }

    // 기준 잔액에 활동값을 더하여 계정의 총 잔액을 계산
    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id)
        );
    }

    /**
     * 해당 계좌에서 특정 금액을 인출하려고 시도, 성공 하면 음수 값으로 새 활동을 생성
     * @return 성공 여부에 따라 true/false 를 반환
     */
    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            return false;
        }

        Activity withdrawal = new Activity(
                this.id,
                this.id,
                targetAccountId,
                LocalDateTime.now(),
                money
        );

        this.activityWindow.addActivity(withdrawal);
        return true;

    }

    private boolean mayWithdraw(Money money) {
        return Money.add(
                this.calculateBalance(),
                money.negate())
                .isPositiveOrZero();
    }

    /**
     * 정확한 금액을 입금
     * 성공적이라면, 유효한 금액과 함께 activity가 생성됨
     *  @return 입금 성공 유무에 따라 true/false 반환
     */
    public boolean deposit(Money money, AccountId sourceAccountId) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountId,
                this.id,
                LocalDateTime.now(),
                money
        );
        this.activityWindow.addActivity(deposit);
        return true;

    }

    @Value
    public static class AccountId {
        private Long value;
    }


}
