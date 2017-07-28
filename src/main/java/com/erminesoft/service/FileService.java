package com.erminesoft.service;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    String createFile(long id);
    void downloadFile(String fileName, HttpServletResponse response);
}
