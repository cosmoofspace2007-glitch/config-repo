package m3.doctorservice;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/search")
    @RateLimiter(name = "searchDoctorLimit", fallbackMethod = "rateLimitFallback")
    public List<Doctor> searchDoctor(@RequestParam String name) {

        return doctorRepository.findByNameContainingIgnoreCase(name);

    }

    public ResponseEntity<ApiResponseError> rateLimitFallback(
            String name,
            RequestNotPermitted ex
    ) {

        ApiResponseError error = new ApiResponseError(
                429,
                "Bạn đã gọi API quá nhiều lần. Vui lòng thử lại sau 10 giây."
        );

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(error);

    }

}