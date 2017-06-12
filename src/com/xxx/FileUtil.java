package com.xxx;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

public class FileUtil {

	/**
	 * 由路径解析出文件后缀
	 * 
	 * @param path
	 * @return path为空或是文件夹时返回空串
	 */
	public static String parseFileSuffix(String path) {
		if (path != null && path.length() > 0) {
			int dotIndex = path.lastIndexOf(".");
			if (dotIndex > 0) {
				return path.substring(dotIndex + 1);
			}
		}
		return "";
	}

	public static File getLastestFile(File[] files) {
		if (files == null) {
			return null;
		} else {
			return getLastestFile(Arrays.asList(files));
		}
	}

	public static File getLastestFile(List<File> files) {
		File lastestFile = null;
		if (files != null && files.size() > 0) {
			Collections.sort(files, new LastestFileComparator());
			lastestFile = files.get(0);
		}
		return lastestFile;
	}

	/** 较新文件比较器 */
	public static class LastestFileComparator implements Comparator<File> {
		@Override
		public int compare(File lhs, File rhs) {
			return lhs.lastModified() < rhs.lastModified() ? 1 : -1;
		}
	}

	public static class PictureFileSelector implements FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return false;
			} else {
				String suffix = parseFileSuffix(f.getName()).toLowerCase(Locale.getDefault());
				return !suffix.equals("svn") && (suffix.equals("png") || suffix.equals("jpg") || suffix.equals("gif")
						|| suffix.equals("jpeg") || suffix.equals("bmp"));
			}
		}
	}

	/**
	 * copy 文件的方法
	 * 
	 * @param src
	 *            源文件路径 D:\\test.txt
	 * @param des
	 *            cop 新文件路径 D:\\testnew.txt
	 * @author huxiaolong
	 */
	@SuppressWarnings("unused")
	private static void copy(String src, String des) throws IOException {
		File sFile = new File(src);
		File dFile = new File(des);
		if (!dFile.exists()) {
			dFile.createNewFile();
		}
		fileCopy(sFile, dFile); // 调用文件拷贝的方法
	}

	/**
	 * 文件拷贝的方法
	 */
	private static void fileCopy(File sFile, File dFile) {
		try {
			FileInputStream fis = new FileInputStream(sFile);
			FileOutputStream fout = new FileOutputStream(dFile);
			byte[] date = new byte[1024]; // 创建字节数组
			int rs = -1;
			while ((rs = fis.read(date)) > 0) { // 循环读取文件
				fout.write(date, 0, rs); // 向文件写数据
			}
			fout.close();
			fis.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Base64字符串转换成Bitmap
	 * 
	 * @return
	 */
	public static boolean base64ToImage(String content, String filePath) {
		if (content == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(content);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(URLEncoder.encode("系统管理员"));
	}
}
