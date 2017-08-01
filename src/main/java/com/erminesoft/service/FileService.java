package com.erminesoft.service;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     *Create excel file and save on disk(in temp directory)
     *
     * @param idWebSite
     * @return String(name file)
     */
    String createFile(long idWebSite);

    /**
     * Download file by name and delete this file from disk(in temp directory)
     *
     * @param fileName
     */
    void downloadFile(String fileName, HttpServletResponse response);
}
