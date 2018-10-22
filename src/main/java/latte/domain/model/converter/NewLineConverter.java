package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class NewLineConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";
		if (entityValue != null) {
			dbValue = entityValue.replace("\r\n", "|");
		}
		
		return dbValue;
	}

	@Override
	public String convertToEntityAttribute(String dbValue) {
		
		String entityValue = "";
		if (dbValue != null) {
			entityValue = dbValue.replace("|", "\r\n");
		}

		return entityValue;
	}

}
