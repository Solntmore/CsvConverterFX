package com.example.csvconverterfx;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

public class Converter {

    private String inDirectory;
    private String outDirectory;

    public String getOutDirectory() {
        return outDirectory;
    }

    public void setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
    }

    public void setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
    }

    public void convertXlsToCsv() {
        try {
            FileOutputStream fos = new FileOutputStream(outDirectory + "\\output.txt");

            XSSFWorkbook wBook = new XSSFWorkbook(
                    new FileInputStream(inDirectory));

            XSSFSheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;

            Iterator<Row> rowIterator = sheet.iterator();
            StringBuilder data = new StringBuilder("1,КИЗ,");

            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case BOOLEAN:
                            data.append("\"").append(cell.getBooleanCellValue()).append("\"").append(",");

                            break;
                        case NUMERIC:
                            data.append("\"").append(cell.getNumericCellValue()).append("\"").append(",");

                            break;
                        case STRING:
                            data.append("\"").append(cell.getStringCellValue()).append("\"").append(",");
                            break;

                        case BLANK:
                            data.append("\"").append("\"").append(",");
                            break;
                        default:
                            data.append("\"").append(cell).append("\"").append(",");
                    }
                }
            }

            fos.write(data.substring(0, data.length() - 1).getBytes());
            fos.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
