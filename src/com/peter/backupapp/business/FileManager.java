
package com.peter.backupapp.business;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public void saveToSdCard(String filename, byte[] buffer) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(buffer);
        } catch (FileNotFoundException e) {
            throw new IOException("file not found!", e);
        } catch (IOException e) {
            throw new IOException("write failure!", e);
        } finally {
            fos.close();
        }
    }

    public void copyFile(String from, String to) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] b = new byte[1024];

        try {
            fis = new FileInputStream(from);

            fos = new FileOutputStream(to);

            while (fis.read(b) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

}
