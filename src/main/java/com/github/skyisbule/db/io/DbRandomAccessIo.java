package com.github.skyisbule.db.io;

import com.github.skyisbule.db.config.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

//底层io实现，归io线程管理，使用RandomAccessFile。
public class DbRandomAccessIo {

    private RandomAccessFile file;

    public DbRandomAccessIo(String fileName){
        try {
            String filePath = Config.DB_ROOT_PATH;
            file = new RandomAccessFile(filePath +File.pathSeparator+fileName, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param offset 从哪里开始读取
     * @param len    要读几个字节
     * @return       len个字节的byte[]
     */
    public byte[] read(int offset, int len) {
        byte[] data = new byte[len];
        try{
            file.seek(offset);
            file.read(data);
            return data;
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }

    }

    /**
     *
     * @param offset 从哪里开始读写
     * @param data   要写的内容
     * @return       是否成功
     */
    public boolean write(int offset,byte[] data) {
        try
        {
            file.seek(offset);
            file.write(data);
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLen() throws IOException {
        return (int)this.file.length();
    }

    private void close() throws IOException {
        this.file.close();
    }

}
