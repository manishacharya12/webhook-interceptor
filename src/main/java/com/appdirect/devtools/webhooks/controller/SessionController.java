package com.appdirect.devtools.webhooks.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.devtools.webhooks.dto.SessionDto;
import com.appdirect.devtools.webhooks.service.SessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/sessions"})
public class SessionController {

	private final SessionService sessionService;

	@PostMapping(path = {"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> generateSession() {
		return ResponseEntity.ok(sessionService.generateSession());
	}

	@GetMapping(path = {"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SessionDto>> getAllSessions() {
		return ResponseEntity.ok(sessionService.getAllSessions());
	}

	@GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> getSessionById(@PathVariable long id) {
		return ResponseEntity.ok(sessionService.getSessionById(id));
	}

	@GetMapping(path = {"/uuid/{uuid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> getSessionByUUID(@PathVariable String uuid) {
		return ResponseEntity.ok(sessionService.getSessionByUUID(uuid));
	}
}
