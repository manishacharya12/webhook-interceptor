package com.appdirect.devtools.webhooks.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.appdirect.devtools.webhooks.dto.WebhookRequestDto;
import com.appdirect.devtools.webhooks.entity.WebhookRequest;
import com.appdirect.devtools.webhooks.mapper.WebhookRequestMapper;
import com.appdirect.devtools.webhooks.repository.WebhookRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
@RequiredArgsConstructor
public class WebhookRequestService {

	private final WebhookRequestRepository webhookRequestRepository;
	private final SessionService sessionService;
	private final WebhookRequestMapper mapper;

	public List<WebhookRequestDto> getAllWebhookRequestsBySessionUuid(String uuid) {
		long sessionId = sessionService.getSessionByUUID(uuid).getId();
		return mapper.toWebhookRequestDtos(webhookRequestRepository.findBySessionId(sessionId));
	}

	public WebhookRequestDto getWebhookRequestBySessionUuidAndWebhookId(String sessionUuid, long webhookId) {
		long sessionId = sessionService.getSessionByUUID(sessionUuid).getId();
		WebhookRequest webhookRequest = webhookRequestRepository.findBySessionIdAndId(sessionId, webhookId);
		return mapper.toWebhookRequestDto(webhookRequest);
	}

	public WebhookRequestDto createWebhookRequest(WebhookRequestDto webhookRequestDto) {
		WebhookRequest newWebhookRequest = webhookRequestRepository.save(mapper.toWebhookRequest(webhookRequestDto));
		return mapper.toWebhookRequestDto(newWebhookRequest);
	}
}
