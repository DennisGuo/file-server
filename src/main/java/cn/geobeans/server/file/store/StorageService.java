package cn.geobeans.server.file.store;


import java.io.File;


public interface StorageService {

    String store(File file);

    String getRootLocation();

    File getByFilePath(String imagePath);
}
