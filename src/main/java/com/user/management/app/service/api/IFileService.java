package com.user.management.app.service.api;

import org.springframework.core.io.InputStreamResource;

import java.io.IOException;

/**
 * file operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IFileService {

    /**
     * Create excel file to download
     *
     * @return
     */
    InputStreamResource createExcelFile() throws IOException;


    /**
     * Create cvs file to download
     *
     * @return
     */
    InputStreamResource createCsvFile();
}
