package com.appdirect.devtools.webhooks.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.appdirect.devtools.webhooks.dto.SessionDto;
import com.appdirect.devtools.webhooks.entity.Session;
import com.appdirect.devtools.webhooks.repository.SessionRepository;

@Service
@RequiredArgsConstructor
public class SessionService {

	private final SessionRepository sessionRepository;

	public List<SessionDto> getAllSessions() {
		return sessionRepository.findAll().stream()
			.map(s -> SessionDto.builder()
				.id(s.getId())
				.uuid(s.getUuid())
				.createdOn(s.getCreatedOn())
				.expiry(s.getExpiry())
				.build())
			.collect(Collectors.toList());
	}

	public SessionDto getSessionById(long id) {
		Session session = sessionRepository.findById(id).orElse(null);
		return SessionDto.builder()
			.id(session.getId())
			.uuid(session.getUuid())
			.createdOn(session.getCreatedOn())
			.expiry(session.getExpiry())
			.build();
	}

	public SessionDto getSessionByUUID(String uuid) {
		Session session = sessionRepository.findByUuid(UUID.fromString(uuid));
		return SessionDto.builder()
			.id(session.getId())
			.uuid(session.getUuid())
			.createdOn(session.getCreatedOn())
			.expiry(session.getExpiry())
			.build();
	}

	public SessionDto generateSession() {
		UUID generatedUuid = UUID.randomUUID();
		ZonedDateTime creationTime = ZonedDateTime.now(ZoneId.of("UTC"));
		Session generatedSession = sessionRepository.save(Session.builder()
			.uuid(generatedUuid)
			.createdOn(creationTime)
			.expiry(creationTime.plusDays(10))
			.build());
		return SessionDto.builder()
			.id(generatedSession.getId())
			.uuid(generatedSession.getUuid())
			.createdOn(generatedSession.getCreatedOn())
			.expiry(generatedSession.getExpiry())
			.build();
	}
}
