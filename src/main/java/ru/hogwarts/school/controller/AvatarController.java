package ru.hogwarts.school.controller;

import org.hibernate.mapping.ManyToOne;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.model.Avatar;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    private final int maxFileSizeInKb = 300;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

        @PostMapping(value = "/{studentId}",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
                public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,@RequestParam MultipartFile avatar)
            throws IOException {

            if (avatar.getSize() > 1024 * maxFileSizeInKb) {
                return ResponseEntity.badRequest().body("picture size is big");
            }
            avatarService.uploadAvatar(studentId, avatar);
            return ResponseEntity.ok().body("picture is save");
        }

        @GetMapping("/{id}/avatar-from-db")
        public ResponseEntity<byte[]> downloadAvatarFromDB(@PathVariable long id){
        Avatar avatar = avatarService.readFromDB(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.ok().headers(headers).body(avatar.getData());
        }
        @GetMapping(value = "/{id}/avatar-from-file")
        public void downloadAvatarFile(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.readFromDB(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()){
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }


    }

}




