package com.appdirect.devtools.webhooks.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SessionDto {

	private long id;
	private UUID uuid;
	private ZonedDateTime createdOn;
	private ZonedDateTime expiry;
}
