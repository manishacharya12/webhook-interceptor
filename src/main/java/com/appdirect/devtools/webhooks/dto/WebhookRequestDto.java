package com.appdirect.devtools.webhooks.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WebhookRequestDto {

		private long id;
		private long sessionId;
		private Map<String, String> headers;
		//private Map<String, String> queryParams;
		private String content;
}
