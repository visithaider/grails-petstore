import org.springframework.web.multipart.MultipartFile

class ImageStorageService {

    List allowedImageFormats = ["jpeg","jpg","png","gif"]
    static String uploadedDir = "web-app/images/uploaded/"
    static String thumbnailDir = "web-app/images/scaled/"

    static transactional = false

    boolean deleteImage(String path) {
        [uploadedDir, thumbnailDir].every {
            new File(it + path).delete()
        }
    }

    String storeUploadedImage(MultipartFile file) {
        assert file != null

        // Deduce suffix from content type
        String ct = file.contentType.minus("image/").toLowerCase()
        String suffix
        if (ct in allowedImageFormats) {
            suffix = "." + ct
        } else {
            throw new IllegalArgumentException("Content type " + ct + " is not supported, only " + allowedImageFormats)
        }

        // Store under new, random name
        String newName = new RandomString().getStringFromLong() + suffix

        // Store the uploaded image
        String uploadedPath = uploadedDir + newName
        File uploadedFile = new File(uploadedPath)
        file.transferTo(uploadedFile)

        // Scale and store thumbnail
        String thumbnailPath = thumbnailDir + newName
        ScalableImage scaled = new ScalableImage(uploadedPath)
        scaled.keepAspect()
        scaled.resizeWithGraphics(thumbnailPath)

        assert uploadedFile.exists()
        assert scaled.file.exists()

        return newName
    }

}