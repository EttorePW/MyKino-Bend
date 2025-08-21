# Fix para Screening Times en Hall

## Problema Identificado

Cuando se creaba un Hall con screening times, los horarios no se guardaban en la tabla `hall_screening_times` aunque se enviaran correctamente desde el frontend.

## Causa del Problema

El problema estaba en el manejo del `@ElementCollection` en JPA. Aunque la configuración era correcta, JPA no estaba persistiendo automáticamente los screening times al guardar el Hall.

## Solución Implementada

### 1. Mejoras en HallService.java

- **Logging detallado**: Agregamos logs para monitorear el proceso de creación
- **Manejo explícito de la colección**: Aseguramos que los screeningTimes se asignen correctamente
- **Mecanismo de fallback**: Si `@ElementCollection` falla, insertamos manualmente los datos
- **Transacción**: Agregamos `@Transactional` para asegurar consistencia

```java
@Transactional
public RespHallDTO createNewHall(NewHallDTO newHallDTO) {
    // ... código existente ...
    
    // Asegurar que la lista de screeningTimes no es null y está inicializada
    if (newHallDTO.getScreeningTimes() != null && !newHallDTO.getScreeningTimes().isEmpty()) {
        hall.getScreeningTimes().clear();
        hall.getScreeningTimes().addAll(newHallDTO.getScreeningTimes());
    }
    
    Hall savedHall = hallRepository.save(hall);
    
    // Verificar si los screeningTimes se guardaron correctamente
    // Si no, usar inserción manual como fallback
    if (!hallScreeningTimeService.hasScreeningTimes(savedHall.getHallId())) {
        hallScreeningTimeService.insertScreeningTimes(savedHall.getHallId(), newHallDTO.getScreeningTimes());
    }
    
    return convertToRespHallDTO(savedHall);
}
```

### 2. Mejoras en Hall.java

- **FetchType.EAGER**: Cambiamos el fetch type para asegurar que los screening times se carguen inmediatamente

```java
@ElementCollection(fetch = FetchType.EAGER)
@CollectionTable(name = "hall_screening_times", joinColumns = @JoinColumn(name = "hall_id"))
@Column(name = "screening_time")
private List<String> screeningTimes = new ArrayList<>();
```

### 3. Nuevos Métodos en HallScreeningTimeService.java

Agregamos métodos para manejar manualmente los screening times:

- **`insertScreeningTimes()`**: Inserta manualmente los screening times
- **`hasScreeningTimes()`**: Verifica si un hall tiene screening times registrados

### 4. Endpoint de Debug

Agregamos un endpoint de debug en HallController para probar la funcionalidad:

```
GET /api/hall/{id}/screening-times/debug
```

## Cómo Probar la Solución

1. **Ejecutar el servidor**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Ejecutar el script de prueba**:
   ```powershell
   .\test-screening-times.ps1
   ```

3. **Crear un Hall manualmente** usando el archivo `test-hall-creation.json`:
   ```powershell
   $body = Get-Content -Path "test-hall-creation.json" -Raw
   Invoke-RestMethod -Uri "http://localhost:8080/api/hall" -Method POST -Body $body -ContentType "application/json"
   ```

## Archivos Modificados

1. `src/main/java/CodersBay/Kino/hall/HallService.java` - Lógica principal mejorada
2. `src/main/java/CodersBay/Kino/hall/Hall.java` - Configuración de @ElementCollection
3. `src/main/java/CodersBay/Kino/hall/HallScreeningTimeService.java` - Métodos de fallback
4. `src/main/java/CodersBay/Kino/hall/HallController.java` - Endpoint de debug

## Archivos de Prueba Creados

1. `test-hall-creation.json` - Payload de ejemplo para crear un Hall
2. `test-screening-times.ps1` - Script de prueba automatizada
3. `SCREENING_TIMES_FIX.md` - Esta documentación

## Verificación de la Solución

Después de implementar estos cambios, deberías ver:

1. **En los logs**: Mensajes indicando si `@ElementCollection` funcionó o si se usó el fallback
2. **En la respuesta de la API**: Los screening times incluidos en el `RespHallDTO`
3. **En la base de datos**: Registros en la tabla `hall_screening_times`

## Debugging Adicional

Si el problema persiste, puedes:

1. Verificar los logs del servidor para ver los mensajes de debug
2. Usar el endpoint `/api/hall/{id}/screening-times/debug` para verificar los datos en la BD
3. Usar el endpoint `/debug/hall-migration-status` para verificar el estado de las tablas
