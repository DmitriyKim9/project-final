package com.javarush.jira.bugtracking.attachment;

import com.javarush.jira.common.error.IllegalRequestDataException;
import com.javarush.jira.common.error.NotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@UtilityClass
public class FileUtil {
    private static final String ATTACHMENT_PATH = "./attachments/%s/";
    private static final int BUFFER_SIZE = 256;

    public static void upload(MultipartFile multipartFile, String directoryPath, String fileName) {
        if (multipartFile.isEmpty()) {
            throw new IllegalRequestDataException("Select a file to upload.");
        }

        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            }
            catch (IOException e) {
                throw new IllegalRequestDataException("Failed to create directory " + path.getFileName());
            }
        }
        Path filePath = Paths.get(directoryPath + fileName);
        try(FileChannel ch = (FileChannel) Files.newByteChannel(filePath, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            byte[] bytes = multipartFile.getBytes();
            for (byte b:bytes) {
                for(int i = 0; i < BUFFER_SIZE; i++)
                    buffer.put(b);
                buffer.rewind();
                ch.write(buffer);
            }
        }
        catch (IOException e){
            throw new IllegalRequestDataException("Failed to upload file" + multipartFile.getOriginalFilename());
        }
    }

    public static Resource download(String fileLink) {
        Path path = Paths.get(fileLink);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new IllegalRequestDataException("Failed to download file " + resource.getFilename());
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException("File" + fileLink + " not found");
        }
    }

    public static void delete(String fileLink) {
        Path path = Paths.get(fileLink);
        try {
            Files.delete(path);
        } catch (IOException ex) {
            throw new IllegalRequestDataException("File" + fileLink + " deletion failed.");
        }
    }

    public static String getPath(String titleType) {
        return String.format(ATTACHMENT_PATH, titleType.toLowerCase());
    }
}
