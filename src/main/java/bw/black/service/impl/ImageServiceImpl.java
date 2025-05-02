package bw.black.service.impl;

import bw.black.dto.response.RespStatus;
import bw.black.entity.Contacts;
import bw.black.entity.ProductImage;
import bw.black.enums.EnumAvailableStatus;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.ContactsRepository;
import bw.black.repository.ProductImageRepository;
import bw.black.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ProductImageRepository productImageRepository;
    private final ContactsRepository contactsRepository;

    @Override
    public RespStatus addImages(List<MultipartFile> files, Long contactsId) throws IOException {
        if (files.isEmpty()) {
            throw new ContactsException( "file is empty1", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        Contacts contacts = contactsRepository.findByIdAndActive(contactsId,EnumAvailableStatus.ACTIVE.getValue());
        if (contacts == null) {
            throw new ContactsException( "file is empty2", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if (fileName.contains("..")) {
                throw new ContactsException( "file is empty3", ExceptionConstant.INVALID_REQUEST_DATA);
            }
            ProductImage productImage = new ProductImage(fileName, file.getContentType(), file.getBytes(), contacts);
            productImageRepository.save(productImage);
        }
        return RespStatus.getSuccessMessage();
    }

    @Override
    public RespStatus deleteImagesByContactsId(Long contactsId) {
        Contacts contacts = contactsRepository.findByIdAndActive(contactsId,EnumAvailableStatus.ACTIVE.getValue());
        if (contacts == null) {
            throw new ContactsException( "file is empty", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        List<ProductImage> images = productImageRepository.findProductImageByContactsIdAndActive(contactsId, EnumAvailableStatus.ACTIVE.getValue());
        if (images.isEmpty()) {
            throw new ContactsException( "file is empty", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        productImageRepository.deactivateProductImagesByContactsId(contactsId);
        productImageRepository.saveAll(images);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public RespStatus deleteImage(Long imageId) {
        ProductImage productImage = productImageRepository.findProductImageByIdAndActive(imageId, EnumAvailableStatus.ACTIVE.getValue());
        if (productImage == null) {
            throw new ContactsException( "file is empty", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        productImage.setActive(EnumAvailableStatus.DEACTIVE.getValue());
        productImageRepository.save(productImage);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public List<ResponseEntity<byte[]>> getImages(Long contactsId) {
        List<ResponseEntity<byte[]>> response = new ArrayList<>();
        Contacts contacts = contactsRepository.findByIdAndActive(contactsId,EnumAvailableStatus.ACTIVE.getValue());
        if (contacts == null) {
            throw new ContactsException( "file is empty", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        List<ProductImage> productImages = productImageRepository.findProductImageByContactsIdAndActive(contactsId, EnumAvailableStatus.ACTIVE.getValue());
        for (ProductImage productImage: productImages){
            ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                    .contentType(MediaType.valueOf(productImage.getFileType()))
                    .body(productImage.getData());
            response.add(responseEntity);
        }
        return response;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long imageId) {
        ProductImage productImage = productImageRepository.findProductImageByIdAndActive(imageId,EnumAvailableStatus.ACTIVE.getValue());
        if (productImage == null){
            throw new ContactsException( "file is empty", ExceptionConstant.INVALID_REQUEST_DATA);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(productImage.getFileType()))
                .body(productImage.getData());
    }
}
