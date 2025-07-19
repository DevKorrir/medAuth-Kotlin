# medAuth-Kotlin

📁 dev.korryr.medauth/
├── 📁 data/
│   ├── 📁 local/
│   │   ├── 📁 database/
│   │   │   ├── AppDatabase.kt
│   │   │   ├── 📁 entities/
│   │   │   │   ├── ScanResultEntity.kt
│   │   │   │   ├── DrugEntity.kt
│   │   │   │   └── VerificationHistoryEntity.kt
│   │   │   └── 📁 dao/
│   │   │       ├── ScanResultDao.kt
│   │   │       ├── DrugDao.kt
│   │   │       └── VerificationHistoryDao.kt
│   │   └── 📁 preferences/
│   │       └── AppPreferences.kt
│   ├── 📁 remote/
│   │   ├── 📁 api/
│   │   │   ├── DrugVerificationApiService.kt
│   │   │   └── AuthApiService.kt
│   │   ├── 📁 dto/
│   │   │   ├── DrugVerificationRequest.kt
│   │   │   ├── DrugVerificationResponse.kt
│   │   │   ├── DrugDetailsResponse.kt
│   │   │   └── ApiResponse.kt
│   │   └── 📁 interceptors/
│   │       ├── AuthInterceptor.kt
│   │       └── LoggingInterceptor.kt
│   └── 📁 repository/
│       ├── DrugRepositoryImpl.kt
│       ├── AuthRepositoryImpl.kt
│       └── ScannerRepositoryImpl.kt
│
├── 📁 domain/
│   ├── 📁 models/
│   │   ├── Drug.kt
│   │   ├── ScanResult.kt
│   │   ├── VerificationResult.kt
│   │   ├── User.kt
│   │   └── 📁 enums/
│   │       ├── RiskLevel.kt
│   │       ├── ScanMethod.kt
│   │       └── VerificationStatus.kt
│   ├── 📁 repository/
│   │   ├── DrugRepository.kt
│   │   ├── AuthRepository.kt
│   │   └── ScannerRepository.kt
│   └── 📁 usecases/
│       ├── VerifyDrugUseCase.kt
│       ├── ScanDrugUseCase.kt
│       ├── GetScanHistoryUseCase.kt
│       ├── LoginUseCase.kt
│       ├── LogoutUseCase.kt
│       └── ReportSuspiciousDrugUseCase.kt
│
├── 📁 presentation/
│   ├── 📁 activities/
│   │   ├── MainActivity.kt
│   │   ├── SplashActivity.kt
│   │   └── OnboardingActivity.kt
│   └── 📁 features/
│       ├── 📁 auth/
│       │   ├── 📁 login/
│       │   │   ├── LoginFragment.kt
│       │   │   ├── LoginViewModel.kt
│       │   │   └── LoginUiState.kt
│       │   ├── 📁 register/
│       │   │   ├── RegisterFragment.kt
│       │   │   ├── RegisterViewModel.kt
│       │   │   └── RegisterUiState.kt
│       │   └── 📁 profile/
│       │       ├── ProfileFragment.kt
│       │       ├── ProfileViewModel.kt
│       │       └── ProfileUiState.kt
│       ├── 📁 scan/
│       │   ├── ScanFragment.kt
│       │   ├── ScanViewModel.kt
│       │   ├── ScanUiState.kt
│       │   └── 📁 components/
│       │       ├── ScannerView.kt
│       │       ├── ScanResultCard.kt
│       │       └── ScanMethodSelector.kt
│       ├── 📁 verification/
│       │   ├── VerificationFragment.kt
│       │   ├── VerificationViewModel.kt
│       │   ├── VerificationUiState.kt
│       │   └── 📁 components/
│       │       ├── DrugInfoCard.kt
│       │       ├── RiskIndicator.kt
│       │       └── VerificationStatusCard.kt
│       ├── 📁 history/
│       │   ├── HistoryFragment.kt
│       │   ├── HistoryViewModel.kt
│       │   ├── HistoryUiState.kt
│       │   └── 📁 components/
│       │       ├── HistoryListItem.kt
│       │       └── FilterBottomSheet.kt
│       ├── 📁 dashboard/
│       │   ├── DashboardFragment.kt
│       │   ├── DashboardViewModel.kt
│       │   ├── DashboardUiState.kt
│       │   └── 📁 components/
│       │       ├── QuickActionCard.kt
│       │       ├── RecentScansWidget.kt
│       │       └── StatisticsCard.kt
│       └── 📁 reports/
│           ├── ReportsFragment.kt
│           ├── ReportsViewModel.kt
│           ├── ReportsUiState.kt
│           └── 📁 components/
│               ├── ReportForm.kt
│               └── ReportListItem.kt
│
├── 📁 navigation/
│   ├── AppNavigation.kt
│   ├── NavigationDestinations.kt
│   └── NavigationExtensions.kt
│
├── 📁 di/
│   ├── AppModule.kt
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   ├── RepositoryModule.kt
│   ├── UseCaseModule.kt
│   └── ViewModelModule.kt
│
├── 📁 utils/
│   ├── 📁 constants/
│   │   ├── AppConstants.kt
│   │   ├── NetworkConstants.kt
│   │   └── DatabaseConstants.kt
│   ├── 📁 extensions/
│   │   ├── ViewExtensions.kt
│   │   ├── StringExtensions.kt
│   │   └── DateExtensions.kt
│   ├── 📁 helpers/
│   │   ├── DateHelper.kt
│   │   ├── ValidationHelper.kt
│   │   └── PermissionHelper.kt
│   └── 📁 security/
│       ├── CryptoHelper.kt
│       ├── CertificatePinner.kt
│       └── BiometricHelper.kt
│
├── 📁 core/
│   ├── 📁 network/
│   │   ├── NetworkManager.kt
│   │   ├── ApiResult.kt
│   │   └── NetworkBoundResource.kt
│   ├── 📁 scanner/
│   │   ├── BarcodeScanner.kt
│   │   ├── QRCodeScanner.kt
│   │   ├── TextRecognizer.kt
│   │   └── ScannerManager.kt
│   ├── 📁 base/
│   │   ├── BaseViewModel.kt
│   │   ├── BaseFragment.kt
│   │   ├── BaseActivity.kt
│   │   └── BaseUiState.kt
│   └── 📁 exceptions/
│       ├── NetworkException.kt
│       ├── DatabaseException.kt
│       └── ScannerException.kt
│
└── 📁 resources/ (res/)
    ├── 📁 layout/
    ├── 📁 values/
    ├── 📁 drawable/
    ├── 📁 menu/
    └── 📁 navigation/
