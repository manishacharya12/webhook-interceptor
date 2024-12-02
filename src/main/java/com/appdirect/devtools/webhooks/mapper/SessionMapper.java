package com.appdirect.devtools.webhooks.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.appdirect.devtools.webhooks.dto.SessionDto;
import com.appdirect.devtools.webhooks.entity.Session;

@Component
public class SessionMapper {

	public Session toSession(SessionDto sessionDto) {
		return Session.builder()
			.id(sessionDto.getId())
			.uuid(sessionDto.getUuid())
			.createdOn(sessionDto.getCreatedOn())
			.expiry(sessionDto.getExpiry())
			.build();
	}

	public SessionDto toSessionDto(Session session) {
		return Optional.ofNullable(session)
			.map(s -> SessionDto.builder()
				.id(session.getId())
				.uuid(session.getUuid())
				.createdOn(session.getCreatedOn())
				.expiry(session.getExpiry())
				.build())
			.orElse(null);
	}

	public List<SessionDto> toSessionDtos(List<Session> sessions) {
		return sessions.stream()
			.map(ssn -> toSessionDto(ssn))
			.collect(Collectors.toList());
	}
}
