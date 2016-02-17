package top.wangjun.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理
 */
public class Image {

    public static int DEFAULT_SPACING = 20;

    private File input;
    private BufferedImage originImage;
    private BufferedImage bufferedImage;
    private String filename;
    private int imageType;
    private int width;
    private int height;

    public Image(File input) throws IOException {
        this.input = input;
        this.filename = input.getName();
        this.originImage = ImageIO.read(input);
        this.bufferedImage = this.originImage;
        this.imageType = originImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originImage.getType();
        this.width = originImage.getWidth();
        this.height = originImage.getHeight();

    }

    public Image(String filename) throws IOException {
        File input = new File(filename);
        this.input = input;
        this.filename = input.getName();
        this.originImage = ImageIO.read(input);
        this.bufferedImage = this.originImage;
        this.imageType = originImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originImage.getType();
        this.width = originImage.getWidth();
        this.height = originImage.getHeight();
    }

    /**
     * 缩放
     * @param width
     * @param height
     * @return
     */
    public Image resize(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, this.imageType);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(this.bufferedImage, 0, 0, width, height, null);
        graphics.dispose();
        this.bufferedImage = image;
        return this;
    }

    /**
     * 剪裁: 坐标, 宽高
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public Image crop(int x, int y, int width, int height) throws IOException {
        this.bufferedImage = this.bufferedImage.getSubimage(x, y, width, height);
        return this;
    }

    /**
     * 添加文字水印, 默认位置右下角
     * @param text
     * @return
     */
    public Image watermark(String text) {
        return this.watermark(text, WatermarkPosition.RIGHT_BOTTOM);
    }

    /**
     * 添加水印
     * @param text
     * @param position
     * @return
     */
    public Image watermark(String text, WatermarkPosition position) {
        Graphics2D graphics = this.bufferedImage.createGraphics();

        //set font
        Font font = new Font("Microsoft YaHei", Font.PLAIN, 16);
        graphics.setFont(font);

        // calculates the coordinate where the String is painted
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, graphics);

        Point point = this.calculatePosition(
                this.bufferedImage.getWidth(),
                this.bufferedImage.getHeight(),
                (int) rect.getWidth(),
                (int) rect.getHeight(),
                position);

        //draw shadow
        TextLayout textLayout = new TextLayout(text, font, graphics.getFontRenderContext());
        graphics.setColor(Color.DARK_GRAY);
        textLayout.draw(graphics, point.x + 1, point.y + 1);

        //draw text
        graphics.setColor(Color.WHITE);
        textLayout.draw(graphics, point.x, point.y);

        graphics.dispose();
        return this;
    }

    /**
     * 创建合并图片的区域
     * @param width
     * @param height
     * @return
     */
    public Image createMergeArea(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, this.imageType);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(this.bufferedImage, 0, 0, width, height, null);
        graphics.dispose();
        this.bufferedImage = image;
        return this;
    }

    /**
     * 合并图片
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public Image merge(Image image, int x, int y, int width, int height) {
        Graphics2D graphics = this.bufferedImage.createGraphics();
        graphics.drawImage(image.getBufferedImage(), x, y, width, height, null);

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, x, height);
        graphics.fillRect(x + width, 0, this.bufferedImage.getWidth() - x - width, height);
        graphics.fillRect(0, height, this.bufferedImage.getWidth(), 1);

        graphics.dispose();
        return this;
    }

    /**
     * 填充色块
     * @param color
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public Image fillRect(Color color, int x, int y, int width, int height) {
        Graphics2D graphics = this.bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
        graphics.dispose();
        return this;
    }

    public void write(String path) throws IOException {
        File output = new File(path);
        this.write(output);
    }

    public void write(File file) throws IOException {
		if(!file.getParentFile().exists()) {
			file.mkdirs();
		}
		Graphics2D graphics = this.bufferedImage.createGraphics();
		graphics.setComposite(AlphaComposite.Src);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIO.write(this.bufferedImage, this.getImageExtension(), file);
        this.bufferedImage = this.originImage;
    }

    /**
     * 获取拓展名
     * @return
     */
    public String getImageExtension() {
        if(filename.length() > 0 && filename.lastIndexOf(".") > -1) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * 计算水印坐标
     * @param imageWidth
     * @param imageHeight
     * @param textWidth
     * @param textHeight
     * @param position
     * @return
     */
    private Point calculatePosition(int imageWidth, int imageHeight, int textWidth, int textHeight, WatermarkPosition position) {
        int x = (imageWidth - textWidth) / 2;
        int y = (imageHeight - textHeight) / 2;

        if(WatermarkPosition.CENTER.equals(position)) {
            y = (imageHeight + textHeight) / 2;
        }

        if(WatermarkPosition.RIGHT_BOTTOM.equals(position)) {
            if(x > DEFAULT_SPACING) {
                x = imageWidth - textWidth - DEFAULT_SPACING;
            } else {
                x = imageWidth - textWidth - x;
            }

            if(y > DEFAULT_SPACING) {
                y = imageHeight - DEFAULT_SPACING;
            } else {
                y = imageHeight - y;
            }
        }

        if(WatermarkPosition.RIGHT_TOP.equals(position)) {
            if(x > DEFAULT_SPACING) {
                x = imageWidth - textWidth - DEFAULT_SPACING;
            } else {
                x = imageWidth - textWidth - x;
            }

            if(y > DEFAULT_SPACING) {
                y = DEFAULT_SPACING + textHeight / 2;
            }
        }

        if(WatermarkPosition.LEFT_BOTTOM.equals(position)) {
            if(x > DEFAULT_SPACING) {
                x = DEFAULT_SPACING;
            }

            if(y > DEFAULT_SPACING) {
                y = imageHeight - DEFAULT_SPACING;
            } else {
                y = imageHeight - y;
            }
        }

        if(WatermarkPosition.LEFT_TOP.equals(position)) {
            if(x > DEFAULT_SPACING) {
                x = DEFAULT_SPACING;
            }

            if(y > DEFAULT_SPACING) {
                y = DEFAULT_SPACING + textHeight / 2;
            }
        }
        return new Point(x, y);
    }

    private static class Point {
        public int x;
        public int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public BufferedImage getOriginImage() {
        return originImage;
    }

    public void setOriginImage(BufferedImage originImage) {
        this.originImage = originImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}