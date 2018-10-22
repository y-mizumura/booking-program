package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class UketukeStateConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";

		if (entityValue == null) {
			// 処理なし
		} else
			switch (entityValue) {
			case "受付中":
				dbValue = "U";
				break;
			case "受付終了":
				dbValue = "E";
				break;
			case "清算終了":
				dbValue = "S";
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
			case "U":
				entityValue = "受付中";
				break;
			case "E":
				entityValue = "受付終了";
				break;
			case "S":
				entityValue = "清算終了";
				break;
			}

		return entityValue;
	}

}
