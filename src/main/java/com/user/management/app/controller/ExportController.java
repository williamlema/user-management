package com.user.management.app.controller;

import com.user.management.app.service.api.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.user.management.app.constant.Resource.*;

@RestController
@RequestMapping(EXPORT)
public class ExportController {

    private final IFileService fileService;

    @Autowired
    public ExportController(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(EXPORT_USER_XSLX)
    public ResponseEntity<InputStreamResource> exportUserInXlsxFile(
           ) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(this.fileService.createExcelFile());
    }

    @GetMapping(EXPORT_USER_XSL)
    public ResponseEntity<InputStreamResource> exportUserInXlsFile() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xls");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(this.fileService.createExcelFile());
    }

    @GetMapping(EXPORT_USER_CSV)
    public ResponseEntity<InputStreamResource> exportUserInCsvFile() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.csv");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(this.fileService.createCsvFile());
    }

}
