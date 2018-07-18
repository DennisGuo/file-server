package cn.geobeans.server.file.store;



import cn.geobeans.server.file.Application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class FileSystemStorageService implements StorageService {


    private final String rootLocation;

    public FileSystemStorageService() {
        this.rootLocation = Application.PATH +"/data";
    }

    @Override
    public String store(File file) {
//        try {
//            if (file.isEmpty()) {
//                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
//            }
//            String oName = file.getOriginalFilename();
//
//            String[] arr = oName.split("\\.");
//            String type = arr[arr.length-1];
//
//            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//            String dir = String.format("%s/%s/", rootLocation, date);
//            String name = String.format("%s.%s",UUID.randomUUID().toString().replace("-",""),type);
//
//            if(!new File(dir).exists()){
//                Files.createDirectories(Paths.get(dir));
//            }
//
//            //Files.copy(file.getInputStream(), Paths.get(dir+name));
//
//            file.transferTo(new File(dir+name));
//
//            log.info("store file to : " + dir+name);
//
//            return "/"+date+"/"+name;
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
//        }
        return null;
    }

    @Override
    public String getRootLocation() {
        return rootLocation;
    }

    @Override
    public File getByFilePath(String imagePath) {
        return new File(rootLocation+imagePath);
    }
}