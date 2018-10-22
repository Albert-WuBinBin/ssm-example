package com.wbb.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

public class UploadUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);

	/**
	 * 文件上传
	 * 
	 * @param uploadFile
	 * @throws IOException
	 */
	public static File upLoadFile(MultipartFile uploadFile) throws IOException {
		String uploadPath = "E://";
		File uploadFlie = new File(uploadPath);
		if (!uploadFlie.exists()) {
			uploadFlie.mkdir();
		}
		MultipartFile file = uploadFile;
		String uploadFileName = file.getOriginalFilename();
		InputStream isRef = file.getInputStream();
		File targetFile = new File(uploadPath, uploadFileName);
		FileOutputStream fosRef = new FileOutputStream(targetFile);
		IOUtils.copy(isRef, fosRef);
		return targetFile;
	}

	/**
	 * 解析excel
	 * 
	 * @param target
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void analysisExcel(File target) throws IOException {
		try {
			InputStream excelFile = new FileInputStream(target);// 输入流
			HSSFWorkbook wb = new HSSFWorkbook(excelFile);// 得到Excel工作簿对象
			final Sheet sheet = wb.getSheetAt(0);// 得到Excel工作表对象
			final HSSFSheet hssfSheet = wb.getSheetAt(0);
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					try {
						readPictures(hssfSheet);
					} catch (IOException e) {
						logger.error("e", e);
					}
				}
			});
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					try {
						readData(sheet);
					} catch (Exception e) {
						logger.error("e", e);
					}
				}
			});
		} catch (Exception e) {
			logger.error("e", e);
		}
	}

	/**
	 * 读取excel内容
	 * 
	 * @param sheet
	 */
	public static void readData(Sheet sheet) {
		try {
			String name = "", phone = "";
			int rowNum = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowNum; i++) {// 从第二行开始读取数据,第一行是表头
				try {
					Row row = sheet.getRow(i);// 得到Excel工作表的行
					int cellNum = row.getLastCellNum();// 取得一行的有效单元格个数
					for (int j = 0; j < cellNum; j++) {
						Cell cell = row.getCell(j);// 得到Excel工作表指定行的单元格
						String cellValue = null;
						if (cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							/*
							 * CELL_TYPE_NUMERIC 数值型 0 CELL_TYPE_STRING 字符串型 1
							 * CELL_TYPE_FORMULA 公式型 2 CELL_TYPE_BLANK 空值 3
							 * CELL_TYPE_BOOLEAN 布尔型 4 CELL_TYPE_ERROR 错误 5
							 */
							switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0:
								cellValue = String.valueOf((long) cell.getNumericCellValue());
								break;
							case 1:
								cellValue = cell.getStringCellValue();
								break;
							case 2:
								cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case 3:
								cellValue = cell.getStringCellValue();
								break;
							case 4:
								cellValue = cell.getStringCellValue();
								break;
							}

							switch (j) {// 通过列数来判断对应插如的字段
							case 0:
								name = cellValue;
								break;
							case 1:
								phone = cellValue;
								break;
							}
						}
					}
					logger.info("第{}行,姓名:{},手机号:{}", i, name, phone);
				} catch (Exception e) {
					logger.error("e", e);
				}
			}
		} catch (Exception e) {
			logger.error("e", e);
		}
	}

	/**
	 * 获得excel中所有图片
	 * 
	 * @param sheet
	 * @throws IOException
	 */
	public static void readPictures(HSSFSheet sheet) throws IOException {
		try {
			List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
			for (HSSFShape shape : list) {
				if (shape instanceof HSSFPicture) {
					HSSFPicture picture = (HSSFPicture) shape;
					HSSFPictureData pdata = picture.getPictureData();
					String extension = pdata.suggestFileExtension();
					GenerateImage(byteToBase64String(pdata.getData()),  ((HSSFPicture) shape).getFileName(), extension);
				}
			}
		} catch (Exception e) {
			logger.error("e", e);
		}
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToBase64String(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * 生成图片
	 * 
	 * @param imgStr
	 * @param name
	 *            图片名
	 * @param extension
	 *            后缀、扩展名
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String name, String extension) {
		try {
			if (imgStr == null)
				return false;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				String path = "E://";
				String imgFilePath = path + name + "." + extension;// 新生成的图片
				logger.info("生成的图片:{}", imgFilePath);
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception e) {
			logger.error("e", e);
		}
		return false;
	}
}
