import org.apache.commons.io.FileUtils
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class ImageStorageService {

    static final allowedImageFormats = ["jpeg","jpg","png","gif"].asImmutable()
    static final String imageDir = "web-app/images/"
    static final String categoryDir = imageDir + "category/"
    static final String productDir = imageDir + "product/"
    static final String thumbnailDir = imageDir + "item/thumbnail/"
    static final String uploadedDir = imageDir + "item/large/"

    static transactional = false
    static {
        [categoryDir, productDir, thumbnailDir, uploadedDir].each {
            new File(it).mkdirs()
        }
    }

    boolean deleteImage(String path) {
        [thumbnailDir, uploadedDir].each {
            new File(it, path).delete()
        }
    }

    void storeCategoryImage(String name, String format, byte[] imageData) {
        def image = new File(categoryDir, name + "." + format)
        FileUtils.writeByteArrayToFile(image, imageData)
    }

    void storeProductImage(String name, String format, byte[] imageData) {
        def image = new File(productDir, name + "." + format)
        FileUtils.writeByteArrayToFile(image, imageData)
    }

    String storeUploadedImage(byte[] imageData, String contentType) {
        assert imageData && imageData.length > 0
        assert contentType
        
        ScalableImage original = new ScalableImage(imageData)
        original.contentType = contentType

        // Set a new, random name
        String newName = new RandomString().getStringFromLong()
        original.name = newName

        // Store the uploaded image TODO: scale even the large image to fixed size
        File uploadedFile = new File(uploadedDir, newName)
        FileUtils.writeByteArrayToFile(uploadedFile, imageData)
        assert uploadedFile.exists()

        // Scale and store thumbnail
        String thumbnailPath = thumbnailDir + newName
        ScalableImage scaled = new ScalableImage(imageData)
        scaled.contentType = contentType
        scaled.keepAspectWithHeight()
        scaled.resizeWithGraphics(thumbnailPath)
        assert scaled.file.exists()

        return original.name
    }

}