package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class MemberStateConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";

		if (entityValue == null) {
			// 処理なし
		} else
			switch (entityValue) {
			case "在籍":
				dbValue = "Z";
				break;
			case "退会":
				dbValue = "T";
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
			case "Z":
				entityValue = "在籍";
				break;
			case "T":
				entityValue = "退会";
				break;
			}

		return entityValue;
	}

}
