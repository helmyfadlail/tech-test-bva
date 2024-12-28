package helmyfadlail.technicaltestbva.util;

import org.springframework.http.HttpStatus;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUploadUtil {

    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public static void assertAllowed(MultipartFile file) {
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max file size is 2MB.");
        }
        final String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only image files are allowed.");
        }
    }

}
