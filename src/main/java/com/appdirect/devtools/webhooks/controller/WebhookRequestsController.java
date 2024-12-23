package com.appdirect.devtools.webhooks.controller;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.devtools.webhooks.dto.WebhookRequestDto;
import com.appdirect.devtools.webhooks.service.SessionService;
import com.appdirect.devtools.webhooks.service.WebhookRequestService;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = {"/api/v1/sessions"})
public class WebhookRequestsController {

	private final SessionService sessionService;
	private final WebhookRequestService webhookRequestService;

	@GetMapping(path = {"/{sessionUuid}/sessionrequests"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WebhookRequestDto>> getAllWebhookRequestsBySessionUuid(@PathVariable String sessionUuid) {
		log.info("Received request with to get webhooks by session uuid={}", sessionUuid);
		return ResponseEntity.ok(webhookRequestService.getAllWebhookRequestsBySessionUuid(sessionUuid));
	}

	@GetMapping(path = {"/{sessionUuid}/requests/{webhookId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WebhookRequestDto> getWebhookRequestBySessionUuidAndWebhookId(@PathVariable String sessionUuid, @PathVariable long webhookId) {
		log.info("Received request with method to get webhook by session uuid={} and webhook Id={}", sessionUuid, webhookId);
		return ResponseEntity.ok(webhookRequestService.getWebhookRequestBySessionUuidAndWebhookId(sessionUuid, webhookId));
	}

	@GetMapping(path = {"/{sessionUuid}/requests/uuid/{webhookUuid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WebhookRequestDto> getWebhookRequestBySessionUuidAndWebhookUuid(@PathVariable String sessionUuid, @PathVariable String webhookUuid) {
		log.info("Received request with method to get webhook by session uuid={} and webhook UUUID={}", sessionUuid, webhookUuid);
		return ResponseEntity.ok(webhookRequestService.getWebhookRequestBySessionUuidAndWebhookUuid(sessionUuid, webhookUuid));
	}

	@RequestMapping(path = {"/{sessionUuid}/requests"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WebhookRequestDto> createWebHookRequest(
		final HttpServletRequest request,
		@PathVariable String sessionUuid,
		@RequestBody(required=false) String eventBody,
		@RequestParam(required=false) Map<String, String> queryParams,
		@RequestHeader(required=false) Map<String, String> headers) {

		log.info("Received a webhook request with method={} and host={}", request.getMethod(), request.getRemoteHost());
		long sessionId = sessionService.getSessionByUUID(sessionUuid).getId();
		UUID generatedUuid = UUID.randomUUID();
		ZonedDateTime creationTime = ZonedDateTime.now(ZoneId.of("UTC"));
		WebhookRequestDto builtWebhookRequestDto = WebhookRequestDto.builder()
			.sessionId(sessionId)
			.uuid(generatedUuid)
			.headers(headers)
			.queryParams(queryParams)
			.httpMethod(request.getMethod())
			.requestUrl(request.getRequestURL().toString())
			.callerHost(request.getRemoteAddr())
			.createdOn(creationTime)
			.build();

		if(eventBody != null && !eventBody.isEmpty()) {
			builtWebhookRequestDto = builtWebhookRequestDto.toBuilder()
				.payload(eventBody)
				.payloadSizeInBytes(eventBody.getBytes().length)
				.build();
		}
		else {
			builtWebhookRequestDto = builtWebhookRequestDto.toBuilder()
				.payloadSizeInBytes(0L)
				.build();
		}

		WebhookRequestDto newWebhookRequest = webhookRequestService.createWebhookRequest(builtWebhookRequestDto);
		return ResponseEntity.created(URI.create("/" + newWebhookRequest.getId() + "/")).body(newWebhookRequest);
	}
}
