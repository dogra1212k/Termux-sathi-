# Play Console submission — Termux-Sathi 3.1.0

## Prepared in this repository

- Target/compile API 36, min API 24, package `com.termuxsathi.app`, version code 2.
- Debug APK for installation testing; unsigned release AAB for build verification.
- Separate workflow to generate a signed release AAB using the owner's upload key.
- Offline privacy policy, in-app deletion, local-only data, no network or sensitive permissions.
- No native `.so` libraries, downloaded executables, root access or background service.
- Store copy and an HTML privacy policy ready to host.

## Owner/account details still required

1. Confirm `com.termuxsathi.app` is the intended package. If this is an update, it must match the existing listing; version code 2 must exceed every previously uploaded code. Check this before the first production upload.
2. Configure the upload-key secrets described in `android-app/README.md`. Use the existing upload key for an existing app. For a genuinely new listing, create and securely back up an upload key using Android Studio's Generate Signed Bundle wizard or the official signing guide. No key is included in this public repository.
3. Review the branch, then run **Signed Play release bundle**. Download `Termux-Sathi-3.1.0-play.aab`. An unsigned AAB or debug APK is not a Play release.
4. Host `docs/privacy-policy.html` at a public, non-editable HTTPS URL (for example GitHub Pages once configured). Verify it loads without sign-in, then enter that actual URL in Play Console. The local file and a proposed Pages URL are not proof that hosting is enabled. Confirm the privacy contact is appropriate; the current policy uses the repository issue tracker.
5. Add your support email and verified developer information. Use `STORE_LISTING.md` for text. Upload a 512×512 app icon, 1024×500 feature graphic and at least two genuine phone screenshots from the release candidate. Do not represent simulations as live device scans.
6. Complete App content: Data safety, ads, app access, content rating and target audience. The current binary has no collection/sharing, ads, account, payment or restricted-access area. Answer questionnaires accurately for all versions you distribute; a final rating or audience selection is the developer's decision.
7. Upload to internal testing first. Test launch, keyboard, back gesture, rotation, font scaling, all lesson screens, quiz, file actions, reset confirmation and deletion on a physical phone. Review the Play pre-launch report.
8. Complete any closed-testing or production-access requirements shown for your specific developer account, then submit for review. Google decides approval; building an AAB does not guarantee approval.

## References checked 15 October 2026

- Target API requirement: https://developer.android.com/google/play/requirements/target-sdk
- App signing: https://developer.android.com/studio/publish/app-signing
- Data safety: https://support.google.com/googleplay/android-developer/answer/10787469
- User data/privacy policy: https://support.google.com/googleplay/android-developer/answer/10144311

## Data declaration rationale

Practice files, reading progress and quiz score stay on the device. Session state contains transcript, command history and current input. Cloud and device-transfer backup are excluded. There are no network calls, ads or analytics SDKs. Use the in-app **Privacy & app info → Delete local data** action to erase local data, or clear storage/uninstall in Android settings. Reassess this declaration if future releases add connectivity or SDKs.
