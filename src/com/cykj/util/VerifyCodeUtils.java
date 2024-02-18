package com.cykj.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 *
 * <p>Title:VerifyCodeUtils </p>
 * <p>Description: 生成图片流核心代码块</p>
 * <p>Company: </p>
 * @author 小桃小涛
 * @date 2017年10月15日下午2:45:57
 */
public class VerifyCodeUtils {
    //去掉了1,0,i,o几个容易混淆的字符
    public static final String VERIFYCODES="23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static Random random = new Random();
    /**
     * @使用系统默认字符源生成验证码
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize){
        return generateVerifyCode(verifySize,VERIFYCODES);
    }
    /**
     * @使用指定源生成验证码
     * @param verifySize 验证码长度
     * @param sources   验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize,String sources){
        //判断是否有字符源
        if(sources==null||sources.length()==0){
            sources=VERIFYCODES;
        }
        int CodeLen = sources.length();
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for(int i=0;i<verifySize;i++){
            verifyCode.append(sources.charAt(random.nextInt(CodeLen-1)));
        }
        return verifyCode.toString();
    }
    /**
     * @生成随机验证码文件 并返回验证码值
     * @param width
     * @param height
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int width,int height,File outputFile,int verifySize) throws IOException{
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(width, height, outputFile, verifyCode);
        return verifyCode;
    }
    public static void outputImage(int width,int height,File outputFile, String code) throws IOException{
        if(outputFile==null){
            return;
        }
        File dir = outputFile.getParentFile();
        if(!dir.exists()){
            dir.mkdirs();
        }
        try{
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(width, height, fos, code);
            fos.close();
        }catch(IOException e){
            throw e;//手动抛出IOException 异常
        }
    }
    public static void outputImage(int width,int height,OutputStream os,String code) throws IOException{
        int verifySize = code.length();
        //创建一个画板
        BufferedImage buf =new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Random random = new Random();
        //创建一个绘图工具
        Graphics2D gs= buf.createGraphics();
        //消除线段的锯齿状边缘
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //创建颜色集合
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{
                Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY,
                Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW
        };
        Float[] fractions = new Float[colors.length];
        for(int i=0;i<colors.length;i++){
            colors[i]=colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i]=random.nextFloat();
        }
        Arrays.sort(fractions);//排序

        //设置边框色
        gs.setColor(Color.GRAY);
        gs.fillRect(0, 0, width, height);

        Color c= getRandomColor(200, 500);
        gs.setColor(c);
        gs.fillRect(0, 2, width, height-4);

        //绘制干扰线
        Random rand = new Random();
        gs.setColor(getRandomColor(160, 200));
        for(int i=0;i<20;i++){
            int x=rand.nextInt(width-1);
            int y= rand.nextInt(height-1);
            int x1=rand.nextInt(6)+1;
            int y1 = rand.nextInt(12)+1;
            gs.drawLine(x, y, x+x1+40, y+y1+20);
        }

        //添加噪点
        float yawpRate =0.05f;//噪点率
        int area = (int)(yawpRate*width*height);
        for(int i=0;i<area;i++){
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            int rgb = getRandomIntColor();
            buf.setRGB(x, y, rgb);
        }
        //使图片扭曲
        shear(gs,width,height,c);

        gs.setColor(getRandomColor(100, 160));
        int fontSize=height-4;
        Font font = new Font("Algerian",Font.ITALIC,fontSize);
        gs.setFont(font);
        char[] chars = code.toCharArray();
        for(int i=0;i<verifySize;i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (width/ verifySize) * i + fontSize/2, height/2);
            gs.setTransform(affine);
            gs.drawChars(chars, i, 1, ((width-10) / verifySize) * i + 5, height/2 + fontSize/2 - 10);
        }
        gs.dispose();
        ImageIO.write(buf, "JPEG", os);
    }
    /**
     * @rgb颜色的区域是0~255
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandomColor(int fc,int bc){
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
    private static int getRandomIntColor(){
        int[] rgb = getRandomRgb();
        int color=0;
        for (int c : rgb) {
            color=color<<8;
            color=color|c;
        }
        return color;
    }
    private static int[] getRandomRgb(){
        int[] rgb = new int[3];
        for(int i=0;i<3;i++){
            rgb[i]=random.nextInt(255);
        }
        return rgb;
    }
    private static void shear(Graphics g,int w ,int h,Color color){
        shearX(g,w,h,color);
        shearY(g,w,h,color);
    }
    private static void shearX(Graphics g,int w ,int h,Color color){
        int period = random.nextInt(2);

        boolean borderGap =true;
        int frames =1;
        int phase = random.nextInt(2);
        for(int i=0;i<h;i++){
            //???????
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w, 1, (int) d, 0);
            if(borderGap){
                g.setColor(color);
                g.drawLine((int)d, i, 0, i);
                g.drawLine((int)d+w, i, w, i);
            }
        }
    }
    private static void shearY(Graphics g, int w, int h, Color color) {
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h, i, h);
            }
        }
    }

    /// public static void main(String[] args){
    ///     File dir = new File("./webapps/static/images/verifycodes");
    ///     int width=200,height=80;
    ///     String verifyCode = generateVerifyCode(4);
    ///     File file = new File(dir, "vc" + ".jpg");
    ///     try {
    ///         outputImage(width, height, file, verifyCode);
    ///     } catch (IOException e) {
    ///         e.printStackTrace();
    ///     }
    /// }
}
