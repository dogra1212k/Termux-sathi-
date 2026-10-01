# 🧭 Kali Tools Master Index

यह Termux-Sathi repository का master navigation page है. इसका लक्ष्य Kali Linux के tools को category-wise समझना, install करना और safe lab में practice करना है.

> ⚠️ Security tooling केवल अपने device, localhost, lab, CTF या explicit authorization वाले environment में use करें.

## 1. Official Kali tool collections

System collections:

```bash
sudo apt update
sudo apt full-upgrade -y

sudo apt install kali-linux-core -y
sudo apt install kali-linux-headless -y
sudo apt install kali-linux-default -y
sudo apt install kali-linux-arm -y
sudo apt install kali-linux-nethunter -y
sudo apt install kali-linux-large -y
sudo apt install kali-linux-everything -y
```

Useful tool collections:

```bash
sudo apt install kali-tools-top10 -y
sudo apt install kali-tools-information-gathering -y
sudo apt install kali-tools-vulnerability -y
sudo apt install kali-tools-web -y
sudo apt install kali-tools-database -y
sudo apt install kali-tools-passwords -y
sudo apt install kali-tools-wireless -y
sudo apt install kali-tools-802-11 -y
sudo apt install kali-tools-bluetooth -y
sudo apt install kali-tools-rfid -y
sudo apt install kali-tools-sdr -y
sudo apt install kali-tools-reverse-engineering -y
sudo apt install kali-tools-exploitation -y
sudo apt install kali-tools-social-engineering -y
sudo apt install kali-tools-sniffing-spoofing -y
sudo apt install kali-tools-post-exploitation -y
sudo apt install kali-tools-forensics -y
sudo apt install kali-tools-reporting -y
sudo apt install kali-tools-fuzzing -y
sudo apt install kali-tools-crypto-stego -y
sudo apt install kali-tools-hardware -y
sudo apt install kali-tools-gpu -y
sudo apt install kali-tools-voip -y
sudo apt install kali-tools-windows-resources -y
sudo apt install kali-linux-labs -y
```

Available metapackages on your current Kali release:

```bash
apt search '^kali-tools-'
apt search '^kali-linux-'
```

Installed Kali metapackages:

```bash
dpkg -l | grep -E 'kali-tools|kali-linux'
```

## 2. Top 10 toolset

`kali-tools-top10` currently includes major tools such as:

```text
Aircrack-ng
Burp Suite
Hydra
John the Ripper
Metasploit Framework
NetExec
Nmap
Responder
SQLMap
Wireshark
```

For beginner learning, start with help/version commands and safe labs:

```bash
aircrack-ng --help
burpsuite
hydra -h
john --help
msfconsole
nmap --help
sqlmap --help
wireshark --version
```

## 3. Recommended learning routes

### Route A — Beginner Linux + Networking

```text
Termux
→ Kali Rootless
→ Linux commands
→ permissions
→ processes
→ networking
→ Bash
→ Python
→ Git
```

### Route B — Defensive / Blue Team

```text
ss / lsof
→ tcpdump / tshark / Wireshark
→ logs
→ Lynis
→ YARA
→ ClamAV
→ hashes
→ forensics
→ system hardening
→ reporting
```

### Route C — Web Security Lab

```text
HTTP basics
→ curl
→ WhatWeb
→ Burp/ZAP
→ Gobuster/Wfuzz
→ Nikto
→ intentionally vulnerable web lab
→ reporting/fixes
```

### Route D — Reverse Engineering

```text
file
→ strings
→ xxd/hexdump
→ readelf/objdump/nm
→ GDB
→ Radare2/Rizin
→ Ghidra
→ APKTool/JADX
```

### Route E — Digital Forensics

```text
hashes
→ file/strings
→ ExifTool
→ YARA
→ dd/ddrescue
→ Sleuth Kit
→ Foremost
→ Autopsy
→ evidence notes/report
```

## 4. Repository guides

- `README.md` — Termux + Kali beginner path
- `KALI_TOOLS_GUIDE.md` — large category and practical reference
- `KALI_TOOLS_PRACTICAL.md` — hands-on practical chapters
- `KALI_QUICK_REFERENCE.md` — command cheat sheet
- `NETHUNTER_ROOTLESS_GUIDE.md` — Android/Rootless setup and limitations
- `SAFE_LABS.md` — reusable localhost practice labs

## 5. Rootless reality

On Android NetHunter Rootless, all Kali packages can be available, but package availability does not mean every hardware feature works.

Expect limitations around:

```text
Wi-Fi injection
monitor mode
USB gadget/HID
raw Bluetooth
SDR/RFID hardware
kernel modules
firewall manipulation
some service managers
GPU/OpenCL acceleration
some process/kernel statistics
```

Use `NETHUNTER_ROOTLESS_GUIDE.md` before assuming a tool is broken.

## 6. Universal tool workflow

For any tool:

```text
1. Purpose samjho
2. Package name verify karo
3. Install karo
4. Version check karo
5. --help / man padho
6. Safe local lab banao
7. Command run karo
8. Output note karo
9. Error troubleshoot karo
10. Findings/report likho
```

Useful template:

```bash
apt search TOOL
apt show PACKAGE
sudo apt install PACKAGE -y
which TOOL
TOOL --version
TOOL --help
man TOOL
```

## 7. Full-install warning

```bash
sudo apt install kali-linux-everything -y
```

Ye maximum Kali collection pull karta hai. Android/Rootless me isko blindly install karna practical nahi ho sakta because storage, RAM, GUI and hardware constraints.

Beginner Android setup ke liye usually:

```bash
sudo apt install kali-linux-default -y
```

ya selected categories better approach hain.

## 8. Safety boundary

Is repo ka goal learning, diagnostics, defensive analysis, authorized labs aur CTF practice hai.

High-risk categories jaise exploitation, credential auditing, interception/spoofing, social engineering aur post-exploitation ke liye:
- install/help/documentation available hai;
- practical execution ko isolated lab/CTF tak rakho;
- third-party accounts, Wi-Fi, websites, systems ya organizations par bina permission testing mat karo.
