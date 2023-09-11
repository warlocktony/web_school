package ru.hogwarts.school.controller;

import org.hibernate.mapping.ManyToOne;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.model.Avatar;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    private final int maxFileSizeInKb = 300;

    public AvatarController(AvatarService avatarService){
        this.avatarService=avatarService;

        @PostMapping(value = "/{id}/avatar",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
                public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,@RequestParam MultipartFile avatar)
                throw IOException{


        }

    }

}
