package com.anxuan.beadhouse.controller.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.ReturnValue;


@Controller
@RequestMapping("/system")
public class UploadController {
	@RequestMapping("/uploadFile")
	public void uploadFile(HttpServletRequest request, PrintWriter out) {
		String outPath = "/FileUpload/docFile/"+ CommUtil.formatTime("yyyyMMdd", new Date());
		String saveFilePathName = CommUtil.getRealPath(request, outPath);
		ReturnValue ret = new ReturnValue();
		try {
			Map uploadFile = CommUtil.saveFileToServer(request, "docfile",saveFilePathName, null, null);
			ret.setCode("failure");
			String fileName = uploadFile.get("fileName").toString();
			if (fileName != "" && fileName != null) {
				ret.setCode("success");
				ret.setData(outPath+"/"+fileName);
				ret.setMessage("上传成功!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/uploadVideo")
	public void uploadVideo(HttpServletRequest request, PrintWriter out) {
		String outPath = "/FileUpload/docFile/video/"+ CommUtil.formatTime("yyyyMMdd", new Date());
		String saveFilePathName = CommUtil.getRealPath(request, outPath);
		ReturnValue ret = new ReturnValue();
		try {
			Map uploadFile = CommUtil.saveFileToServer(request, "video",saveFilePathName, null, null);
			ret.setCode("failure");
			String fileName = uploadFile.get("fileName").toString();
			if (fileName != "" && fileName != null) {
				ret.setCode("success");
				ret.setData(outPath+"/"+fileName);
				ret.setMessage("上传成功!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}

}
