package com.appdirect.devtools.webhooks.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.appdirect.devtools.webhooks.dto.WebhookRequestDto;
import com.appdirect.devtools.webhooks.entity.WebhookRequest;
import com.appdirect.devtools.webhooks.repository.WebhookRequestRepository;

@Service
@RequiredArgsConstructor
public class WebhookRequestService {

	private final WebhookRequestRepository webhookRequestRepository;
	private final SessionService sessionService;
	//private final WebhookMapper webhookMapper;

	public List<WebhookRequestDto> getAllWebhookRequests(String uuid) {
		long sessionId = sessionService.getSessionByUUID(uuid).getId();
		return webhookRequestRepository.findBySessionId(sessionId).stream()
			.map(whr -> WebhookRequestDto.builder()
				.id(whr.getId())
				.sessionId(whr.getSessionId())
				.content(whr.getContent())
				.build())
			.collect(Collectors.toList());
		// return webhookMapper.toWebhookDtos(webhookRepository.findAll());
	}

	public WebhookRequestDto getWebhook(long id) {
		WebhookRequest webhookRequest = webhookRequestRepository.findById(id).orElse(null);
		return WebhookRequestDto.builder()
			.id(webhookRequest.getId())
			.sessionId(webhookRequest.getSessionId())
			.content(webhookRequest.getContent())
		.build();
	}

	public WebhookRequestDto createWebhookRequest(WebhookRequestDto webhookRequestDto) {
		//Webhook webhook = webhookMapper.toWebhook(webHookDto);
		WebhookRequest newWebhookRequest = webhookRequestRepository.save(WebhookRequest.builder().id(webhookRequestDto.getId()).sessionId(webhookRequestDto.getSessionId()).content(webhookRequestDto.getContent()).build());
		return WebhookRequestDto.builder()
			.id(newWebhookRequest.getId())
			.sessionId(newWebhookRequest.getSessionId())
			.content(newWebhookRequest.getContent())
		.build();
	}
}
