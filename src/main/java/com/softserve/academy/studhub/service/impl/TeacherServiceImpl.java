package com.softserve.academy.studhub.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    private final static Map<Object, Object> CONFIG = new HashMap<>();

    static {
        CONFIG.put("cloud_name", "studhubcloud");
        CONFIG.put("api_key", "728188731214736");
        CONFIG.put("api_secret", "8d9xEPsgdsDVGy71nWQxHZggOow");
    }

    private Cloudinary cloudinary = new Cloudinary(CONFIG);

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(int id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {


        teacher.setCreationDate(LocalDateTime.now());
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

   /* @Override
    public List<Teacher> sortByAge() {
        return teacherRepository.findAllByOrdeByCreationDateAsc();
    }

    @Override
    public List<Teacher> sortByMark() {
        return teacherRepository.findAllByTagListInOrderByMarkAsc();
    }*/

    @Override
    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Integer addPhotoToTeacher(Integer teacherId, MultipartFile multipartFile) {
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.TEACHER_NOTFOUND));
        try {
            teacher.setImageUrl(uploadPhotoToCloudinary(multipartFile));
            teacherRepository.saveAndFlush(teacher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacherId;
    }

    @Override
    public Page<Teacher> findByLastName(String keyword, Pageable pageable) {
        return teacherRepository.findByLastName(keyword, pageable);
    }

    private String uploadPhotoToCloudinary(MultipartFile toUpload) throws IOException {

        @SuppressWarnings("rawtypes")
        Map uploadResult = cloudinary.uploader().upload(toUpload.getBytes(),
            ObjectUtils.asMap("use_filename", "true", "unique_filename", "true"));
        return (String) uploadResult.get("url");
    }
}
