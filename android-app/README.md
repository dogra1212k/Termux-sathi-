# Termux-Sathi Android App

Native Android companion app for the Termux-Sathi repository.

## Features

- Termux-Sathi setup commands copy
- GitHub repository open
- Kali Master Index shortcut
- Kali Tools Guide shortcut
- Safe Labs shortcut
- NetHunter Rootless Guide shortcut
- Termux launch button

The app does not silently execute Kali/root commands. It acts as a safe launcher/reference companion.

## Local build

Requires JDK 17 and Android SDK 35.

From `android-app/`:

```bash
gradle :app:assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Fresh build verification trigger.

Self-contained learning terminal build verification.

Learning terminal v2.1 build verification trigger.

Learning terminal v2.2 build verification trigger.
