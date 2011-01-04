package com.tomovwgti.lib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BugReport {

	String model; // モデル名
	String setting; // 選択した設定
	List<String> trace = new ArrayList<String>(); // Stack Trace
	Result result;

	String remoteAddr;
	Date createdAt = new Date();

	public static enum Result {
		OK, NG
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the setting
	 */
	public String getSetting() {
		return setting;
	}

	/**
	 * @param setting
	 *            the setting to set
	 */
	public void setSetting(String setting) {
		this.setting = setting;
	}

	/**
	 * @return the trace
	 */
	public List<String> getTrace() {
		return trace;
	}

	/**
	 * @param trace
	 *            the trace to set
	 */
	public void setTrace(List<String> trace) {
		this.trace = trace;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the remoteAddr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * @param remoteAddr
	 *            the remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}