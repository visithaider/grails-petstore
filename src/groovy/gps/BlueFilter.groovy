package gps

import java.awt.image.RGBImageFilter

class BlueFilter extends RGBImageFilter {

    BlueFilter() {
        // TODO: this fails sometimes, complaining that it can't cast Boolean to RGBImageFilter
        //canFilterIndexColorModel = true
    }

    @Override   
    int filterRGB(int x, int y, int rgb) {
        return Integer.valueOf(rgb | 0x000000FF)
    }

}
