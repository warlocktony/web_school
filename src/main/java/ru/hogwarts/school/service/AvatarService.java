package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;

public interface AvatarService {
    String getExtensions(String fileName);
    void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException;
}
