package m3.appointmentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id){

        return appointmentService.checkPatient(id);

    }

}
