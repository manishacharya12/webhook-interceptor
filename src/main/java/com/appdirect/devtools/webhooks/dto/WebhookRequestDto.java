package com.appdirect.devtools.webhooks.dto;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class WebhookRequestDto {

		private long id;
		private UUID uuid;
		private long sessionId;
		private Map<String, String> headers;
		private Map<String, String> queryParams;
		private String httpMethod;
		private String requestUrl;
		private String payload;
		private long payloadSizeInBytes;
		private String callerHost;
		private ZonedDateTime createdOn;
}
