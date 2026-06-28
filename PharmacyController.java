package m3.pharmacyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyController {

    @Value("${app.branch-name}")
    private String branchName;

    @Value("${app.hotline}")
    private String hotline;

    @GetMapping("/info")
    public String info(){

        return "Chi nhánh: "
                + branchName
                + " Hotline: "
                + hotline;

    }

}
