package org.techcaresinc.techcaresinc_obr_service.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.techcaresinc.techcaresinc_obr_service.entities.Certificate;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePreview {

    public static class TempFile{
        public static String tempFilePath (Certificate certificate){
            return System.getProperty("java.io.tmpdir")+"//"+certificate.getChild_name()+"fathersid.pdf";
        }
    }

    public ResponseEntity preview(CertificateService service, Long id){
        Certificate certificate = service.findById(id);

        if (certificate == null)
            return new ResponseEntity("no data found for"+certificate.getChild_name(), HttpStatus.NOT_FOUND);

        else {
            String filename = TempFile.tempFilePath(certificate);

            Resource resource = null;

            try {
                resource = new UrlResource(Paths.get(filename).toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if (resource.exists()){
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "found")
                        .body(resource);

            }
        }

        return new ResponseEntity("done"+certificate.getChild_name(), HttpStatus.NOT_FOUND);
    }
}
