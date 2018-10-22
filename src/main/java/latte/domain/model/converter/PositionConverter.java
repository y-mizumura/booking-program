package latte.domain.model.converter;

import javax.persistence.AttributeConverter;

public class PositionConverter implements AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String entityValue) {
		
		String dbValue = "";
		
		if(entityValue == null){
	        // 処理なし
	    } else switch (entityValue) {
			case "前衛" :
				dbValue = "Z";
				break;
			case "後衛" :
				dbValue = "K";
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
			case "Z" :
				entityValue = "前衛";
				break;
			case "K" :
				entityValue = "後衛";
				break;
		}
		
		return entityValue;
	}

}
