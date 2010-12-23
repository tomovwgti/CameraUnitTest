package com.tomo.vwgti.cut.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;
import org.slim3.util.StringUtil;

import com.tomo.vwgti.cut.server.entity.BugReport;
import com.tomo.vwgti.cut.server.entity.BugReportMeta;

public class BugReportService {

	static BugReportMeta meta = BugReportMeta.get();

	private BugReportService() {
	}

	public static List<BugReport> filter(BugReport hint) {
		ModelQuery<BugReport> query = Datastore.query(meta);
		hint = hint != null ? hint : new BugReport();
		// TODO
		// 本当に細かい条件で検索するなら全状態を網羅するUnitTestを書き完全なカスタムインデックスを手に入れられるようにするべき
		if (!StringUtil.isEmpty(hint.getModel())) {
			query = query.filter(meta.model.equal(hint.getModel()));
		}
		if (hint.getResult() != null) {
			query = query.filter(meta.result.equal(hint.getResult()));
		}
		query = query.sortInMemory(meta.createdAt.desc);
		return query.asList();
	}

	public static List<BugReport> queryAll() {
		return Datastore.query(meta).asList();
	}
}
