package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);

    AvatarRepository avatarRepository = mock(AvatarRepository.class);

    String avatarsDir = "./src/test/resources/avatar";

    AvatarServiceImpl avatarService = new AvatarServiceImpl
            (studentService, avatarRepository, avatarsDir);

    Student student1 = new Student(0L, "Voland De Mord", 10_000);
    Avatar avatar1 = new Avatar(0L, avatarsDir, 250,
            "jpg", new byte[]{}, student1);
    Avatar avatarEmpty = new Avatar();


    @Test
    void uploadAvatar__avatarSavedToDBAndDir() throws IOException {
        MultipartFile file = new MockMultipartFile("9.jpg", "9.jpg",
                "jpg", new byte[]{});
        when(studentService.read(student1.getId())).thenReturn(student1);
        when(avatarRepository.findByStudent_id(student1.getId())).thenReturn(Optional.empty());


        avatarService.uploadAvatar(student1.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student1.getId()
                + "." + file.getContentType())));


    }

    @Test
    void readFromDB_studentInRepository_returnAvatar() {

        when(avatarRepository.findById(0L)).thenReturn(Optional.of(avatar1));

        Avatar result = avatarService.readFromDB(0);
        assertEquals(avatar1, result);

    }

    @Test
    void readFromDB_studentNotInRepository_returnAvatar() {
        when(avatarRepository.findById(0L))
                .thenReturn(Optional.empty());
        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> avatarService.readFromDB(0));
        assertEquals("avatar not found", ex.getMessage());

    }

    @Test
    void getPage__returnListOfAvatar() {
        when(avatarRepository.findAll((PageRequest) any()))
                .thenReturn(new PageImpl<>(List.of(avatarEmpty)));

        List<Avatar> result = avatarService.getPage(0, 1);

        assertEquals(List.of(avatarEmpty), result);
    }


}
