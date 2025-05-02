package bw.black.controller;


import bw.black.dto.response.RespStatus;
import bw.black.service.ImageService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize( "hasAuthority('ADMIN')")
    @PostMapping("/{contactsId}")
    public RespStatus addImages(@RequestParam(value = "files") List<MultipartFile> files, @PathVariable Long contactsId) throws IOException {
        return imageService.addImages(files,contactsId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @DeleteMapping("/list/{contactsId}")
    public RespStatus deleteImagesByContactsId(@PathVariable  Long contactsId){
        return imageService.deleteImagesByContactsId(contactsId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @DeleteMapping("/{imageId}")
    public RespStatus deleteImage(@PathVariable  Long imageId){
        return imageService.deleteImage(imageId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping("/list/{contactsId}")
    public List<ResponseEntity<byte[]>> getImages(@PathVariable Long contactsId){
        return imageService.getImages(contactsId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable  Long imageId){
        return imageService.getImage(imageId);
    }
}