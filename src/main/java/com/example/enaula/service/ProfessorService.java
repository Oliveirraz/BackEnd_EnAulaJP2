package com.example.enaula.service;

import com.example.enaula.RequestDTO.ProfessorRequestDTO;
import com.example.enaula.ResponseDTO.ProfessorResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Professor;
import com.example.enaula.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;



}