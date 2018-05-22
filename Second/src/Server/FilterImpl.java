package Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

public class FilterImpl implements Filter {
    private float[] data;
    private int height;
    private int width;

    protected FilterImpl() throws RemoteException {}

    public FilterImpl(int height, int width) {
        this.data = new float[height * width];
        this.height = height;
        this.width = width;
    }

    public FilterImpl(String filename) throws IOException {
        loadImageFromFile(filename);
    }

    @Override
    public void saveImageToFile(String filename, String format) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int value = clamp(getValue(i, j));
                int rgb = 0;
                rgb = rgb | (value << 16);
                rgb = rgb | (value << 8);
                rgb = rgb | value;
                image.setRGB(j, i, rgb);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ImageIO.write(image, format, fos);
        }
    }

    @Override
    public void loadImageFromFile(String filename) throws IOException {
        BufferedImage image;
        try (FileInputStream fis = new FileInputStream(filename)) {
            image = ImageIO.read(fis);
        }
        height = image.getHeight();
        width = image.getWidth();
        data = new float[height * width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int rgb = image.getRGB(j, i);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                setValue(i, j, 0.3f * r + 0.6f * g + 0.1f * b);//(взяли с цоса у ибаса, спасибо ибасу) переводим в чб, чтоб было легче преобразовывать, если в цветной для каждого цвета нужен свой массив :(
            }
        }
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getValue(int i, int j) {
        if (i < 0) {
            i = 0;
        }
        if (j < 0) {
            j = 0;
        }
        if (i > height-1) {
            i = height-1;
        }
        if (j > width-1) {
            j = width-1;
        }
        return data[i * width + j];
    }

    public void setValue(int i, int j, float value) {
        data[i * width + j] = value;
    }

    private int clamp(float value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return Math.round(value);
    }

    @Override
    public void filter() throws RemoteException {
        FilterImpl h = new FilterImpl(3, 3);
        for (int i = 0; i < h.getHeight(); i++) {
            for (int j = 0; j < h.getWidth(); j++) {
                h.setValue(i, j, (float) (1.0/9.0));
            }
        }
        this.setData(conv(this, h).getData());
    }

    private FilterImpl conv(FilterImpl f, FilterImpl h) throws RemoteException {//ибас молодец, свертку по цосу тоже писали :3
        FilterImpl g = new FilterImpl(f.getHeight(), f.getWidth());
        for (int i = 0; i < g.getHeight(); i++) {
            for (int j = 0; j < g.getWidth(); j++) {
                float sum = 0;
                for (int m = 0; m < h.getHeight(); m++) {
                    for (int n = 0; n < h.getWidth(); n++) {
                        int hy = h.getHeight()-1-m;
                        int hx = h.getWidth()-1-n;
                        int fy = i-(h.getHeight()/2)+m;
                        int fx = j-(h.getWidth()/2)+n;
                        float hxy = h.getValue(hy, hx);
                        float fxy = f.getValue(fy, fx);
                        sum += hxy * fxy;
                    }
                }
                g.setValue(i, j, sum);
            }
        }
        return g;
    }
}
