# Termux-Sathi Android app — 3.1.0

An independent offline Hindi/Hinglish learning terminal. Package: `com.termuxsathi.app`.

- 54 lessons with content, explanation screens, five-question quiz and local reading progress.
- Real file practice in private app storage; canonical path validation prevents directory escape.
- Explicit simulations for network, Git, package and system-identity examples.
- No runtime permissions, network permission, advertising, analytics or production SDK dependencies.
- Privacy policy and confirmed local-data deletion inside the app.
- Android 7+ (`minSdk 24`), target/compile API 36, version code 2.

## Build

Use JDK 17, Gradle 8.11.1, Android SDK platform 36 and build tools 35.0.0.
From this directory:

```bash
gradle :app:testDebugUnitTest :app:lintRelease :app:assembleDebug :app:bundleRelease
```

Without signing variables, the release bundle is **unsigned** and cannot be uploaded to Play.
The debug APK is for device testing only. CI builds both, runs lint/unit tests and an Android 16 emulator smoke test.

- APK: `app/build/outputs/apk/debug/app-debug.apk`
- AAB: `app/build/outputs/bundle/release/app-release.aab`

## Signed Play bundle

Use the existing upload key if this package already exists in Play Console. Never replace an existing package ID or generate a different key for an existing listing without following Play's upload-key process.

Create GitHub environment `play-release` and add these secrets there (or as repository secrets):

| Secret | Value |
|---|---|
| `TERMUX_UPLOAD_KEYSTORE_BASE64` | Base64 content of the owner's upload keystore |
| `TERMUX_UPLOAD_STORE_PASSWORD` | Keystore password |
| `TERMUX_UPLOAD_KEY_ALIAS` | Upload key alias |
| `TERMUX_UPLOAD_KEY_PASSWORD` | Key password |

Run **Actions → Signed Play release bundle → Run workflow** on the reviewed branch.
The workflow fails clearly when secrets are missing, verifies the resulting JAR signature and uploads only the signed AAB and its checksum. It does not publish to Google Play.

For a local signed build, set `TERMUX_UPLOAD_STORE_FILE` (absolute path) and the three password/alias variables above, then run `gradle :app:bundleRelease`. Keep the key and passwords in a secure owner-controlled backup; never commit them or share them in chat.

See [Play submission checklist](../docs/PLAY_CONSOLE.md), [store listing](../docs/STORE_LISTING.md) and [privacy policy](../docs/privacy-policy.html).
