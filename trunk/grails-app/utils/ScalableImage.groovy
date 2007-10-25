import java.awt.Graphics2D
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
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class ScalableImage {
	
    int thumbWidth = 133, thumbHeight = 100
    String format = "jpg"
    BufferedImage image
    File file

    ScalableImage(String imagePath) {
        image = ImageIO.read(new File(imagePath))
    }

    ScalableImage(int width, int height, String imagePath) {
        this(imagePath)
        this.thumbWidth = width
        this.thumbHeight = height
    }

    void keepAspect() {
        keepAspectWithWidth()
        keepAspectWithHeight()
    }

    void keepAspectWithWidth() {
        thumbHeight = image.height * thumbWidth / image.width
    }

    void keepAspectWithHeight() {
        thumbWidth = image.width * thumbHeight / image.height
    }

    void resizeWithScaledInstance(String to) {
        BufferedImage bThumb = new BufferedImage(thumbWidth, thumbHeight, image.type)
        bThumb.graphics.drawImage(
            image.getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_AREA_AVERAGING),
            0, 0, thumbWidth, thumbHeight, null)
        file = new File(to)
        ImageIO.write(bThumb, format, file)
    }

    void resizeWithGraphics(String to) {
        BufferedImage th = new BufferedImage(thumbWidth, thumbHeight, image.type)
        Graphics2D g2d = th.createGraphics()
        /* Problem with static import :-(
        g2d.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY)
        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY)
        g2d.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE)
        g2d.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON)
        g2d.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR)
        g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY)
        g2d.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_NORMALIZE)
        g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        */
        g2d.drawImage(image, 0, 0, thumbWidth, thumbHeight, null)
        file = new File(to)
        ImageIO.write(th, format, file)
    }

    void resizeWithAffineTransform(String to, double power) {
        BufferedImage th = new BufferedImage(
            (int) (image.width * power), (int)(image.height * power), image.type)
        double powerW = ((double) thumbWidth) / image.width
        double powerH = ((double) thumbHeight) / image.height
        AffineTransformOp op = new AffineTransformOp(
            AffineTransform.getScaleInstance(powerW, powerH),
            AffineTransformOp.TYPE_BILINEAR);
        op.filter(image, th)
        file = new File(to)
        ImageIO.write(th, format, file)
    }

}

