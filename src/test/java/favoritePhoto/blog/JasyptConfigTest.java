package favoritePhoto.blog;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

    @Test
    void jasypt() {
        String url = "secret";
        String username = "secret";
        String password = "secret";
        String s3Bucket = "secret";
        String s3AccessKey = "secret";
        String s3SecretKey = "secret";

        System.out.println("dbUrl = " + jasyptEncoding(url));
        System.out.println("username = " + jasyptEncoding(username));
        System.out.println("password = " + jasyptEncoding(password));
        System.out.println("s3Bucket = " + jasyptEncoding(s3Bucket));
        System.out.println("s3AccessKey = " + jasyptEncoding(s3AccessKey));
        System.out.println("s3SecretKey = " + jasyptEncoding(s3SecretKey));

    }

    public String jasyptEncoding(String value) {

        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
