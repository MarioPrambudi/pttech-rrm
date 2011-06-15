package com.djavafactory.pttech.rrm.util;


import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    static final int BUFFER = 2048;

    public static File writeToFile(String filename, String content) {
        File outFile = new File(filename);
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF8"));
            output.write(content);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public static String readFromFile(String filename) {
        int ch;
        StringBuffer strContent = new StringBuffer("");
        try {
            Reader in = new InputStreamReader(new FileInputStream(filename), "UTF-8");
            while( (ch = in.read()) != -1) {
                strContent.append((char)ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strContent.toString();
    }

    public static void zipFile(String zipFilename, String[] filesToZip) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFilename);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            for(String file : filesToZip) {
                origin = new BufferedInputStream(new FileInputStream(file), BUFFER);
                ZipEntry entry = new ZipEntry(file);
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0,
                  BUFFER)) != -1) {
                   out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
