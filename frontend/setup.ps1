# Frontend Setup Script for Windows

Write-Host "Restrudent Frontend Setup" -ForegroundColor Green
Write-Host "=========================" -ForegroundColor Green
Write-Host ""

# Navigate to frontend directory
Set-Location -Path "$PSScriptRoot"

# Check if Node.js is installed
Write-Host "Checking Node.js installation..." -ForegroundColor Yellow
try {
    $nodeVersion = node --version
    Write-Host "Node.js version: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "Error: Node.js is not installed. Please install Node.js 18+ from https://nodejs.org/" -ForegroundColor Red
    exit 1
}

# Check if npm is installed
Write-Host "Checking npm installation..." -ForegroundColor Yellow
try {
    $npmVersion = npm --version
    Write-Host "npm version: $npmVersion" -ForegroundColor Green
} catch {
    Write-Host "Error: npm is not installed." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Installing dependencies..." -ForegroundColor Yellow
Write-Host "This may take a few minutes..." -ForegroundColor Yellow
Write-Host ""

# Try to install dependencies
try {
    npm install --legacy-peer-deps
    Write-Host ""
    Write-Host "Dependencies installed successfully!" -ForegroundColor Green
} catch {
    Write-Host ""
    Write-Host "Warning: Some dependencies may not have installed correctly." -ForegroundColor Yellow
    Write-Host "Trying alternative installation method..." -ForegroundColor Yellow

    # Try with force flag
    npm install --force
}

Write-Host ""

# Check if .env.local exists
if (-Not (Test-Path ".env.local")) {
    Write-Host ".env.local file not found!" -ForegroundColor Yellow
    if (Test-Path ".env.local.example") {
        Write-Host "Creating .env.local from example..." -ForegroundColor Yellow
        Copy-Item ".env.local.example" ".env.local"
        Write-Host "Please edit .env.local with your configuration." -ForegroundColor Yellow
    } else {
        Write-Host "Creating default .env.local..." -ForegroundColor Yellow
        @"
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_KEYCLOAK_URL=http://localhost:9090
NEXT_PUBLIC_KEYCLOAK_REALM=retrurant-review
NEXT_PUBLIC_KEYCLOAK_CLIENT_ID=restrudent-client
"@ | Out-File -FilePath ".env.local" -Encoding utf8
        Write-Host ".env.local created with default values." -ForegroundColor Green
    }
} else {
    Write-Host ".env.local already exists." -ForegroundColor Green
}

Write-Host ""
Write-Host "Setup complete!" -ForegroundColor Green
Write-Host ""
Write-Host "To start the development server, run:" -ForegroundColor Cyan
Write-Host "  npm run dev" -ForegroundColor White
Write-Host ""
Write-Host "The application will be available at:" -ForegroundColor Cyan
Write-Host "  http://localhost:3000" -ForegroundColor White
Write-Host ""
