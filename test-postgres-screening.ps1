# Script para probar screening times con PostgreSQL en Render
# Aseg\u00farate de que tu aplicaci\u00f3n est\u00e9 desplegada en Render

$renderUrl = "https://tu-app-render.onrender.com"  # Cambia por tu URL de Render
Write-Host "=== Testing Screening Times with PostgreSQL ===" -ForegroundColor Green

# Funci\u00f3n para hacer peticiones con mejor manejo de errores
function Invoke-ApiRequest {
    param(
        [string]$Uri,
        [string]$Method = "GET",
        [string]$Body = $null,
        [string]$ContentType = "application/json"
    )
    
    Write-Host "Making $Method request to: $Uri" -ForegroundColor Yellow
    if ($Body) {
        Write-Host "Request body: $Body" -ForegroundColor Cyan
    }
    
    try {
        if ($Body) {
            $response = Invoke-RestMethod -Uri $Uri -Method $Method -Body $Body -ContentType $ContentType -TimeoutSec 30
        } else {
            $response = Invoke-RestMethod -Uri $Uri -Method $Method -TimeoutSec 30
        }
        Write-Host "\u2705 Success!" -ForegroundColor Green
        return $response
    } catch {
        Write-Host "\u274c Error: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.ErrorDetails.Message) {
            Write-Host "Details: $($_.ErrorDetails.Message)" -ForegroundColor Red
        }
        return $null
    }
}

Write-Host ""
Write-Host "Paso 1: Verificar que la aplicaci\u00f3n est\u00e9 funcionando..." -ForegroundColor Yellow
$healthCheck = Invoke-ApiRequest -Uri "$renderUrl/actuator/health"
if (-not $healthCheck) {
    Write-Host "La aplicaci\u00f3n no responde. Verifica la URL de Render." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Paso 2: Obtener lista de cinemas para usar un cinema_id v\u00e1lido..." -ForegroundColor Yellow
# Primero crear un cinema si no existe
$newCinema = @{
    name = "Test Cinema for Screening Times"
    address = "123 Test Street"
    manager = "Test Manager"
    maxHalls = 10
} | ConvertTo-Json -Depth 3

$cinema = Invoke-ApiRequest -Uri "$renderUrl/api/cinema" -Method "POST" -Body $newCinema
if ($cinema) {
    $cinemaId = $cinema.cinemaId
    Write-Host "Using cinema ID: $cinemaId" -ForegroundColor Cyan
} else {
    Write-Host "No se pudo crear cinema. Usando ID 1 por defecto." -ForegroundColor Yellow
    $cinemaId = 1
}

Write-Host ""
Write-Host "Paso 3: Crear hall con screening times..." -ForegroundColor Yellow
$newHall = @{
    capacity = 150
    occupiedSeats = 0
    supportedMovieVersion = "D2D"
    seatPrice = 15.50
    cinemaId = $cinemaId
    screeningTimes = @("14:00", "16:30", "19:00", "21:30")
} | ConvertTo-Json -Depth 3

Write-Host "Creating hall with data: $newHall" -ForegroundColor Cyan

$createdHall = Invoke-ApiRequest -Uri "$renderUrl/api/hall" -Method "POST" -Body $newHall
if ($createdHall) {
    $hallId = $createdHall.hallId
    Write-Host "\u2705 Hall created successfully!" -ForegroundColor Green
    Write-Host "Hall ID: $hallId" -ForegroundColor Cyan
    Write-Host "Returned screening times: $($createdHall.screeningTimes)" -ForegroundColor Cyan
    Write-Host "Screening times count: $($createdHall.screeningTimes.Count)" -ForegroundColor Cyan
} else {
    Write-Host "\u274c Failed to create hall" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Paso 4: Verificar screening times con el endpoint de debug..." -ForegroundColor Yellow
$debugInfo = Invoke-ApiRequest -Uri "$renderUrl/api/hall/$hallId/screening-times/debug"
if ($debugInfo) {
    Write-Host "Debug Results:" -ForegroundColor Green
    Write-Host "  Hall ID: $($debugInfo.hallId)" -ForegroundColor Cyan
    Write-Host "  Has Screening Times: $($debugInfo.hasScreeningTimes)" -ForegroundColor Cyan
    Write-Host "  Count: $($debugInfo.screeningTimesCount)" -ForegroundColor Cyan
    Write-Host "  Times: $($debugInfo.screeningTimes -join ', ')" -ForegroundColor Cyan
    
    if ($debugInfo.hasScreeningTimes -and $debugInfo.screeningTimesCount -gt 0) {
        Write-Host ""
        Write-Host "\u2705\u2705 \u00a1SUCCESS! Screening times se guardaron correctamente en PostgreSQL!" -ForegroundColor Green
        Write-Host "\ud83c\udf89 El problema est\u00e1 resuelto." -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "\u274c\u274c PROBLEMA: Screening times NO se guardaron en PostgreSQL" -ForegroundColor Red
        Write-Host "Revisa los logs de la aplicaci\u00f3n en Render para m\u00e1s detalles." -ForegroundColor Yellow
    }
} else {
    Write-Host "\u274c No se pudo obtener informaci\u00f3n de debug" -ForegroundColor Red
}

Write-Host ""
Write-Host "Paso 5: Obtener hall completo para verificaci\u00f3n final..." -ForegroundColor Yellow
$fullHall = Invoke-ApiRequest -Uri "$renderUrl/api/hall/$hallId"
if ($fullHall) {
    Write-Host "Hall completo obtenido:" -ForegroundColor Green
    Write-Host "  ID: $($fullHall.hallId)" -ForegroundColor Cyan
    Write-Host "  Capacity: $($fullHall.capacity)" -ForegroundColor Cyan
    Write-Host "  Screening Times Count: $($fullHall.screeningTimes.Count)" -ForegroundColor Cyan
    Write-Host "  Screening Times: $($fullHall.screeningTimes -join ', ')" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "=== Test Completo ===" -ForegroundColor Green
Write-Host ""
Write-Host "Instrucciones para usar con tu URL de Render:" -ForegroundColor Yellow
Write-Host "1. Edita este archivo y cambia `\$renderUrl` por tu URL real" -ForegroundColor White
Write-Host "2. Ejecuta: .\test-postgres-screening.ps1" -ForegroundColor White
Write-Host "3. Verifica los logs de Render para ver los mensajes de debug" -ForegroundColor White
