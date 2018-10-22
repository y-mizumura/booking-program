package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class SexConverter implements AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";
		
		if(entityValue == null){
	        // 処理なし
	    } else switch (entityValue) {
			case "男性" :
				dbValue = "M";
				break;
			case "女性" :
				dbValue = "F";
				break;
		}
		
		return dbValue;
	}

	@Override
	public String convertToEntityAttribute(String dbValue) {
		
		String entityValue = "";
		
		if(dbValue == null){
	        // 処理なし
	    } else switch (dbValue) {
			case "M" :
				entityValue = "男性";
				break;
			case "F" :
				entityValue = "女性";
				break;
		}
		
		return entityValue;
	}

}
