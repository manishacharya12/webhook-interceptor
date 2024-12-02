package com.appdirect.devtools.webhooks.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.appdirect.devtools.webhooks.dto.SessionDto;
import com.appdirect.devtools.webhooks.entity.Session;
import com.appdirect.devtools.webhooks.mapper.SessionMapper;
import com.appdirect.devtools.webhooks.repository.SessionRepository;

@Service
@RequiredArgsConstructor
public class SessionService {

	private final SessionRepository sessionRepository;
	private final SessionMapper mapper;

	public List<SessionDto> getAllSessions() {
		return mapper.toSessionDtos(sessionRepository.findAll());
	}

	public SessionDto getSessionById(long id) {
		Session session = sessionRepository.findById(id).orElse(null);
		return mapper.toSessionDto(session);


	}

	public SessionDto getSessionByUUID(String uuid) {
		Session session = sessionRepository.findByUuid(UUID.fromString(uuid));
		return mapper.toSessionDto(session);
	}

	public SessionDto generateSession() {
		UUID generatedUuid = UUID.randomUUID();
		ZonedDateTime creationTime = ZonedDateTime.now(ZoneId.of("UTC"));
		Session generatedSession = sessionRepository.save(Session.builder()
			.uuid(generatedUuid)
			.createdOn(creationTime)
			.expiry(creationTime.plusDays(10))
			.build());
		return mapper.toSessionDto(generatedSession);
	}
}
