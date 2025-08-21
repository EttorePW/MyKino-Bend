package CodersBay.Kino.hall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class StringArrayConverter implements AttributeConverter<List<String>, String> {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        
        try {
            // Para PostgreSQL JSONB, aseguramos que sea un JSON válido
            String jsonString = objectMapper.writeValueAsString(attribute);
            System.out.println("[DEBUG] Converting to DB: " + jsonString);
            return jsonString;
        } catch (JsonProcessingException e) {
            System.err.println("Error converting list to JSON: " + e.getMessage());
            e.printStackTrace();
            return "[]";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty() || "null".equals(dbData)) {
            return new ArrayList<>();
        }
        
        try {
            System.out.println("[DEBUG] Converting from DB: " + dbData);
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            System.err.println("Error converting JSON to list: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
