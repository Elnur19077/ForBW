package bw.black.service;

import bw.black.dto.response.RespStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    RespStatus addImages(List<MultipartFile> files, Long contactsId) throws IOException;

    RespStatus deleteImagesByContactsId(Long contactsId);

    RespStatus deleteImage(Long imageId);

    List<ResponseEntity<byte[]>> getImages(Long contactsId);

    List<ResponseEntity<byte[]>> getImages();

    ResponseEntity<byte[]> getImage(Long imageId);
}

