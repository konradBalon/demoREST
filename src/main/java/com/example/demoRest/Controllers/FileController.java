package com.example.demoRest.Controllers;


import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UploadController {

    public final static String FILE_UPLOAD_PATH = "//home/konrad/Desktop/git/h2Base/upload/";

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + fileName + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{nazwaPliku}")
    public ResponseEntity<Resource> down(@PathVariable("nazwaPliku") String nazwaPliku, HttpServletRequest request) {

        Resource resource = getFile(nazwaPliku);
        MediaType mt = recognizeContentType(resource,request);

        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA) //sciaga plik
                .contentType(mt) //wyświetla plik

                .body(resource);

    }

    private MediaType recognizeContentType(Resource resource, HttpServletRequest request) {
        String contentType = null;


        try {
            contentType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (contentType != null) {
                return MediaType.parseMediaType(contentType);
            } else {
                return MediaType.APPLICATION_JSON;
            }
        }
    }
        private Resource getFile (String nazwaPliku){
            Resource resource = null;
            try {
                Path path = Paths.get(FILE_UPLOAD_PATH + nazwaPliku);
                resource = new UrlResource(path.toUri());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return resource;
        }
    }