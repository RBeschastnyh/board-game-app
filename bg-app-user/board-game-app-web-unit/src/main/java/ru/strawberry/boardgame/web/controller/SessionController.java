package ru.strawberry.boardgame.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.strawberry.boardgame.web.data.dto.AuthRequest;
import ru.strawberry.boardgame.web.service.SessionService;

import java.util.UUID;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(sessionService.getById(sessionId));
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getSessionByUsername(@PathVariable String username) {
        return ResponseEntity.ok(sessionService.getByLogin(username));
    }

    @PutMapping("/{sessionId}/validate")
    public ResponseEntity<?> validate(@PathVariable UUID sessionId, @RequestBody String login) {
        return ResponseEntity.ok(sessionService.validate(sessionId, login));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(sessionService.createSession(authRequest));
    }

    @PostMapping
    public ResponseEntity<?> createSession() {
        return null;
    }
}
