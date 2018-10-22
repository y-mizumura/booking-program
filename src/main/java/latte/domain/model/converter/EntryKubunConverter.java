package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class EntryKubunConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";

		if (entityValue == null) {
			// 処理なし
		} else
			switch (entityValue) {
			case "参加":
				dbValue = "S";
				break;
			case "途中参加":
				dbValue = "T";
				break;
			case "不参加":
				dbValue = "F";
				break;
			case "考え中":
				dbValue = "K";
				break;
			}

		return dbValue;
	}

	@Override
	public String convertToEntityAttribute(String dbValue) {
		
		String entityValue = "";

		if (dbValue == null) {
			// 処理なし
		} else
			switch (dbValue) {
			case "S":
				entityValue = "参加";
				break;
			case "T":
				entityValue = "途中参加";
				break;
			case "F":
				entityValue = "不参加";
				break;
			case "K":
				entityValue = "考え中";
				break;
			}

		return entityValue;
	}

}
