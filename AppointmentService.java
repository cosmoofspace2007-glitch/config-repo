package m3.appointmentservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private PatientClient patientClient;

    @Retry(
            name = "patientRetry",
            fallbackMethod = "getPatientFallback"
    )
    public ResponseEntity<?> checkPatient(Long patientId){

        return ResponseEntity.ok(patientClient.getPatient(patientId));

    }

    /**
     * Fallback sau khi Retry thất bại
     */
    public ResponseEntity<ApiResponseError> getPatientFallback(Long patientId, Exception e){

        ApiResponseError error = new ApiResponseError(
                503,
                "Không thể lấy thông tin bệnh nhân. Vui lòng thử lại sau."
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);

    }

}