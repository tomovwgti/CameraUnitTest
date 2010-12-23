package com.tomo.vwgti.cut.server.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.google.appengine.api.datastore.Text;

/**
 * JSON組み立て用のロジックを提供します.
 * 
 * @author vvakame
 */
public class JsonUtil {

	private JsonUtil() {
	}

	/**
	 * 与えられたMapを元にJSON文字列を組み立てます.
	 * mapのvalueにもつことができるのは、null、String、Numberのいずれかです.
	 * 
	 * @param map
	 *            変換したいMap
	 * @return 与えられたMapのJSON表現
	 */
	public static StringBuilder toJson(Map<String, Object> map) {
		return toJson(map, null);
	}

	/**
	 * 与えられたMapを元にJSON文字列を組み立てます.
	 * mapのvalueにもつことができるのは、null、String、Numberのいずれかです.
	 * 
	 * @param map
	 *            変換したいMap
	 * @param builder
	 *            組み立てに利用するStringBuilderまたはnull.
	 * @return 与えられたMapのJSON表現
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuilder toJson(Map<String, Object> map,
			StringBuilder builder) {
		if (builder == null) {
			builder = new StringBuilder();
		}
		if (map == null) {
			return builder.append("null");
		}

		builder.append("{");
		int add = 0;
		for (String key : map.keySet()) {
			Object value = map.get(key);
			if (value == null) {
				builder.append("\"").append(key).append("\":null,");
			} else if (value instanceof String) {
				builder.append("\"").append(key).append("\":\"")
						.append(sanitizeJson((String) value)).append("\",");
			} else if (value instanceof Collection) {
				Collection collection = (Collection) value;
				if (collection.isEmpty()) {
					builder.append("\"").append(key).append("\":[],");
				} else if (collection.iterator().next() instanceof Map) {
					builder.append("\"").append(key).append("\":")
							.append(toJsonArray(collection).toString())
							.append(",");
				} else {
					builder.append("\"").append(key).append("\":[");
					boolean first = true;
					for (Object object : collection) {
						if (first == false) {
							builder.append(",");
						} else {
							first = false;
						}
						if (object instanceof String) {
							builder.append("\"").append(object).append("\"");
						} else {
							builder.append(object.toString());
						}
					}
					builder.append("],");
				}
			} else if (value instanceof Number) {
				builder.append("\"").append(key).append("\":").append(value)
						.append(",");
			} else if (value instanceof Boolean) {
				builder.append("\"").append(key).append("\":").append(value)
						.append(",");
			} else if (value instanceof Text) {
				builder.append("\"").append(key).append("\":\"")
						.append(sanitizeJson(((Text) value).getValue()))
						.append("\",");
			} else if (value instanceof Enum) {
				builder.append("\"").append(key).append("\":\"")
						.append(((Enum) value).name()).append("\",");
			} else if (value instanceof Date) {
				builder.append("\"").append(key).append("\":\"")
						.append(((Date) value).getTime()).append("\",");
			} else {
				// TODO 必要だったらMapとCollectionsの場合も実装
				throw new IllegalArgumentException("key=" + key
						+ " に対応するvalueの型は対応していません - " + value.getClass());
			}
			add++;
		}
		// 1要素でも追加していたら1文字削る
		if (0 < add) {
			builder.setLength(builder.length() - 1);
		}
		builder.append("}");

		return builder;
	}

	/**
	 * 与えられたrepositoryをJSON形式の文字列として保持するStringBuilderに変換します.
	 * 
	 * @param maps
	 * @return repositoriesのJSON表現
	 */
	public static StringBuilder toJsonArray(Collection<Map<String, Object>> maps) {
		return toJsonArray(maps, null);
	}

	/**
	 * 与えられたrepositoryをJSON形式の文字列として保持するStringBuilderに変換します.
	 * StringBuilderが渡された場合、後部に連結するようにJSONを組み立てます.
	 * 
	 * @param maps
	 * @param builder
	 * @return repositoriesのJSON表現
	 */
	public static StringBuilder toJsonArray(
			Collection<Map<String, Object>> maps, StringBuilder builder) {

		if (builder == null) {
			builder = new StringBuilder();
		}
		if (maps == null) {
			return builder.append("null");
		}

		builder.append("[");
		int add = 0;
		for (Map<String, Object> map : maps) {
			toJson(map, builder);
			builder.append(",");
			add++;
		}
		// 1要素でも追加していたら1文字削る
		if (0 < add) {
			builder.setLength(builder.length() - 1);
		}
		builder.append("]");

		return builder;
	}

	/**
	 * JSON的に意味のある文字を変換します。
	 * 
	 * @param orig
	 *            変換したい文字列
	 * @return 変換後文字列
	 */
	public static String sanitizeJson(String orig) {
		if (orig == null) {
			return null;
		}
		return orig.replace("\\", "\\\\").replace("\"", "\\\"")
				.replace("/", "\\/").replace("\b", "\\b").replace("\f", "\\f")
				.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}
