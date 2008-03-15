import java.awt.Image
import static java.awt.RenderingHints.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.servlet.ServletContext
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.web.context.ServletContextAware

class ImageStorageService implements ServletContextAware, InitializingBean, DisposableBean {

    static transactional = false

    List allowedImageFormats = ["jpg","jpeg","gif","png"].asImmutable()
    // Better, but Java 6 only: ImageIO.getReaderFileSuffixes() as List

    ServletContext servletContext
    String categoryDir, productDir, thumbnailDir, uploadedDir

    @Override
    void afterPropertiesSet() {
        configureDirectories()
        createDirectories()
    }

    @Override
    void destroy() {
        clearDirectories()
    }

    void deleteImage(String path) {
        [thumbnailDir, uploadedDir].each {
            new File(it, path).delete()
        }
    }

    void storeCategoryImage(String name, byte[] imageData) {
        writeFileToDisk(imageData, categoryDir, name)
    }

    void storeProductImage(String name, byte[] imageData) {
        writeFileToDisk(imageData, productDir, name)
    }

    String storeUploadedImage(byte[] imageData, String contentType) {
        def format = getFormat(contentType)
        def newName = getRandomName() + "." + format

        def image = toImage(imageData)

        def large = scaleImage(image, 400, 300)
        def small = scaleImage(image, 133, 100)

        writeFileToDisk(toByteArray(large, format), uploadedDir, newName)
        writeFileToDisk(toByteArray(small, format), thumbnailDir, newName)

        return newName
    }

    /* Private methods below */

    private String getRandomName() {
        new RandomString().getStringFromLong()
    }

    private String getFormat(String contentType) {
        def format = contentType.minus("image/").toLowerCase()
        if (format in allowedImageFormats) {
            return format
        } else {
            throw new IllegalArgumentException("Content type " + format + " is not supported, only " + allowedImageFormats)
        }

    }

    private Image scaleImage(Image image, int width, int height) {
        def scaled = new BufferedImage(width, height, image.type)
        def g = scaled.createGraphics()
        g.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY)
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        g.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY)
        g.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE)
        g.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON)
        g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR)
        g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY)
        g.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_NORMALIZE)
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        g.drawImage(image, 0, 0, width, height, null)

        return scaled
    }

    private void writeFileToDisk(byte[] imageData, String dir, String name) {
        def image = new File(dir, name)
        FileUtils.writeByteArrayToFile(image, imageData)
    }

    private byte[] toByteArray(Image image, String format) {
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, format, baos)
        baos.toByteArray()
    }

    private Image toImage(byte[] imageData) {
        ImageIO.read(new ByteArrayInputStream(imageData))
    }

    private Image toImage(String path) {
        ImageIO.read(new File(path))
    }

    private void configureDirectories() {
        def imagesDir = servletContext.getRealPath("images/")
        categoryDir = imagesDir + "category/"
        productDir = imagesDir + "product/"
        thumbnailDir = imagesDir + "item/thumbnail/"
        uploadedDir = imagesDir + "item/large/"
    }

    private void createDirectories() {
        [categoryDir, productDir, thumbnailDir, uploadedDir].each {
            new File(it).mkdirs()
        }
    }

    private void clearDirectories() {
        [categoryDir, productDir, thumbnailDir, uploadedDir].each {
            FileUtils.forceDeleteOnExit(new File(it))
        }
    }

}
