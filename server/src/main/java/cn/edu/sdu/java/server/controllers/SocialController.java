package cn.edu.sdu.java.server.controllers;

import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.payload.response.OptionItemList;
import cn.edu.sdu.java.server.services.SocialService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/social")
public class SocialController {
    private final SocialService socialService;


    public SocialController(SocialService socialService) {
        this.socialService = socialService;
    }

    @PostMapping("/getStudentItemOptionList")
    public OptionItemList getStudentItemOptionList(@Valid @RequestBody DataRequest dataRequest) {
        return socialService.getStudentItemOptionList(dataRequest);
    }
    @PostMapping("/getSocialList")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse getSocialList(@Valid @RequestBody DataRequest dataRequest) {
        return socialService.getSocialList(dataRequest);
    }
    @PostMapping("/socialSave")
    public DataResponse socialSave(@Valid @RequestBody DataRequest dataRequest) {
        return socialService.socialSave(dataRequest);
    }
    @PostMapping("/socialDelete")
    public DataResponse socialDelete(@Valid @RequestBody DataRequest dataRequest) {
        return socialService.socialDelete(dataRequest);
    }

}
