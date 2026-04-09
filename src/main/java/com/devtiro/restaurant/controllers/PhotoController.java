package com.devtiro.restaurant.controllers;

import com.devtiro.restaurant.domain.dtos.PhotoDto;
import com.devtiro.restaurant.domain.entities.Photo;
import com.devtiro.restaurant.mappers.PhotoMapper;
import com.devtiro.restaurant.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/photos")
public class PhotoController {
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    public PhotoDto uploadPhoto(MultipartFile file){
        if(file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"file is empty");
        }else{
        Photo savedPhoto=photoService.uploadPhoto(file);
        return photoMapper.toDto(savedPhoto);
    }}
}
