package favoritePhoto.blog.service.file;

import com.amazonaws.services.s3.model.CannedAccessControlList;
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

    private String localLocation = "/Users/cho/Desktop/blog/src/main/resources/upload";

    public String imageUpload(MultipartRequest request) throws IOException {

        MultipartFile file = request.getFile("upload");

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);


        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        localFile.delete();

        return s3Url;
    }

//    public String imageUpload(MultipartRequest request) throws IOException {
//        MultipartFile file = request.getFile("upload");
//
//        String fileName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
//        String uuidFileName = UUID.randomUUID().toString() + fileName;
//        String localPath = "/path/to/save/images/" + uuidFileName;
//
//        // 리사이징된 이미지를 임시 파일로 저장
//        File resizedFile = resizeImage(file, localPath, 800, 600);
//
//        // S3에 업로드
//        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, resizedFile).withCannedAcl(CannedAccessControlList.PublicRead));
//
//        // 임시 파일 삭제
//        resizedFile.delete();
//
//        return s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();
//    }
//
//    private File resizeImage(MultipartFile file, String destinationPath, int width, int height) throws IOException {
//        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
//
//        // 리사이징
//        BufferedImage resizedImage = new BufferedImage(width, height, bufferedImage.getType());
//        resizedImage.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
//
//        File resizedFile = new File(destinationPath);
//        ImageIO.write(resizedImage, "jpg", resizedFile);
//
//        return resizedFile;
//    }

}
