package org.tutorial_ide.fly_away.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tutorial_ide.fly_away.service.CleanupService;

@RestController
@RequiredArgsConstructor
public class CleanupController {

    private final CleanupService cleanupService;

    /** DELETE /cleanup — Public (for testing) */
    @DeleteMapping("/cleanup")
    public ResponseEntity<Void> cleanup() {
        cleanupService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}