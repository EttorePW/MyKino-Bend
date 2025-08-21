# Script para probar la creación de halls con screening times
$baseUrl = "http://localhost:8080"

Write-Host "=== Testing Hall Screening Times Fix ===" -ForegroundColor Green

# Función para hacer peticiones HTTP con mejor manejo de errores
function Invoke-ApiRequest {
    param(
        [string]$Uri,
        [string]$Method = "GET",
        [string]$Body = $null,
        [string]$ContentType = "application/json"
    )
    
    try {
        if ($Body) {
            $response = Invoke-RestMethod -Uri $Uri -Method $Method -Body $Body -ContentType $ContentType
        } else {
            $response = Invoke-RestMethod -Uri $Uri -Method $Method
        }
        return $response
    } catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.Exception.Response) {
            $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
            $errorBody = $reader.ReadToEnd()
            Write-Host "Response: $errorBody" -ForegroundColor Red
        }
        return $null
    }
}

# 1. Verificar estado de la base de datos
Write-Host "1. Checking database status..." -ForegroundColor Yellow
$dbStatus = Invoke-ApiRequest -Uri "$baseUrl/debug/hall-migration-status"
if ($dbStatus) {
    Write-Host "Database Status: OK" -ForegroundColor Green
    Write-Host "Hall table exists: $($dbStatus.hallCount) halls" -ForegroundColor Cyan
    Write-Host "Hall screening times table exists: $($dbStatus.hall_screening_times_table_exists)" -ForegroundColor Cyan
    Write-Host "Screening times count: $($dbStatus.screeningTimesCount)" -ForegroundColor Cyan
} else {
    Write-Host "Could not connect to database debug endpoint" -ForegroundColor Red
}

Write-Host ""

# 2. Crear un nuevo hall con screening times
Write-Host "2. Creating new hall with screening times..." -ForegroundColor Yellow
$newHallData = Get-Content -Path "test-hall-creation.json" -Raw
$newHall = Invoke-ApiRequest -Uri "$baseUrl/api/hall" -Method "POST" -Body $newHallData

if ($newHall) {
    Write-Host "Hall created successfully!" -ForegroundColor Green
    Write-Host "Hall ID: $($newHall.hallId)" -ForegroundColor Cyan
    Write-Host "Screening Times: $($newHall.screeningTimes -join ', ')" -ForegroundColor Cyan
    $hallId = $newHall.hallId
} else {
    Write-Host "Failed to create hall" -ForegroundColor Red
    exit 1
}

Write-Host ""

# 3. Verificar los screening times usando el endpoint de debug
Write-Host "3. Verifying screening times in database..." -ForegroundColor Yellow
$debugInfo = Invoke-ApiRequest -Uri "$baseUrl/api/hall/$hallId/screening-times/debug"

if ($debugInfo) {
    Write-Host "Debug Info:" -ForegroundColor Green
    Write-Host "  Has Screening Times: $($debugInfo.hasScreeningTimes)" -ForegroundColor Cyan
    Write-Host "  Count: $($debugInfo.screeningTimesCount)" -ForegroundColor Cyan
    Write-Host "  Times: $($debugInfo.screeningTimes -join ', ')" -ForegroundColor Cyan
    
    if ($debugInfo.hasScreeningTimes -and $debugInfo.screeningTimesCount -gt 0) {
        Write-Host "✅ SUCCESS: Screening times were properly saved to database!" -ForegroundColor Green
    } else {
        Write-Host "❌ PROBLEM: Screening times were NOT saved to database!" -ForegroundColor Red
    }
} else {
    Write-Host "Failed to get debug info" -ForegroundColor Red
}

Write-Host ""

# 4. Obtener el hall completo para verificar la respuesta
Write-Host "4. Getting full hall details..." -ForegroundColor Yellow
$fullHall = Invoke-ApiRequest -Uri "$baseUrl/api/hall/$hallId"

if ($fullHall) {
    Write-Host "Full Hall Details:" -ForegroundColor Green
    Write-Host "  Hall ID: $($fullHall.hallId)" -ForegroundColor Cyan
    Write-Host "  Capacity: $($fullHall.capacity)" -ForegroundColor Cyan
    Write-Host "  Seat Price: $($fullHall.seatPrice)" -ForegroundColor Cyan
    Write-Host "  Screening Times Count: $($fullHall.screeningTimes.Count)" -ForegroundColor Cyan
    Write-Host "  Screening Times: $($fullHall.screeningTimes -join ', ')" -ForegroundColor Cyan
    
    if ($fullHall.screeningTimes.Count -gt 0) {
        Write-Host "✅ SUCCESS: Hall response includes screening times!" -ForegroundColor Green
    } else {
        Write-Host "❌ PROBLEM: Hall response does NOT include screening times!" -ForegroundColor Red
    }
} else {
    Write-Host "Failed to get full hall details" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Test Complete ===" -ForegroundColor Green
