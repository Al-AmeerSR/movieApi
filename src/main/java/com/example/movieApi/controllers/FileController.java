package com.example.movieApi.controllers;


import com.example.movieApi.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    private final  FileService fileService ;

    @Value("${project.poster}")
    private String path;
    FileController(FileService fileService){

        this.fileService = fileService;
    }

@PostMapping("/upload")
public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file)throws IOException {

   String uploadedFileName = fileService.uploadFile(path,file);

        return ResponseEntity.ok("File uploaded :"+uploadedFileName);
}

@GetMapping("/{fileName}")
public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response)throws  IOException ,FileNotFoundException {

  InputStream resourceFile =  fileService.getResourceFile(path,fileName) ;

  response.setContentType(String.valueOf(MediaType.ALL));

  StreamUtils.copy(resourceFile,response.getOutputStream());
}
}
