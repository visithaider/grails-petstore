import org.springframework.web.multipart.MultipartFile

class ImageStorageService {

    List imageSuffixes = ["jpeg","jpg","png","gif"]
    static String uploadedDir = "web-app/images/uploaded/"
    static String thumbnailDir = "web-app/images/scaled/"

    String storeUploadedImage(MultipartFile file) {

        // Deduce suffix from content type
        String ct = file.contentType.minus("image/").toLowerCase()
        String suffix
        if (ct in imageSuffixes) {
            suffix = "." + ct
        } else {
            throw new IllegalArgumentException("Content type " + ct + " is not supported")
        }

        // Store under new, random name
        String newName = new RandomString().getStringFromLong() + suffix

        // Store the uploaded image
        String uploadedPath = uploadedDir + newName
        file.transferTo(new File(uploadedPath))

        // Scale and store thumbnail
        String thumbnailPath = thumbnailDir + newName
        ScalableImage scaled = new ScalableImage(uploadedPath)
        scaled.keepAspect()
        scaled.resizeWithGraphics(thumbnailPath)

        File f = new File(uploadedPath)
        println "Stored images at " + f.absolutePath
        assert f.exists() 

        return newName
    }

}