package com.tomovwgti.cut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class MyApplication extends Application {

	private static File BUG_REPORT_FILE = null;
	static {
		File errorDir = new File(Environment.getExternalStorageDirectory(),
				"/CameraUnitTest/ExceptionLog/");
		if (!errorDir.exists()) {
			if (!errorDir.mkdirs()) {
			}
		}
		BUG_REPORT_FILE = new File(errorDir, String.valueOf(System
				.currentTimeMillis()) + ".log");
	}

	/**
	 * {@inheritDoc}<br>
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			UncaughtExceptionHandler defaultHandler;
			{
				defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			}

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				try {
					File file = BUG_REPORT_FILE;
					PrintWriter pw = null;
					pw = new PrintWriter(new FileOutputStream(file));
					saveState(ex, pw);
					pw.flush();
					pw.close();
				} catch (FileNotFoundException e) {
				}
				defaultHandler.uncaughtException(thread, ex);
			}

			void saveState(Throwable e, PrintWriter pw)
					throws FileNotFoundException {
				StackTraceElement[] stacks = e.getStackTrace();
				pw.println(e.getClass().getCanonicalName());
				StringBuilder sb = new StringBuilder();
				for (StackTraceElement stack : stacks) {
					sb.setLength(0);
					sb.append("\t");
					sb.append(stack.getClassName()).append("#");
					sb.append(stack.getMethodName()).append(":");
					sb.append(stack.getLineNumber());
					pw.println(sb.toString());
					Log.i("LOG", sb.toString());
				}
				Throwable cause = e.getCause();
				if (cause != null) {
					saveState(cause, pw);
				}
				pw.close();
			}
		});
	}
}
