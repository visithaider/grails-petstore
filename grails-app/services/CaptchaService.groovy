import java.awt.Color
import static java.awt.Color.GRAY
import java.awt.Font
import static java.awt.Font.BOLD
import static java.awt.Font.ITALIC
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.awt.image.FilteredImageSource

class CaptchaService {

    static transactional = false
    static scope = "session"

    Random rd = new Random()
    int width = 200, height = 60
    Color background = new Color(0xc0c0c0)
    String captchaString

    boolean validateCaptchaString(String captcha) {
        this.captchaString == captcha?.trim()
    }

    BufferedImage generateCaptchaImage() {
        setNewCaptchaString()

        def bufferImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        def lastBimg, g, g2

        try {
            g = bufferImg.graphics
            g.color = background
            g.fillRect(0, 0, width, height)
            g.font = new Font("Arial", BOLD | ITALIC, 30)
            g.color = GRAY

            16.times { drawRandomLine(g) }
            drawMessage(g)

            def filter = new BlueFilter()
            def producer = new FilteredImageSource(bufferImg.source, filter)
            def filteredImg = Toolkit.getDefaultToolkit().createImage(producer)

            lastBimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            g2 = lastBimg.graphics
            g2.drawImage(filteredImg, 0, 0, null)
        } finally {
            g?.dispose()
            g2?.dispose()
        }
        return lastBimg
    }

    private void setNewCaptchaString() {
        captchaString = RandomString.getString(6, "IiOo0")
    }

    private boolean isValidCaptchaString(String s) {
        captchaString == s?.trim()
    }

    private void drawMessage(Graphics g) {
        g.font = new Font("Arial", BOLD | ITALIC, 30)
        g.color = GRAY
        int len = captchaString.length()
        int wgap = width / len
        int startX = 10, startY = 20

        for (i in 0..len - 1) {
            g.drawString(captchaString[i], startX + (wgap * i), startY + rd.nextInt(40))
        }
    }

    private void drawRandomLine(Graphics g) {
        int x1 = rd.nextInt(200)
        int y1 = rd.nextInt(60)
        int x2 = rd.nextInt(200)
        int y2 = rd.nextInt(60)
        g.drawLine(x1, y1, x2, y2)
    }

}
