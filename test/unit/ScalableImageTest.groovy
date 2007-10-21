import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class ScalableImageTest extends GroovyTestCase {

    def resourcePath = "test/resources/"
    def to = resourcePath + "scaled_test.jpg"
    def toFile = new File(to)
    ScalableImage img = new ScalableImage(resourcePath + "test.jpg")

    void assertImage() {
        assertImage(100,133)
    }

    void assertImage(int height, int width) {
        assert toFile.exists()

        BufferedImage read = ImageIO.read(toFile)
        
        assert read.height == height
        assert read.width == width
    }

    void testResizeWithGraphics() {
        img.resizeWithGraphics(to)
        assertImage()
    }

    void testResizeWithAffineTransform() {
        img.resizeWithAffineTransform(to, 0.5d)
        // Original image is 512x384
        assertImage(192, 256)
    }

    void testResizeWithScaledInstance() {
        img.resizeWithScaledInstance(to)
        assertImage()
    }

    void tearDown() {
        toFile.delete()
    }
}