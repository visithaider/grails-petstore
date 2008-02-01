package org.grails.petstore

import java.awt.*
import static java.awt.Color.GRAY
import static java.awt.Font.BOLD
import static java.awt.Font.ITALIC
import java.awt.image.BufferedImage
import java.awt.image.FilteredImageSource
import java.awt.image.ImageFilter
import java.awt.image.ImageProducer


class CaptchaService {

    static transactional = false

    Random rd = new Random()
    static final int WIDTH = 200
    static final int HEIGHT = 60
    Color background = new Color(0xc0c0c0)

    protected void drawMessage(Graphics g, String message) {
        g.font = new Font("Arial", BOLD | ITALIC, 30)
        g.color = GRAY
        int len = message.length()
        int wgap = WIDTH / len
        int startX = 10, startY = 20

        for (i in 0..len - 1) {
            g.drawString(message.substring(i, i + 1), startX + (wgap * i), startY + rd.nextInt(40))
        }
    }

    protected void drawRandomLine(Graphics g) {
        int x1 = rd.nextInt(200)
        int y1 = rd.nextInt(60)
        int x2 = rd.nextInt(200)
        int y2 = rd.nextInt(60)
        g.drawLine(x1, y1, x2, y2)
    }

    String generateCaptchaString(int count) {
        RandomString rs = new RandomString()
        rs.getString(count, "IiOo0")
    }

    BufferedImage getCaptchaImage(String message) {
        getCaptchaImage(message, WIDTH, HEIGHT)
    }

    BufferedImage getCaptchaImage(String message, int w, int h) {
        BufferedImage bufferImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
        BufferedImage lastBimg
        Graphics g, g2

        try {
            g = bufferImg.graphics
            g.color = background
            g.fillRect(0, 0, w, h)
            g.font = new Font("Arial", BOLD | ITALIC, 30)
            g.color = GRAY

            16.times { drawRandomLine(g) }
            drawMessage(g, message)

            ImageFilter filter = new BlueFilter()
            ImageProducer producer = new FilteredImageSource(bufferImg.source, filter)
            Image filteredImg = Toolkit.getDefaultToolkit().createImage(producer)

            lastBimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
            g2 = lastBimg.graphics
            g2.drawImage(filteredImg, 0, 0, null)
        } finally {
            g?.dispose()
            g2?.dispose()
        }
        return lastBimg
    }
}
