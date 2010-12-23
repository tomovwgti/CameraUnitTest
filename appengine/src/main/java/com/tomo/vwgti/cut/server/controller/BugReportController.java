package com.tomo.vwgti.cut.server.controller;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.tomo.vwgti.cut.server.entity.BugReport;
import com.tomo.vwgti.cut.server.entity.BugReportMeta;
import com.tomo.vwgti.cut.server.service.BugReportService;

public class BugReportController extends BaseController {

	BugReportMeta meta = BugReportMeta.get();

	@Override
	Navigation doResponse() throws Exception {
		String json = getRequestBody();
		BugReport report;
		if (!StringUtil.isEmpty(json)) {
			report = meta.jsonToModel(json);
		} else {
			report = new BugReport();
		}

		if (isPost()) {
			// データ保存モード
			report.setRemoteAddr(request.getRemoteAddr());
			Datastore.put(report);

			responseSucceed();
		} else {
			// データ検索モード
			List<BugReport> list = BugReportService.filter(report);
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			boolean exists = false;
			for (BugReport r : list) {
				builder.append(meta.modelToJson(r)).append(",");
				exists = true;
			}
			if (exists) {
				builder.setLength(builder.length() - 1);
			}
			builder.append("]");
			responseSucceed(builder);
		}
		return null;
	}
}
