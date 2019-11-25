package com.user.management.app.service.impl;

import com.user.management.app.model.entity.User;
import com.user.management.app.repository.UserRepository;
import com.user.management.app.service.api.IFileService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FileServiceImpl implements IFileService {

    private final UserRepository userRepository;

    @Autowired
    public FileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create excel file to download
     *
     * @return
     */
    @Override
    public InputStreamResource createExcelFile() throws IOException {
        List<User> allUser = this.userRepository.findAll();
        String [] columns = {"Nombre","Rol","Username","Email","Celular"};
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");
        Row headers = sheet.createRow(0);
        AtomicInteger pos = new AtomicInteger();
        Arrays.stream(columns).forEach(s -> {
            Cell celda = headers.createCell(pos.get());
            celda.setCellValue(s);
            pos.getAndIncrement();
        });
        pos.set(1);
        allUser.stream().forEach(user -> {
            Row row = sheet.createRow(pos.get());
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getRol().getDescription());
            row.createCell(2).setCellValue(user.getUserName());
            row.createCell(3).setCellValue(user.getEmail());
            row.createCell(4).setCellValue(user.getPhoneNumber());
            pos.getAndIncrement();
        });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);

        return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    }

    /**
     * Create cvs file to download
     *
     * @return
     */
    @Override
    public InputStreamResource createCsvFile() {
        AtomicReference<String> file = new AtomicReference<>("Nombre," + "Rol," + "Username," + "Email," + "Celular\n");
        List<User> allUser = this.userRepository.findAll();
        allUser.stream().forEach(user -> {
            file.set(file.get().concat(user.getName()).concat(",")
                    .concat(user.getRol().getDescription()).concat(",")
                    .concat(user.getUserName()).concat(",")
                    .concat(user.getEmail()).concat(",")
                    .concat(user.getPhoneNumber()).concat("\n"));
        });
        return  new InputStreamResource(new ByteArrayInputStream(file.get().getBytes()));
    }
}
