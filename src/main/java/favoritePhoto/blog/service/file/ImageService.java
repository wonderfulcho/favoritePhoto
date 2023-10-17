package favoritePhoto.blog.service.file;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import favoritePhoto.blog.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    private S3Config s3Config;

    @Autowired
    public ImageService(S3Config s3Config) {
        this.s3Config = s3Config;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String imageUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;

        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        s3Config.amazonS3Client().putObject(bucket, uuidFileName, inputStream, metadata);
        s3Config.amazonS3Client().setObjectAcl(bucket, uuidFileName, CannedAccessControlList.PublicRead);

        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        return s3Url;
    }
}
