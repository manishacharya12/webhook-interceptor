package com.appdirect.devtools.webhooks.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.appdirect.devtools.webhooks.dto.WebhookRequestDto;
import com.appdirect.devtools.webhooks.entity.WebhookRequest;

@Mapper
public interface WebhookRequestMapper {

	@Mapping(target = "id", source = "id")
	@Mapping(target = "sessionId", source = "sessionId")
	@Mapping(target = "content", source = "content")
	WebhookRequest toWebhook(WebhookRequestDto webhookRequestDto);

	WebhookRequestDto toWebhookDto(WebhookRequest webhookRequest);

	List<WebhookRequestDto> toWebhookDtos(List<WebhookRequest> webhookRequests);
}
