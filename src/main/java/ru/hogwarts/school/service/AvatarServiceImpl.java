package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static sun.jvm.hotspot.gc.z.ZForwardingEntry.getSize;


@Service
public class AvatarServiceImpl implements AvatarService{

    private final String avatarsDir;

    private final StudentService studentService;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService,
                             AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}")String avatarsDir) {
        this.avatarsDir = avatarsDir;
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    public void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException {
        Student student = studentService.read(id);

        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try(
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ){
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudent_id(id).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile, getSize() );
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);

    }

    private String gatExtensions(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
