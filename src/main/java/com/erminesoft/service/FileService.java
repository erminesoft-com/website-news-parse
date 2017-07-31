package com.erminesoft.service;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     *@param idWebSite
     * Create excel file on disk(in temp directory)
     * @return String(name file)
     */
    String createFile(long idWebSite);

    /**
     *@param fileName
     * Download file with name and delete excel file on disk(in temp directory)
     */
    void downloadFile(String fileName, HttpServletResponse response);
}
