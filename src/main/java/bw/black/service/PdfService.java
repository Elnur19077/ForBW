package bw.black.service;

import bw.black.entity.Contacts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public interface PdfService {


    ByteArrayInputStream generateContactsPdf(Contacts contact);

}
