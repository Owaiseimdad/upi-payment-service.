package com.upi.authentication_service.repository;

import com.upi.authentication_service.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, String> {
}
