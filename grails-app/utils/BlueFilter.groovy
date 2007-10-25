import java.awt.image.RGBImageFilter;

class BlueFilter extends RGBImageFilter {

    BlueFilter() {
        canFilterIndexColorModel = true
    }

    @Override   
    int filterRGB(int x, int y, int rgb) {
        return Integer.valueOf(rgb | 0x000000FF)
    }

}
