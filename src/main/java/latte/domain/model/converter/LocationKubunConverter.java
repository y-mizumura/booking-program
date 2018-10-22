package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class LocationKubunConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";

		if (entityValue == null) {
			// 処理なし
		} else
			switch (entityValue) {
			case "お気に入り":
				dbValue = "A";
				break;
			case "都営":
				dbValue = "T";
				break;
			case "公営":
				dbValue = "K";
				break;
			case "私営":
				dbValue = "S";
				break;
			case "その他":
				dbValue = "Z";
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
			case "A":
				entityValue = "お気に入り";
				break;
			case "T":
				entityValue = "都営";
				break;
			case "K":
				entityValue = "公営";
				break;
			case "S":
				entityValue = "私営";
				break;
			case "Z":
				entityValue = "その他";
				break;
			}

		return entityValue;
	}
	
}
