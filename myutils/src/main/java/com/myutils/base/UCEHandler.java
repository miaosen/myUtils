package com.myutils.base;

import android.content.Context;

import com.myutils.core.logger.L;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-13
 * @Descrition 全局未捕获异常处理
 */
public class UCEHandler implements
		java.lang.Thread.UncaughtExceptionHandler {

	private Context context;

	private static UCEHandler instant = null;

	public static synchronized UCEHandler getInstance() {
		if (instant == null) {
			instant = new UCEHandler();
		}
		return instant;
	}

	public void init(Context context) {
		this.context = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
//		StringWriter stackTrace = new StringWriter();
//		exception.printStackTrace(new PrintWriter(stackTrace));
		exception.printStackTrace();

		// Intent intent = new Intent("android.fbreader.action.CRASH",
		// new Uri.Builder().scheme(exception.getClass().getSimpleName())
		// .build());
		// try {
		// context.startActivity(intent);
		// } catch (Exception e) {
		// intent = new Intent(context, ErrorActivity.class);
		// // intent.putExtra(BugReportActivity.STACKTRACE,
		// // stackTrace.toString());
		// context.startActivity(intent);
		// }

		// if (context instanceof Activity) {
		// ((Activity)context).finish();
		// // }
//		if ("main".equals(thread.getName())) {
//			Intent intent = new Intent(context, ErrorActivity.class);
//			StackTraceElement[] stackTraces = exception.getStackTrace();
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < stackTraces.length; i++) {
//				StackTraceElement element = stackTraces[i];
//				sb.append(element.toString() + "\n");
//			}
//			intent.putExtra("msg", sb.toString());
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intent);
//		}

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);

	}
}
