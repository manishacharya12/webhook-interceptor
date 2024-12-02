package com.appdirect.devtools.webhooks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column
	private long sessionId;

	@Column(columnDefinition = "text")
	private String headers;

	@Column(columnDefinition = "text")
	private String queryParams;

	@Column(columnDefinition = "varchar(6)")
	private String httpMethod;

	@Column(columnDefinition = "varchar(2048)")
	private String requestUrl;

	@Column
	private long payloadSizeInBytes;

	@Column(columnDefinition = "text")
	private String payload;

	@Column(columnDefinition = "varchar(30)")
	private String callerHost;
}
