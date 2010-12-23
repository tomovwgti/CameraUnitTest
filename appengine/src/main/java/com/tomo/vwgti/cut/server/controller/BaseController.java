package com.tomo.vwgti.cut.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.text.StrBuilder;
import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;
import org.slim3.util.AppEngineUtil;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreTimeoutException;
import com.google.apphosting.api.ApiProxy.ApplicationException;
import com.google.apphosting.api.ApiProxy.CapabilityDisabledException;
import com.google.apphosting.api.DeadlineExceededException;
import com.tomo.vwgti.cut.server.util.JsonUtil;

public abstract class BaseController extends SimpleController {
	static final Logger logger = Logger.getLogger(BaseController.class
			.getName());

	private String requestBody = null;

	@Override
	final protected Navigation run() throws Exception {

		requestBody = getString(request.getInputStream());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Navigation navigation = doResponse();
		if (navigation != null) {
			return navigation;
		}
		response.flushBuffer();
		return null;
	}

	/**
	 * このメソッドを実装して、ClientへのレスポンスをJSONで返してください。
	 * 
	 * @return {@link Navigation}
	 * @throws Exception
	 */
	abstract Navigation doResponse() throws Exception;

	protected void responseSucceed() throws IOException {
		PrintWriter writer = response.getWriter();
		writer.println("{");
		writer.println("\"status\":\"OK\",");
		writer.print("\"body\":");
		writer.println("{}");
		writer.println("}");
	}

	protected void responseSucceed(CharSequence responseJson)
			throws IOException {
		PrintWriter writer = response.getWriter();
		writer.println("{");
		writer.println("\"status\":\"OK\",");
		writer.print("\"body\":");
		writer.println(responseJson);
		writer.println("}");
	}

	@Override
	protected Navigation handleError(Throwable error) throws Throwable {
		// とりあえずサーバ実行時のみ（JUnit実行は対象外）出力するように修正した。
		if (AppEngineUtil.isServer()) {
			error.printStackTrace();
		}
		if (error instanceof ApplicationException) {
			responseError("NG", error.toString(), error.getMessage());
		} else if (error instanceof CapabilityDisabledException) {
			responseError("APPENGINE", "READONLY", "AppEngineのサービスが読み取り専用です。");
		} else if (error instanceof DatastoreTimeoutException) {
			responseError("APPENGINE", "DSTIMEOUT", "データストアがタイムアウトしました。");
		} else if (error instanceof DatastoreFailureException) {
			responseError("APPENGINE", "DSFAILURE", "データストアのアクセスに失敗しました。");
		} else if (error instanceof DeadlineExceededException) {
			responseError("APPENGINE", "DLE", "30秒を超えても処理が終了しませんでした。");
		} else {
			logger.log(Level.WARNING, "unexpected error.", error);
			responseError("ERR", error.toString(), error.getMessage());
		}
		return null;
	}

	void responseError(String status, String errorCode, String errorMessage)
			throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.println("{");
		writer.println("\"status\":\"" + status + "\",");
		writer.println("\"errorCode\":\"" + JsonUtil.sanitizeJson(errorCode)
				+ "\",");
		writer.println("\"errorMessage\":\""
				+ JsonUtil.sanitizeJson(errorMessage) + "\"");
		writer.println("}");
		response.flushBuffer();
	}

	String getString(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StrBuilder builder = new StrBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line).append("\n");
		}
		return builder.toString();
	}

	/**
	 * @return the requestBody
	 */
	public String getRequestBody() {
		return requestBody;
	}
}
