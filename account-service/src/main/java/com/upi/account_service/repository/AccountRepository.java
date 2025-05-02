package com.upi.account_service.repository;

import com.upi.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUserId(String userId);
}
