package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);

    String gatExtensions(String fileName);

    List<Avatar> getPage(int pageNumber, int size);
}



