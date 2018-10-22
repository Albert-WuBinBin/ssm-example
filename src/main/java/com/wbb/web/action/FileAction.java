package com.wbb.web.action;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wbb.web.utils.UploadUtils;

@RequestMapping("/file")
@RestController
public class FileAction {

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public String importExcel(@RequestParam(value = "file", required = false) MultipartFile uploadFile)
			throws IOException {
		File target = UploadUtils.upLoadFile(uploadFile);// 文件上传
		UploadUtils.analysisExcel(target);
		return "upload success";
	}
}
