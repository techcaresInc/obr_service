package org.techcaresinc.techcaresinc_obr_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.techcaresinc.techcaresinc_obr_service.entities.Certificate;
import org.techcaresinc.techcaresinc_obr_service.services.CertificateService;
import org.techcaresinc.techcaresinc_obr_service.services.FilePreview;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/")
public class CertificateController {

    @Autowired
    private CertificateService service;

/*
    @RequestMapping("/")
    public String root(Model model){

        Certificate certificate = new Certificate();
        model.addAttribute("certificate", certificate);

        return "index";
    }
*/
    /*.......................obr_get_service retrieve all db data.............................*/
    @RequestMapping(value = "/get_service", method = RequestMethod.GET)
    public ResponseEntity<?> get_service() {
        List<Certificate> certificates = service.findAll();

        if (certificates.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(certificates, HttpStatus.OK);
    }
    /*.......................obr_get_service_id retrieve specific db data.............................*/
    @RequestMapping(value = "/get_service_id/{id}", method = RequestMethod.GET)
    public ResponseEntity get_service_id(@PathVariable("id") long id){
        Certificate certificate = service.findById(id);

        if (certificate == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(certificate, HttpStatus.OK);
    }

    /*.......................obr_get_file_preview download db data.............................*/
    @RequestMapping(value = "/get_file_preview/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> get_file_preview(@PathVariable("id") long id, HttpServletResponse response){
        Certificate certificate = service.findById(id);

        if (certificate == null)
            return new ResponseEntity("no data found for"+certificate.getChild_name(), HttpStatus.NOT_FOUND);

        else {
            OutputStream stream = null;

            byte[] bytes =  new byte[certificate.getFathers_id().length];

            try {
                stream = new FileOutputStream(FilePreview.TempFile.tempFilePath(certificate));

                int i = 0;
                for (Byte aByte: certificate.getFathers_id()){
                    bytes[i++] = aByte.byteValue();
                }

                stream.write(bytes);
                stream.close();

                response.sendRedirect("/obr_service/get_file_download/"+id);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity("done"+certificate.getChild_name(), HttpStatus.OK);
    }

    /*.......................obr_get_file_download download db data.............................*/
    @RequestMapping(value = "/get_file_download/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> get_file_download(@PathVariable("id") Long id){
        String filename = FilePreview.TempFile.tempFilePath(service.findById(id));

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

        return new ResponseEntity("done"+service.findById(id).getChild_name(), HttpStatus.OK);
    }


    /*.......................obr_post_service insert db data.............................*/
    @RequestMapping(value = "/post_service", method = RequestMethod.POST)
    public ResponseEntity post_service(@Valid @RequestBody Certificate certificate
            , UriComponentsBuilder builder) {

        if (service.existsById(certificate.getId()))
            return new ResponseEntity("Record With Id No "+ certificate.getId() +"Already Exist"
                    , HttpStatus.CONFLICT);
        service.save(certificate);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/obr_service/get_service_id/{id}").
                buildAndExpand(certificate.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /*    .......................obr_put_file upload db data.............................*/
    @RequestMapping(value = "/put_file/{id}", method = RequestMethod.PUT
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> put_file(@PathVariable("id") long id
            , @RequestParam("file") MultipartFile file) {
        Certificate certificate = service.findById(id);

        if (certificate == null)
            return new ResponseEntity("Invalid Id No.", HttpStatus.NOT_FOUND);

        else {
            if (!file.isEmpty()) {
                try {
                    Byte[] bytes = new Byte[file.getBytes().length];

                    int i = 0;
                    for (Byte b : file.getBytes()) {
                        bytes[i++] = b;
                    }

                    certificate.setFathers_id(bytes);
                } catch (IOException  e) {
                    return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
                }

                service.update(certificate);
            }

            else
                return new ResponseEntity("kindly choose a file", HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity(certificate, HttpStatus.OK);
    }

    /*.......................obr_put_service update db data.............................*/
    @RequestMapping(value = "/put_service/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id,@Valid @RequestBody Certificate certificate) {
        Certificate currentCertificate = service.findById(id);

        if (currentCertificate == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        currentCertificate.setChild_name(certificate.getChild_name());
        currentCertificate.setSex(certificate.getSex());
        currentCertificate.setAge(certificate.getAge());
        currentCertificate.setFathers_name(certificate.getFathers_name());

        service.update(currentCertificate);

        return new ResponseEntity(currentCertificate, HttpStatus.OK);
    }

    /*.......................obr_delete_service update db data.............................*/
    @RequestMapping(value = "/delete_service/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        Certificate certificate = service.findById(id);

        if (certificate == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        service.deleteById(id);

        return new ResponseEntity(certificate,HttpStatus.NO_CONTENT);
    }

    /*.......................obr_delete_all_service update db data.............................*/
    @RequestMapping(value = "/delete_all_service", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUsers() {
        service.deleteAll();

        return new ResponseEntity("db data erased", HttpStatus.NO_CONTENT);
    }
}
