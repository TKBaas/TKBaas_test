package com.tk.baas.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.UUID;

import javax.imageio.ImageIO;


public class ImageUtil {
	/**
	 * 将图片改变大小后存放到硬盘
	 * @param width 改变后的宽度
	 * @param height 改变后的高度
	 * @param oldPath 原图片的目录
	 * @param oldName 原图片的名字
	 * @param newPath 新图片的目录
	 * @return 返回新图片的名字
	 */
	public static String alterPictureSize(int width, int height, 
								 String oldPath, String oldName, String newPath) 
								 throws Exception{
		int index = oldName.lastIndexOf(".");
		if(index < 0){//图片格式有误
			return null;
		}
		String newName = UUID.randomUUID()+oldName.substring(index);
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		Image src = ImageIO.read(new File(oldPath, oldName));
		g.drawImage(src, 0, 0, width, width, null);
		ImageIO.write(img, "jpeg", new File(newPath, newName));
		return newName;
	}
	
	/**
	 * 测试图片的放大缩小
	 */
	public static void main(String[] args) throws Exception, SQLException {
		String newName = alterPictureSize(50, 50, "F:\\photo\\图片素材", "1.jpg", "F:\\photo");
		System.out.println(newName);
	}
}
