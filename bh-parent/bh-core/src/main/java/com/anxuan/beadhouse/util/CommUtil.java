package com.anxuan.beadhouse.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class CommUtil {
	public static final int PAGE_COUNT = 2;
	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
		String url = "http://" + request.getServerName();
		if (null2Int(Integer.valueOf(request.getServerPort())) != 80)
			url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
		else {
			url = url + contextPath;
		}
		return url;
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static double null2Double(Object s) {
		double v = 0.0;
		if (s != null)
			try {
				v = Integer.decode(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static float null2Float(Object s) {
		float v = 0.0F;
		if (s != null)
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	public static String[] splitByChar(String s, String c) {
		String[] list = s.split(c);
		return list;
	}

	public static int indexOf(String s, String c) {
		int i = -1;
		try {
			i = s.indexOf(c);
		} catch (Exception e) {
			e.printStackTrace();
			i = -1;
		}
		return i;
	}

	public static Long null2Long(Object s) {
		Long v = Long.valueOf(-1L);
		if (s != null)
			try {
				v = Long.valueOf(Long.parseLong(s.toString()));
			} catch (Exception localException) {
			}
		return v;
	}

	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null)
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static String SubStringToChat(Object str, String chart) {
		List<String> strList = new ArrayList<String>();
		String tempStr = null2String(str);
		if (!"".equals(tempStr) && tempStr != null) {
			for (int i = 0, W = tempStr.length(); i < W; i++) {
				strList.add(tempStr.charAt(i) + "");
			}
		}
		return StringUtils.join(strList, chart);
	}

	/**
	 * 
	 * @param request
	 * @param filePath
	 *            (文件地址)
	 * @param saveFilePathName
	 *            (保存文件地址)
	 * @param saveFileName
	 *            (保存文件名字)
	 * @param extendes
	 *            (文件格式)
	 * @return
	 * @throws IOException
	 */
	public static Map saveFileToServer(HttpServletRequest request, String filePath, String saveFilePathName,
			String saveFileName, String[] extendes) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(filePath);
		Map map = new HashMap();
		if (multipartFile != null && (!multipartFile.isEmpty())) {
			// 获取文件后缀
			String suffix = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			if (saveFileName == null || saveFileName.trim().equals("")) {
				saveFileName = UUID.randomUUID().toString() + "." + suffix;
			}
			if (saveFileName.lastIndexOf(".") < 0) {
				saveFileName = saveFileName + "." + suffix;
			}
			float fileSize = Float.valueOf((float) multipartFile.getSize()).floatValue();
			List errors = new ArrayList();
			boolean flag = true;
			if (extendes != null) {
				for (String s : extendes) {
					if (suffix.toLowerCase().equals(s))
						flag = true;
				}
			}
			if (flag) {
				File path = new File(saveFilePathName);
				if (!path.exists()) {
					path.mkdirs();
				}
			}
			String saveFile = UUID.randomUUID().toString() + suffix;// 构建文件名称
			try {
				File path = new File(saveFilePathName + File.separator + saveFile);
				multipartFile.transferTo(path);
				if (isImg(suffix)) {
					File img = new File(saveFilePathName + File.separator + saveFileName);
					BufferedImage bis = ImageIO.read(img);
					int w = bis.getWidth();
					int h = bis.getHeight();
					map.put("width", Integer.valueOf(w));
					map.put("height", Integer.valueOf(h));
				}
				map.put("code", "success");
				map.put("fileName", saveFile);
				map.put("fileSize", Float.valueOf(0.0F));
				map.put("message", "上传成功!");
			} catch (Exception e) {
				map.put("width", Integer.valueOf(0));
				map.put("height", Integer.valueOf(0));
				map.put("fileName", "");
				map.put("fileSize", Float.valueOf(0.0F));
				map.put("message", e.getMessage());
			}
		} else {
			map.put("width", Integer.valueOf(0));
			map.put("height", Integer.valueOf(0));
			map.put("code", "failure");
			map.put("fileName", "");
			map.put("fileSize", Float.valueOf(0.0F));
			map.put("message", "上传失败");
		}

		return map;
	}

	public static boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = new ArrayList();
		list.add("jpg");
		list.add("jpeg");
		list.add("bmp");
		list.add("gif");
		list.add("png");
		list.add("tif");
		for (String s : list) {
			if (s.equals(extend))
				ret = true;
		}
		return ret;
	}

	public static String formatTime(String format, Object v) {
		if (v == null)
			return null;
		if (v.equals(""))
			return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	public static String getRealPath(HttpServletRequest request, String fileName) {
		String path = request.getSession().getServletContext().getRealPath("/");
		return path + fileName;
	}

	public static Map saveVideoFileToserver(HttpServletRequest request, String filePath, String saveFilePathName) {
		Map modelMap = new HashMap();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		/** 构建视频保存的目录 **/
		String filePathName = File.separator + dateformat.format(new Date());
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(saveFilePathName + filePathName);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile(filePath);
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename()
				.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		/** 使用UUID生成文件名称 **/
		filePathName += File.separator + UUID.randomUUID().toString() + suffix;// 构建文件名称
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = filePathName;
		File file = new File(saveFilePathName + fileName);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelMap.put("fileName", fileName);
		return modelMap;
	}

	public static int Int2Time(String time) {
		int timeInt = 0;
		if (time != null) {
			String timeStr[] = splitByChar(time, ":");
			int hour = null2Int(timeStr[0]);
			int cent = null2Int(timeStr[1]);
			int min = null2Int(timeStr[2]);
			timeInt = (hour * 3600) + (cent * 60) + min;
		}
		return timeInt;
	}

	// 导入2003 xls文件
	public static List readXls(File file) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		int length = hwb.getActiveSheetIndex();// 判断有几张活动的sheet表
		List mapList = new ArrayList();
		for (int k = 0; k <= length; k++) {
			HSSFSheet sheet = hwb.getSheetAt(k);
			HSSFRow row = null;
			HSSFCell cell = null;
			Map hashmap = new HashMap();
			hashmap.put("typeName", sheet.getSheetName());
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					hashmap.put(j, cell);
				}
				mapList.add(hashmap);
			}
		}
		return mapList;
	}

	// 导入2007 xlsx文件
	public static List readXlsx(File file) throws IOException {
		// 购置XXSFWorkbook对象，传入file文件
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一张表格内容
		int length = xwb.getActiveSheetIndex();
		int num = 0;
		List mapList = new ArrayList();
		for (int k = 0; k <= length; k++) {
			XSSFSheet sheet = xwb.getSheetAt(k);
			Object value = null;
			XSSFRow row = null;
			XSSFCell cell = null;
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				num += 1;
				Map hashmap = new HashMap();
				hashmap.put("typeName" + num, sheet.getSheetName());
				if (row == null) {
					continue;
				}
				for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					hashmap.put("problem" + j, cell);
				}
				mapList.add(hashmap);

			}
		}
		return mapList;
	}

	public static Date stringToDate(String str) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static List<String> getPageToPage(int zPage, int mPage) {
		List<String> pageToPageList = new ArrayList<String>();
		int num = 1;
		for (int i = 1, L = zPage; i <= L; i++) {
			String active = num == 1 ? "active" : "";
			if (L <= mPage) {
				num += 1;
				pageToPageList.add("<li class='" + active + "' s_page=" + i + " e_page=" + L + "  onclick='getProblem("
						+ i + ");'>" + i + "~" + L + "题</li>");
				break;
			} else if (i % mPage == 0) {
				num += 1;
				pageToPageList.add("<li class='" + active + "' s_page=" + (i - (mPage - 1)) + " e_page=" + i
						+ " onclick='getProblem(" + (i - (mPage - 1)) + ",\"xq\");'>" + (i - (mPage - 1)) + "~" + i
						+ "题</li>");
			} else if ((num * mPage) > L) {
				pageToPageList.add("<li class='" + active + "' s_page=" + ((L / mPage) * +1) + " e_page=" + L
						+ "  onclick='getProblem(" + ((L / mPage) * mPage + 1) + ",\"xq\");'>"
						+ ((L / mPage) * mPage + 1) + "~" + L + "题</li>");
				break;
			}
		}
		return pageToPageList;
	}

	public static int getTimeTosecond(String time) {
		int timeInt = 0;
		if (time != null) {
			String timeStr[] = splitByChar(time, ":");
			int hour = null2Int(timeStr[0]);
			int cent = null2Int(timeStr[1]);
			timeInt = (hour * 60) + (cent);
		}
		return timeInt;
	}

	public static String longToString(long currentTime, String formatType) throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType) throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType) throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	public static double div(Object a, Object b) {
		double ret = 0.0D;
		if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
			BigDecimal e = new BigDecimal(null2String(a));
			BigDecimal f = new BigDecimal(null2String(b));
			if (null2Double(f) > 0.0D)
				ret = e.divide(f, 3, 1).doubleValue();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double subtract(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.subtract(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double add(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.add(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double mul(Object a, Object b) {
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		double ret = e.multiply(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double formatMoney(Object money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(money)).doubleValue();
	}

	public static String addZeroForNum(int number, int num) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(num);
		formatter.setGroupingUsed(true);
		return formatter.format(number);
	}

	public static int getAge(Date birthDate) {
		if (birthDate == null)
			throw new RuntimeException("出生日期不能为null");
		int age = 0;
		Date now = new Date();
		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");
		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);
		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);
		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);
		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}


	public static Date getLastDayOfMonth(String hltime) {
		Calendar cal = Calendar.getInstance();
		int year = CommUtil.null2Int(hltime.substring(0, 4));
		int month = CommUtil.null2Int(hltime.substring(4));
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return cal.getTime();
	}
	public static Date getBirthDate(int year){
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.YEAR, year);
		 return cal.getTime();
	}
	public static void main(String[] args) {
		System.out.println(CommUtil.dateToString(getBirthDate(-120), "yyyy-MM-dd"));
	}
}
