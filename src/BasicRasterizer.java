// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class BasicRasterizer extends SubNode {

    public static void initialize(int outheight, int outwidth, int out[]) {
        pixelBuffer = out;
        bufferWidth = outwidth;
        bufferHeight = outheight;
        setDimensions(outwidth, 0, outheight, 0);
    }

    public static void reset() {
        widthOffset = 0;
        heightOffset = 0;
        width = bufferWidth;
        height = bufferHeight;
        widthLength = width - 1;
        widthCenter = width / 2;
    }

    public static void setDimensions(int w, int wOffset, int h, int hOffset) {
        if(wOffset < 0)
            wOffset = 0;
        if(hOffset < 0)
            hOffset = 0;
        if(w > bufferWidth)
            w = bufferWidth;
        if(h > bufferHeight)
            h = bufferHeight;
        widthOffset = wOffset;
        heightOffset = hOffset;
        width = w;
        height = h;
        widthLength = width - 1;
        widthCenter = width / 2;
        heightCenter = height / 2;
    }

    public static void resetPixelBuffer() {
        int i = bufferWidth * bufferHeight;
        for(int j = 0; j < i; j++)
            pixelBuffer[j] = 0;
    }

    public static void drawQuad(int x, int y, int w, int h, int alpha, int color)
    {
        if(x < widthOffset)
        {
            w -= widthOffset - x;
            x = widthOffset;
        }
        if(y < heightOffset)
        {
            h -= heightOffset - y;
            y = heightOffset;
        }
        if(x + w > width)
            w = w - x;
        if(y + h > height)
            h = h - y;
        int outalpha = 256 - alpha;
        int red = (color >> 16 & 0xff) * alpha;
        int green = (color >> 8 & 0xff) * alpha;
        int blue = (color & 0xff) * alpha;
        int widthincrement = bufferWidth - w;
        int offset = x + y * bufferWidth;
        for(int i = 0; i < h; i++)
        {
            for(int j = -w; j < 0; j++)
            {
                int outred = (pixelBuffer[offset] >> 16 & 0xff) * outalpha;
                int outgreen = (pixelBuffer[offset] >> 8 & 0xff) * outalpha;
                int outblue = (pixelBuffer[offset] & 0xff) * outalpha;
                int result = ((red + outred >> 8) << 16) + ((green + outgreen >> 8) << 8) + (blue + outblue >> 8);
                pixelBuffer[offset++] = result;
            }
            offset += widthincrement;
        }
    }

    public static void drawQuad(int x, int y, int w, int h, int color)
    {
        if(x < widthOffset) {
            w -= widthOffset - x;
            x = widthOffset;
        }
        if(y < heightOffset) {
            h -= heightOffset - y;
            y = heightOffset;
        }
        if(x + w > width)
            w = width - x;
        if(y + h > height)
            h = height - y;
        int widthincrement = bufferWidth - w;
        int offset = x + y * bufferWidth;
        for(int i = -h; i < 0; i++)  {
            for(int j = -w; j < 0; j++)
                pixelBuffer[offset++] = color;
            offset += widthincrement;
        }
    }

    public static void drawQuadrilateralOutline(int x, int y, int width, int height, int color) {
        drawHorizontalLine(x, y, width, color);
        drawHorizontalLine(x, (y + height) - 1, width, color);
        drawVerticalLine(x, y, height, color);
        drawVerticalLine((x + width) - 1,y, height, color);
    }

    public static void drawQuadOutline(int x,int y, int width, int height, int alpha, int color) {
        drawVerticalLine(x, y, width, alpha, color);
        drawVerticalLine(x, (y + height) - 1, width, alpha, color);
        if(height >= 3) {
            drawHorizontalLine(x, y + 1, height - 2, alpha, color);
            drawHorizontalLine((x + width) - 1, y + 1, height - 2, alpha, color);
        }
    }

    public static void drawHorizontalLine(int x,int y, int len, int color) {
        if(y < heightOffset || y >= height)
            return;
        if(x < widthOffset) {
            len -= widthOffset - x;
            x = widthOffset;
        }
        if(x + len > width)
            len = width - x;
        int offset = x + y * bufferWidth;
        for(int o = 0; o < len; o++)
            pixelBuffer[offset + o] = color;
    }

    public static void drawVerticalLine(int x, int y, int len, int alpha, int color)
    {
        if(y < heightOffset || y >= height)
            return;
        if(x < widthOffset) {
            len -= widthOffset - x;
            x = widthOffset;
        }
        if(x + len > width)
            len = width - x;
        int outalpha = 256 - alpha;
        int red = (color >> 16 & 0xff) * alpha;
        int green = (color >> 8 & 0xff) * alpha;
        int blue = (color & 0xff) * alpha;
        int offset = x + y * bufferWidth;
        for(int i = 0; i < len; i++) {
            int outred = (pixelBuffer[offset] >> 16 & 0xff) * outalpha;
            int outgreen = (pixelBuffer[offset] >> 8 & 0xff) * outalpha;
            int outblue = (pixelBuffer[offset] & 0xff) * outalpha;
            int result = ((red + outred >> 8) << 16) + ((green + outgreen >> 8) << 8) + (blue + outblue >> 8);
            pixelBuffer[offset++] = result;
        }
    }

    public static void drawVerticalLine(int x,int y, int length, int color) {
        if(x < widthOffset || x >= width)
            return;
        if(y < heightOffset)
        {
            length -= heightOffset - y;
            y = heightOffset;
        }
        if(y + length > height)
            length = height - y;
        int offset = x + y * bufferWidth;
        for(int o = 0; o < length; o++)
            pixelBuffer[offset + (o * bufferWidth)] = color;
    }

    public static void drawHorizontalLine(int x, int y, int length, int alpha, int color) {
        if(x < widthOffset || x >= width)
            return;
        if(y < heightOffset)
        {
            length -= heightOffset - y;
            y = heightOffset;
        }
        if(y + length > height)
            length = height - y;
        int outalpha = 256 - alpha;
        int red = (color >> 16 & 0xff) * alpha;
        int green = (color >> 8 & 0xff) * alpha;
        int blue = (color & 0xff) * alpha;
        int offset = x + y * bufferWidth;
        for(int j3 = 0; j3 < length; j3++)
        {
            int outred = (pixelBuffer[offset] >> 16 & 0xff) * outalpha;
            int outgreen = (pixelBuffer[offset] >> 8 & 0xff) * outalpha;
            int outblue = (pixelBuffer[offset] & 0xff) * outalpha;
            int result = ((red + outred >> 8) << 16) + ((green + outgreen >> 8) << 8) + (blue + outblue >> 8);
            pixelBuffer[offset] = result;
            offset += bufferWidth;
        }
    }

    public BasicRasterizer() {
    }
    
    public static int pixelBuffer[];
    public static int bufferWidth;
    public static int bufferHeight;
    public static int heightOffset;
    public static int height;
    public static int widthOffset;
    public static int width;
    public static int widthLength;
    public static int widthCenter;
    public static int heightCenter;
}
