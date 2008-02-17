package org.grails.petstore

import java.awt.Image
import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION
import static java.awt.RenderingHints.KEY_ANTIALIASING
import static java.awt.RenderingHints.KEY_COLOR_RENDERING
import static java.awt.RenderingHints.KEY_DITHERING
import static java.awt.RenderingHints.KEY_FRACTIONALMETRICS
import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.KEY_RENDERING
import static java.awt.RenderingHints.KEY_STROKE_CONTROL
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY
import static java.awt.RenderingHints.VALUE_DITHER_ENABLE
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_ON
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY
import static java.awt.RenderingHints.VALUE_STROKE_NORMALIZE
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.apache.commons.io.FileUtils

class ImageStorageService {

    final allowedImageFormats = ["jpg","jpeg","gif","png"] //ImageIO.getReaderFileSuffixes() as List

    String imageDir = "web-app/images/"
    String categoryDir = imageDir + "category/"
    String productDir = imageDir + "product/"
    String thumbnailDir = imageDir + "item/thumbnail/"
    String uploadedDir = imageDir + "item/large/"

    static transactional = false

    void deleteImage(String path) {
        [thumbnailDir, uploadedDir].each {
            new File(it, path).delete()
        }
    }

    void createDirectories() {
        [categoryDir, productDir, thumbnailDir, uploadedDir].each {
            def dir = new File(it)
            dir.mkdirs()
        }

    }

    void clearDirectories() {
        [categoryDir, productDir, thumbnailDir, uploadedDir].each {
            def dir = new File(it)
            FileUtils.deleteDirectory(dir)
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

    // Private methods below

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

}
