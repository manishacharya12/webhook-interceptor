package com.appdirect.devtools.webhooks.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/sessions"})
public class WebhookRequestsController {

	private final SessionService sessionService;
	private final WebhookRequestService webhookRequestService;

	@GetMapping(path = {"/{uuid}/sessionrequests"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WebhookRequestDto>> getAllWebhookRequests(@PathVariable String uuid) {
		return ResponseEntity.ok(webhookRequestService.getAllWebhookRequests(uuid));
	}

	@GetMapping(path = {"/{uuid}/requests/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WebhookRequestDto> getWebhookRequest(@PathVariable UUID uuid, @PathVariable long id) {
		return ResponseEntity.ok(webhookRequestService.getWebhook(id));
	}

	@RequestMapping(path = {"/{uuid}/requests"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WebhookRequestDto> createWebHookRequest(
		final HttpServletRequest request,
		@PathVariable String uuid,
		@RequestBody(required=false) String eventBody,
		@RequestParam(required=false) Map<String, String> queryParam,
		@RequestHeader(required=false) Map<String, String> headers) {

		System.out.println(request.getMethod());
		System.out.println(request.getRemoteAddr());
		System.out.println("url " + request.getRequestURL());
		System.out.println("uri " + request.getRequestURI());
		System.out.println(eventBody.getBytes().length);

		long sessionId = sessionService.getSessionByUUID(uuid).getId(); // resolve id from session uuid
		WebhookRequestDto builtWebhookRequestDto = WebhookRequestDto.builder()
			.sessionId(sessionId)
			.content(eventBody)
			.build();
		WebhookRequestDto newWebhookRequest = webhookRequestService.createWebhookRequest(builtWebhookRequestDto);
		return ResponseEntity.created(URI.create("/" + newWebhookRequest.getId() + "/")).body(newWebhookRequest);
	}
}
