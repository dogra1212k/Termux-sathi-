> 📚 **Navigation:** [Master Index](KALI_MASTER_INDEX.md) · [Complete Guide](KALI_TOOLS_GUIDE.md) · [Practical Guide](KALI_TOOLS_PRACTICAL.md) · [Quick Reference](KALI_QUICK_REFERENCE.md) · [Rootless Guide](NETHUNTER_ROOTLESS_GUIDE.md) · [Safe Labs](SAFE_LABS.md)

# 🧪 Kali Linux Tools — Step-by-Step Practical Guide

Ye practical guide **apne device, localhost, lab, CTF ya explicit permission wale system** ke liye hai.

> ⚠️ Random public IPs, websites, Wi-Fi, accounts ya third-party systems par scanning, brute-force, exploitation, interception ya credential testing mat karein.

---

# 1. Nmap — Port Discovery

## Install
```bash
sudo apt update
sudo apt install nmap -y
```

## Check
```bash
nmap --version
```

## Safe Lab
Terminal 1:
```bash
mkdir -p ~/kali-lab/nmap
cd ~/kali-lab/nmap
echo "Termux-Sathi Nmap Lab" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:
```bash
nmap -p 8000 127.0.0.1
```

Expected idea:
```text
8000/tcp open
```

Service detection:
```bash
nmap -sV -p 8000 127.0.0.1
```

---

# 2. curl — HTTP Testing

```bash
sudo apt install curl -y
curl --version
```

Local server check:
```bash
curl http://127.0.0.1:8000
```

Headers:
```bash
curl -I http://127.0.0.1:8000
```

Verbose:
```bash
curl -v http://127.0.0.1:8000
```

---

# 3. wget — Download Practice

```bash
sudo apt install wget -y
wget -O local.html http://127.0.0.1:8000
ls -lh local.html
cat local.html
```

---

# 4. WhatWeb — Web Technology Identification

```bash
sudo apt install whatweb -y
whatweb --version
```

Local practice:
```bash
whatweb http://127.0.0.1:8000
```

---

# 5. Nikto — Web Server Assessment

```bash
sudo apt install nikto -y
nikto -Version
```

Use only against your own local server/lab.

Help:
```bash
nikto -Help
```

---

# 6. Burp Suite — Web Proxy

```bash
sudo apt install burpsuite -y
burpsuite
```

Basic workflow:
```text
1. Burp start
2. Proxy tab open
3. Browser proxy 127.0.0.1:8080
4. Apne local test page ko open karo
5. Request/response dekho
```

GUI/VNC required ho sakta hai.

---

# 7. SQLMap — SQL Injection Testing

```bash
sudo apt install sqlmap -y
sqlmap --version
sqlmap --help
```

Practice only with an intentionally vulnerable local lab/CTF.

Safe learning task:
```text
- SQLMap options samjho
- request format dekho
- DVWA/Juice Shop jaise local training lab use karo
```

---

# 8. Wireshark / tshark — Packet Analysis

```bash
sudo apt install wireshark tshark -y
tshark --version
```

Interfaces:
```bash
tshark -D
```

Offline capture read:
```bash
tshark -r capture.pcap
```

HTTP filter:
```bash
tshark -r capture.pcap -Y http
```

Only inspect captures/traffic you own or are authorized to analyze.

---

# 9. tcpdump — CLI Packet Capture

```bash
sudo apt install tcpdump -y
tcpdump --version
tcpdump -D
```

Local interface capture may require privileges:
```bash
sudo tcpdump -i lo
```

Stop:
```text
Ctrl + C
```

Save own lab capture:
```bash
sudo tcpdump -i lo -w lab.pcap
```

Read:
```bash
tcpdump -r lab.pcap
```

---

# 10. Netcat — Local TCP Practice

```bash
sudo apt install netcat-openbsd -y
nc -h
```

Terminal 1:
```bash
nc -l 127.0.0.1 9000
```

Terminal 2:
```bash
nc 127.0.0.1 9000
```

Ab text type karo. Dono terminals ke beech local TCP connection dikhega.

---

# 11. dig / nslookup — DNS

```bash
sudo apt install dnsutils -y
dig example.com
dig +short example.com
nslookup example.com
```

Localhost check:
```bash
getent hosts localhost
```

---

# 12. ss — Ports & Sockets

```bash
ss -tuln
ss -tun
```

Port 8000:
```bash
ss -tuln | grep 8000
```

---

# 13. Lynis — Local System Audit

```bash
sudo apt install lynis -y
lynis show version
sudo lynis audit system
```

Focus:
```text
Warnings
Suggestions
Hardening index
```

---

# 14. John the Ripper — Password Audit Basics

```bash
sudo apt install john -y
john --help
```

Safe practice:
```text
Use only hashes generated from your own test passwords.
Do not use against stolen/leaked credentials.
```

Show supported formats:
```bash
john --list=formats
```

---

# 15. Hashcat — Hash Auditing

```bash
sudo apt install hashcat -y
hashcat --version
hashcat --help
```

Benchmark may be limited on Rootless Android:
```bash
hashcat -b
```

GPU/OpenCL availability depends on hardware and environment.

---

# 16. Hydra — Authentication Audit

```bash
sudo apt install hydra -y
hydra -h
```

Use only with your own intentionally configured test service.

Safe learning:
```text
- syntax help samjho
- module list dekho
- lab account lockout behavior samjho
```

---

# 17. Searchsploit — Exploit Database Search

```bash
sudo apt install exploitdb -y
searchsploit --help
```

Search:
```bash
searchsploit apache
```

Ye local exploit database search karta hai. Result milna exploit chalane ki permission nahi deta.

---

# 18. Metasploit Framework — Lab Framework

```bash
sudo apt install metasploit-framework -y
msfconsole
```

Inside:
```text
help
version
exit
```

Use only against intentionally vulnerable lab machines/CTFs.

---

# 19. GDB — Debugging

```bash
sudo apt install gdb gcc -y
gdb --version
```

Create safe program:
```bash
cat > hello.c <<'EOF'
#include <stdio.h>
int main() {
    printf("Hello Termux-Sathi\n");
    return 0;
}
EOF
```

Compile:
```bash
gcc -g hello.c -o hello
```

Debug:
```bash
gdb ./hello
```

Inside:
```text
break main
run
next
quit
```

---

# 20. strings / file / sha256sum — Forensics Basics

```bash
echo "Termux-Sathi Forensics" > sample.txt
file sample.txt
strings sample.txt
sha256sum sample.txt
md5sum sample.txt
```

---

# 21. Binwalk — Firmware/File Inspection

```bash
sudo apt install binwalk -y
binwalk --help
```

Use with files/firmware images you own or have permission to analyze.

---

# 22. YARA — Pattern Matching

```bash
sudo apt install yara -y
yara --version
```

Create rule:
```bash
cat > sample.yar <<'EOF'
rule TermuxSathiDemo {
    strings:
        $a = "Termux-Sathi"
    condition:
        $a
}
EOF
```

Test:
```bash
echo "Hello Termux-Sathi" > test.txt
yara sample.yar test.txt
```

Expected:
```text
TermuxSathiDemo test.txt
```

---

# 23. APKTool — Android APK Inspection

```bash
sudo apt install apktool -y
apktool --version
```

Help:
```bash
apktool --help
```

Only inspect APKs you own or have permission to analyze.

---

# 24. JADX — Android Code Viewer

```bash
sudo apt install jadx -y
jadx --version
```

GUI:
```bash
jadx-gui
```

CLI:
```bash
jadx --help
```

---

# 25. Radare2 — Reverse Engineering

```bash
sudo apt install radare2 -y
r2 -v
```

Use own compiled binary:
```bash
r2 ./hello
```

Inside:
```text
aaa
afl
q
```

---

# 26. Aircrack-ng — Wireless Toolkit

```bash
sudo apt install aircrack-ng -y
aircrack-ng --help
```

Interface info:
```bash
iw dev
ip link
```

Monitor mode/injection requires compatible hardware/kernel and should only be used on your own Wi-Fi lab.

---

# 27. Kismet — Wireless Monitoring

```bash
sudo apt install kismet -y
kismet --version
```

Hardware support and privileges may be unavailable in Rootless Kali.

---

# 28. Bettercap — Network Lab Tool

```bash
sudo apt install bettercap -y
bettercap -version
```

Use only inside your own isolated network lab.

---

# 29. mitmproxy — HTTP Proxy Lab

```bash
sudo apt install mitmproxy -y
mitmproxy --version
```

Start local proxy:
```bash
mitmproxy
```

Use only with your own browser/device traffic in a lab.

---

# 30. Gobuster — Content Discovery

```bash
sudo apt install gobuster -y
gobuster help
```

Use only against your own local web lab. Start with:
```text
gobuster dir --help
```

---

# 31. Wfuzz — Web Fuzzing

```bash
sudo apt install wfuzz -y
wfuzz --help
```

Use only on intentionally vulnerable local applications/CTFs.

---

# 32. WPScan — WordPress Security Assessment

```bash
sudo apt install wpscan -y
wpscan --version
```

Use only with your own WordPress lab.

---

# 33. ffuf — Web Fuzzer

```bash
sudo apt install ffuf -y
ffuf -h
```

Use only with local/authorized web labs.

---

# 34. OpenSSL — Crypto Basics

```bash
openssl version
echo "Termux-Sathi" > msg.txt
sha256sum msg.txt
```

Random bytes:
```bash
openssl rand -hex 16
```

---

# 35. SQLite — Local Database Practice

```bash
sudo apt install sqlite3 -y
sqlite3 lab.db
```

Inside:
```sql
CREATE TABLE notes(id INTEGER, text TEXT);
INSERT INTO notes VALUES(1,'Termux-Sathi');
SELECT * FROM notes;
.quit
```

---

# 36. Git — Tool Source / Lab Management

```bash
sudo apt install git -y
git --version
git status
```

Never run random scripts from a cloned repo without reading them first.

---

# 37. Python — Safe Local Lab Server

```bash
python3 --version
mkdir -p ~/kali-lab/web
cd ~/kali-lab/web
echo "Kali Lab" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

This local server can be reused for Nmap, curl, WhatWeb and basic proxy practice.

---

# 38. Common Errors

## command not found
```bash
apt search TOOL
sudo apt install PACKAGE -y
```

## Permission denied
```bash
ls -l FILE
chmod +x FILE
```

## Address already in use
```bash
ss -tuln | grep PORT
```

## Package not found
```bash
sudo apt update
apt search PACKAGE
```

## GUI not opening
Rootless Kali me KeX/VNC/desktop environment required ho sakta hai.

---

# 39. Daily Practice Order

```text
1. Linux commands
2. Networking
3. Local Python server
4. Nmap
5. curl / WhatWeb
6. tshark / tcpdump
7. Lynis
8. Forensics tools
9. GDB / reverse engineering
10. Web security labs
11. Authorized CTF exploitation
12. Reporting
```

---

# 40. Universal Tool Checklist

Har tool ke liye ye 7 steps follow karo:

```text
1. Tool ka purpose samjho
2. Package install karo
3. Version check karo
4. --help / man padho
5. Local lab banao
6. Command run karo
7. Output aur error note karo
```

Yahi actual learning workflow hai. Sirf commands ratna Linux ko impress nahi karta. 😄
