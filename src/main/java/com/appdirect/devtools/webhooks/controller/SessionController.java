package com.appdirect.devtools.webhooks.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.devtools.webhooks.dto.SessionDto;
import com.appdirect.devtools.webhooks.service.SessionService;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = {"/api/v1/sessions"})
public class SessionController {

	private final SessionService sessionService;

	@PostMapping(path = {"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> generateSession() {
		log.info("Received session generation request");
		return ResponseEntity.ok(sessionService.generateSession());
	}

	@GetMapping(path = {"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SessionDto>> getAllSessions() {
		log.info("Received request to fetch all sessions");
		return ResponseEntity.ok(sessionService.getAllSessions());
	}

	@GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> getSessionById(@PathVariable long id) {
		log.info("Received request to fetch session by Id={}", id);
		return ResponseEntity.ok(sessionService.getSessionById(id));
	}

	@GetMapping(path = {"/uuid/{uuid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SessionDto> getSessionByUUID(@PathVariable String uuid) {
		log.info("Received request to fetch session by UUID={}", uuid);
		return ResponseEntity.ok(sessionService.getSessionByUUID(uuid));
	}
}
