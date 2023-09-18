package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;


import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public class AvatarServiceImpl implements AvatarService {

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final String avatarsDir;

    private final StudentService studentService;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService,
                             AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.avatarsDir = avatarsDir;
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("was called method uploadAvatar");

        Student student = studentService.read(studentId);

        Path filePath = Path.of(avatarsDir, student.getId() + "." + gatExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);

    }

    public Avatar readFromDB(long id) {
        logger.info("was called method readFromDB with data" + id);

        Avatar findByIdAvatar = avatarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("avatar not found"));

        logger.info("from method readFromDB return" + id);

        return findByIdAvatar;
    }

    public List<Avatar> getPage(int pageNumber, int size) {
        logger.info("was called method getPage with data" + pageNumber + size);

        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        List<Avatar> findAllAndGetContent = avatarRepository.findAll(pageRequest).getContent();

        logger.info("from method getPage return" + pageNumber + size);


        return findAllAndGetContent;
    }

    public String gatExtensions(String fileName) {
        logger.info("was called method gatExtensions with data" + fileName);

        String result = fileName.substring(fileName.lastIndexOf(".") + 1);

        logger.info("from method gatExtensions return" + fileName);


        return result;
    }

}


