package com.erminesoft.service;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.model.WebSite;
import com.erminesoft.repository.WebsiteRepository;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final static String EXTENSION = ".xlsx";
    private static final String CONTENT_TYPE = "application/force-download";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final String BASE_TEMP_PATH = System.getProperty("java.io.tmpdir");

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private ParserService parserService;

    @Override
    public String createFile(long id) {
        WebSite webSite = websiteRepository.findOne(id);
        List<ArticleDto> list = parserService.getListArticlesById(id);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("News");
        XSSFRow header = sheet.createRow(0);
        CreationHelper createHelper = workbook.getCreationHelper();
        header.createCell(0).setCellValue("#");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Link");
        header.createCell(3).setCellValue("Image");
        header.createCell(4).setCellValue("Desc");
        header.createCell(5).setCellValue("Time");

        list.forEach( articleDto -> {
            XSSFRow aRow = sheet.createRow(articleDto.getId());
            sheet.autoSizeColumn(articleDto.getId());
            aRow.createCell(0).setCellValue(articleDto.getId());
            aRow.createCell(1).setCellValue(articleDto.getTitle());

            XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
            link.setAddress(articleDto.getLink());
            aRow.createCell(2).setHyperlink(link);
            aRow.createCell(2).setCellValue(articleDto.getLink());

            XSSFHyperlink linkImage = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
            linkImage.setAddress(articleDto.getImageUrl());
            aRow.createCell(3).setHyperlink(linkImage);
            aRow.createCell(3).setCellValue(articleDto.getImageUrl());

            aRow.createCell(4).setCellValue(articleDto.getDescription());
            aRow.createCell(5).setCellValue(articleDto.getDate());
        });

        String fileName = webSite.getName().concat("_").concat(String.valueOf(new Date().getTime())).concat(EXTENSION);

        try {
            FileOutputStream outputStream = new FileOutputStream(BASE_TEMP_PATH +  "/" + fileName);
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            logger.warn("Error saving file, {}", e.getLocalizedMessage());
        } catch (IOException e) {
            logger.warn("Error during saving excel file, {}", e.getLocalizedMessage());
        }
        logger.info("Leaving createExcelFile()");
        return fileName;
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse response) {
        logger.info("Entering getFile() fileName = {}", fileName);
        File fileToDownload = null;
        try {
            fileToDownload = new File(BASE_TEMP_PATH + "/" + fileName);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType(CONTENT_TYPE);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT + fileName);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Deleting file {}", fileToDownload);
        boolean result = false;
        try {
            if (fileToDownload != null) {
                result = Files.deleteIfExists(fileToDownload.toPath());
            }
        } catch (IOException e) {
            logger.warn("Error deleting file - {}", fileName);
        }
        logger.info("Leaving getFile(), delete file = {}", result);
    }
}
