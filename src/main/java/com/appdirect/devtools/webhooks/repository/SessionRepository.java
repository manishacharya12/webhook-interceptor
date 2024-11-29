package com.appdirect.devtools.webhooks.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appdirect.devtools.webhooks.entity.Session;

public interface SessionRepository  extends JpaRepository<Session, Long> {

	Session findByUuid(UUID uuid);
}
