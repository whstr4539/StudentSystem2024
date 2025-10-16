package cn.edu.sdu.java.server.controllers;

import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.payload.response.OptionItemList;
import cn.edu.sdu.java.server.services.HonorService;
import cn.edu.sdu.java.server.services.VolunteerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;


    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/getStudentItemOptionList")
    public OptionItemList getStudentItemOptionList(@Valid @RequestBody DataRequest dataRequest) {
        return volunteerService.getStudentItemOptionList(dataRequest);
    }
    @PostMapping("/getVolunteerList")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getVolunteerList(@Valid @RequestBody DataRequest dataRequest) {
        return volunteerService.getVolunteerList(dataRequest);
    }
    @PostMapping("/volunteerSave")
    public DataResponse volunteerSave(@Valid @RequestBody DataRequest dataRequest) {
        return volunteerService.volunteerSave(dataRequest);
    }
    @PostMapping("/volunteerDelete")
    public DataResponse volunteerDelete(@Valid @RequestBody DataRequest dataRequest) {
        return volunteerService.volunteerDelete(dataRequest);
    }

}
