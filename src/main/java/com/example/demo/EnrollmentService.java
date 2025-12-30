package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository repository;

    @Autowired
    private UserDetailsRepository userRepository;

    // Enroll user in a course
    public Enrollment enrollUser(Enrollment enrollment) {
        // Check if already enrolled
        Optional<Enrollment> existing = repository.findByUserIdAndCourseId(
            enrollment.getUserId(), 
            enrollment.getCourseId()
        );
        
        if (existing.isPresent()) {
            throw new RuntimeException("User already enrolled in this course");
        }
        
        // Fetch user details and populate enrollment
        UserDetails user = userRepository.findById(enrollment.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        enrollment.setUserName(user.getFirstName() + " " + user.getLastName());
        enrollment.setUserEmail(user.getEmail());
        enrollment.setUserMobileNumber(user.getMobileNumber());
        
        return repository.save(enrollment);
    }

    // Get all enrollments for a user
    public List<Enrollment> getUserEnrollments(Long userId) {
        return repository.findByUserId(userId);
    }

    // Check if user is enrolled in a course
    public boolean isUserEnrolled(Long userId, Long courseId) {
        return repository.findByUserIdAndCourseId(userId, courseId).isPresent();
    }

    // Update progress
    public Enrollment updateProgress(Long enrollmentId, Integer progress) {
        Enrollment enrollment = repository.findById(enrollmentId)
            .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setProgress(progress);
        return repository.save(enrollment);
    }
}
