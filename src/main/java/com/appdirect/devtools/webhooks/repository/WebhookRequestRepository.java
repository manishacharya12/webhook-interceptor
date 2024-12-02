package com.appdirect.devtools.webhooks.repository;

import java.util.List;

import com.appdirect.devtools.webhooks.entity.WebhookRequest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookRequestRepository extends JpaRepository<WebhookRequest, Long> {

	List<WebhookRequest> findBySessionId(long sessionId);

	WebhookRequest findBySessionIdAndId(long sessionId, long webhookId);
}
