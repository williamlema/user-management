package com.user.management.app.constant;

import com.user.management.app.exception.NoSupportedFileException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SupportedFiles {
    CSV(".csv"),
    XLS(".xls"),
    XLSX(".xlsx"),
    TVS(".tvs");

    String extension;

    public static SupportedFiles isSupported(String filename){
        return Arrays.asList(SupportedFiles.values())
                .stream()
                .filter(supportedFiles -> filename.toLowerCase().contains(supportedFiles.getExtension()))
                .findFirst().orElseThrow(() -> new NoSupportedFileException("Archivo no soportado"));
    }
}
