package m3.appointmentservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private DoctorClient doctorClient;

    @CircuitBreaker(
            name = "doctorServiceCB",
            fallbackMethod = "getDoctorFallback"
    )
    public Object checkDoctor(Long doctorId) {

        return doctorClient.getDoctor(doctorId);

    }
    public ApiResponseError getDoctorFallback(Long doctorId, Exception e) {

        return new ApiResponseError(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây."
        );

    }

}