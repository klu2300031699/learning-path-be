package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    // Enroll user in a course
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollUser(@RequestBody Enrollment enrollment) {
        try {
            Enrollment created = service.enrollUser(enrollment);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Get user enrollments
    @GetMapping("/user/{userId}")
    public List<Enrollment> getUserEnrollments(@PathVariable Long userId) {
        return service.getUserEnrollments(userId);
    }

    // Check if user is enrolled
    @GetMapping("/check/{userId}/{courseId}")
    public ResponseEntity<?> checkEnrollment(@PathVariable Long userId, @PathVariable Long courseId) {
        boolean isEnrolled = service.isUserEnrolled(userId, courseId);
        return ResponseEntity.ok(new EnrollmentCheckResponse(isEnrolled));
    }

    // Update progress
    @PutMapping("/progress/{enrollmentId}")
    public ResponseEntity<?> updateProgress(@PathVariable Long enrollmentId, @RequestBody ProgressUpdate progressUpdate) {
        try {
            Enrollment updated = service.updateProgress(enrollmentId, progressUpdate.getProgress());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Helper classes
    static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    static class EnrollmentCheckResponse {
        private boolean enrolled;
        public EnrollmentCheckResponse(boolean enrolled) { this.enrolled = enrolled; }
        public boolean isEnrolled() { return enrolled; }
        public void setEnrolled(boolean enrolled) { this.enrolled = enrolled; }
    }

    static class ProgressUpdate {
        private Integer progress;
        public Integer getProgress() { return progress; }
        public void setProgress(Integer progress) { this.progress = progress; }
    }
}
