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
				.uuid(webhookRequestDto.getUuid())
				.sessionId(webhookRequestDto.getSessionId())
				.headers(new ObjectMapper().writeValueAsString(webhookRequestDto.getHeaders()))
				.queryParams(new ObjectMapper().writeValueAsString(webhookRequestDto.getQueryParams()))
				.httpMethod(webhookRequestDto.getHttpMethod())
				.requestUrl(webhookRequestDto.getRequestUrl())
				.payload(webhookRequestDto.getPayload())
				.payloadSizeInBytes(webhookRequestDto.getPayloadSizeInBytes())
				.callerHost(webhookRequestDto.getCallerHost())
				.createdOn(webhookRequestDto.getCreatedOn())
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
							.id(whr.getId())
							.uuid(whr.getUuid())
							.sessionId(whr.getSessionId())
							.headers(new ObjectMapper().readValue(whr.getHeaders(), new TypeReference<>() {}))
							.queryParams(new ObjectMapper().readValue(whr.getQueryParams(), new TypeReference<>() {}))
							.httpMethod(whr.getHttpMethod())
							.requestUrl(whr.getRequestUrl())
							.payload(whr.getPayload())
							.payloadSizeInBytes(whr.getPayloadSizeInBytes())
							.callerHost(whr.getCallerHost())
							.createdOn(whr.getCreatedOn())
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
