package com.appdirect.devtools.webhooks.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.appdirect.devtools.webhooks.dto.WebhookRequestDto;
import com.appdirect.devtools.webhooks.entity.WebhookRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class WebhookRequestMapper {


	public WebhookRequest toWebhookRequest(WebhookRequestDto webhookRequestDto) {
		try {
			return WebhookRequest.builder()
				.id(webhookRequestDto.getId())
				.sessionId(webhookRequestDto.getSessionId())
				.headers(new ObjectMapper().writeValueAsString(webhookRequestDto.getHeaders()))
				.queryParams(new ObjectMapper().writeValueAsString(webhookRequestDto.getQueryParams()))
				.httpMethod(webhookRequestDto.getHttpMethod())
				.requestUrl(webhookRequestDto.getRequestUrl())
				.payload(webhookRequestDto.getPayload())
				.payloadSizeInBytes(webhookRequestDto.getPayloadSizeInBytes())
				.callerHost(webhookRequestDto.getCallerHost())
				.build();
		} catch (JsonProcessingException e) {
			log.error("JSON parsing exception while parsing headers or queryParams");
			return WebhookRequest.builder().build();
		}
	}

	public WebhookRequestDto toWebhookRequestDto(WebhookRequest webhookRequest) {

			return Optional.ofNullable(webhookRequest)
				.map(whr -> {
					try {
						return WebhookRequestDto.builder()
							.id(webhookRequest.getId())
							.sessionId(webhookRequest.getSessionId())
							.headers(new ObjectMapper().readValue(webhookRequest.getHeaders(), new TypeReference<>() {}))
							.queryParams(new ObjectMapper().readValue(webhookRequest.getQueryParams(), new TypeReference<>() {}))
							.httpMethod(webhookRequest.getHttpMethod())
							.requestUrl(webhookRequest.getRequestUrl())
							.payload(webhookRequest.getPayload())
							.payloadSizeInBytes(webhookRequest.getPayloadSizeInBytes())
							.callerHost(webhookRequest.getCallerHost())
							.build();
					} catch (JsonProcessingException e) {
						log.error("JSON parsing exception while parsing headers or queryParams");
						return null;
					}
				})
				.orElse(null);
	}

	public List<WebhookRequestDto> toWebhookRequestDtos(List<WebhookRequest> webhookRequests) {
		return webhookRequests.stream()
			.map(whr -> toWebhookRequestDto(whr))
			.collect(Collectors.toList());
	}
}
