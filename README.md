# Security Snack

A small Jetpack Compose demo app that showcases **Android application security essentials** — from secure key storage and biometric authentication to obfuscation and log hygiene.  
Built for developers who want to *understand, not memorize* Android security internals.

### 1. Android Keystore
- AES-GCM keys generated via `KeyGenParameterSpec`
- Keys are **non-exportable**, tied to app UID
- Supports both **per-use (CryptoObject)** and **session window** authentication modes
- Proper IV generation (fresh 12-byte random per encryption)

### 2. BiometricPrompt Integration
Two valid flows implemented:
1. **Biometric-only per-use auth** → uses a `CryptoObject`
2. **Biometric + Device Credential** → session window via `setUserAuthenticationParameters()`


### 3. Legacy Awareness — EncryptedSharedPreferences
- Demonstrates the now-deprecated Jetpack Security Crypto API
- Used only for non-sensitive prefs to show compatibility with older codebases

### 4. R8 / ProGuard Rules
- Enabled code shrinking & resource shrinking in `release`
** Before APK size** - **9MB**
** After APK size** - **2.2MB**