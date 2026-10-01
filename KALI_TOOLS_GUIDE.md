> 📚 **Navigation:** [Master Index](KALI_MASTER_INDEX.md) · [Practical Guide](KALI_TOOLS_PRACTICAL.md) · [Quick Reference](KALI_QUICK_REFERENCE.md) · [Rootless Guide](NETHUNTER_ROOTLESS_GUIDE.md) · [Safe Labs](SAFE_LABS.md)

# 🐉 Kali Linux Tools — Step-by-Step Hindi Guide

यह guide Kali Linux के common tools को समझने और **अपने device, localhost, lab, CTF या explicitly authorized system** पर practice करने के लिए है.

> ⚠️ किसी दूसरे व्यक्ति/organization के system, Wi-Fi, account या website पर बिना permission testing न करें.

## 0. Tools install/search करने का तरीका

Kali update:

```bash
sudo apt update
```

Tool search:

```bash
apt search TOOL_NAME
```

Package details:

```bash
apt show TOOL_NAME
```

Install:

```bash
sudo apt install TOOL_NAME -y
```

Help:

```bash
TOOL_NAME --help
man TOOL_NAME
```

Kali tools categories/metapackages देखने के लिए:

```bash
apt search kali-tools
```

---

# 1. Nmap — Network Discovery & Port Checking

**काम:** अपने host/lab पर open ports और services देखना.

Install:

```bash
sudo apt install nmap -y
nmap --version
```

सबसे safe localhost practical:

```bash
nmap 127.0.0.1
```

Specific local port:

```bash
nmap -p 8000 127.0.0.1
```

Service/version detection अपने localhost पर:

```bash
nmap -sV -p 8000 127.0.0.1
```

### Practical lab

Terminal 1:

```bash
mkdir -p ~/nmap-lab
cd ~/nmap-lab
echo "Termux-Sathi Nmap Lab" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:

```bash
nmap -p 8000 127.0.0.1
curl http://127.0.0.1:8000
```

Expected: port 8000 open/listening दिख सकता है.

---

# 2. Wireshark — Packet Analysis

**काम:** network packets/capture files को analyze करना.

Install:

```bash
sudo apt install wireshark -y
```

Version:

```bash
wireshark --version
```

> NetHunter Rootless/Android में live capture permissions और GUI support limited हो सकते हैं. ऐसे case में saved `.pcap` files analyze करना बेहतर practical है.

CLI companion:

```bash
sudo apt install tshark -y
tshark --version
```

Capture file पढ़ना:

```bash
tshark -r sample.pcap
```

पहले 10 packets:

```bash
tshark -r sample.pcap -c 10
```

HTTP display filter:

```bash
tshark -r sample.pcap -Y http
```

---

# 3. tcpdump — Command-Line Packet Capture

**काम:** network packets capture/read करना.

Install:

```bash
sudo apt install tcpdump -y
```

Interfaces:

```bash
tcpdump -D
```

Existing capture पढ़ना:

```bash
tcpdump -r sample.pcap
```

First 10 packets:

```bash
tcpdump -c 10 -r sample.pcap
```

> Live capture के लिए privileges/interface access चाहिए; Rootless Android में restrictions सामान्य हैं.

---

# 4. dig / nslookup — DNS Troubleshooting

Install:

```bash
sudo apt install dnsutils -y
```

Lookup:

```bash
nslookup example.com
```

Detailed DNS query:

```bash
dig example.com
```

Short result:

```bash
dig +short example.com
```

Record type:

```bash
dig example.com A
dig example.com AAAA
```

**Use:** DNS resolve हो रहा है या नहीं, यह समझने के लिए.

---

# 5. curl — HTTP/HTTPS Testing

Install:

```bash
sudo apt install curl -y
```

Headers:

```bash
curl -I https://example.com
```

Verbose connection:

```bash
curl -v https://example.com
```

Status code:

```bash
curl -o /dev/null -s -w "%{http_code}\n" https://example.com
```

Local lab:

```bash
curl http://127.0.0.1:8000
```

---

# 6. wget — File Download

Install:

```bash
sudo apt install wget -y
```

Download:

```bash
wget https://example.com
```

Custom filename:

```bash
wget -O example.html https://example.com
```

Check:

```bash
ls -lh example.html
file example.html
```

---

# 7. Netcat (nc) — Local TCP/UDP Testing

Install:

```bash
sudo apt install netcat-openbsd -y
```

Help:

```bash
nc -h
```

### Safe localhost chat lab

Terminal 1:

```bash
nc -l 127.0.0.1 9000
```

Terminal 2:

```bash
nc 127.0.0.1 9000
```

अब दोनों terminals में text type करके localhost connection समझ सकते हैं.

Stop:

```text
Ctrl + C
```

---

# 8. ss — Sockets & Listening Ports

Listening TCP/UDP ports:

```bash
ss -tuln
```

Active connections:

```bash
ss -tun
```

Port 8000:

```bash
ss -tuln | grep 8000
```

**Use:** कौन-सी local service किस port पर listen कर रही है.

---

# 9. traceroute — Network Path

Install:

```bash
sudo apt install traceroute -y
```

Run:

```bash
traceroute example.com
```

> कुछ networks intermediate hops को block/filter करते हैं, इसलिए `*` दिखना हमेशा fault नहीं है.

---

# 10. whois — Domain Registration Information

Install:

```bash
sudo apt install whois -y
```

Public documentation domain example:

```bash
whois example.com
```

Output लंबा हो तो:

```bash
whois example.com | less
```

---

# 11. OpenSSL — TLS/Certificate Inspection

Version:

```bash
openssl version
```

Install अगर missing हो:

```bash
sudo apt install openssl -y
```

TLS connection/certificate inspection:

```bash
openssl s_client -connect example.com:443 -servername example.com
```

Certificate dates:

```bash
echo | openssl s_client -connect example.com:443 -servername example.com 2>/dev/null | openssl x509 -noout -dates
```

---

# 12. Nikto — Web Server Assessment

**काम:** web server configuration और known issue patterns check करना.

Install:

```bash
sudo apt install nikto -y
```

Help:

```bash
nikto -Help
```

### Safe local lab

पहले localhost server:

```bash
python3 -m http.server 8000 --bind 127.0.0.1
```

दूसरे terminal में:

```bash
nikto -h http://127.0.0.1:8000
```

> केवल अपने web server/lab पर use करें.

---

# 13. Burp Suite — Web Application Testing Proxy

Install:

```bash
sudo apt install burpsuite -y
```

Start:

```bash
burpsuite
```

**Basic learning flow:**

1. Burp start करें.
2. Proxy tab समझें.
3. अपना local test app/browser proxy configure करें.
4. केवल localhost/lab HTTP request intercept करें.
5. Request headers और parameters observe करें.

> NetHunter Rootless में GUI/VNC setup की जरूरत पड़ सकती है. Authorization के बिना third-party traffic intercept न करें.

---

# 14. SQLMap — SQL Injection Testing

Install:

```bash
sudo apt install sqlmap -y
```

Version/help:

```bash
sqlmap --version
sqlmap -h
```

SQLMap automated security testing tool है. इसे public/third-party websites पर बिना written permission use न करें.

**Safe learning:** DVWA, WebGoat या अपने intentionally vulnerable local lab पर ही practice करें.

Basic lab syntax pattern:

```text
sqlmap -u "http://127.0.0.1:PORT/your-lab-endpoint?id=1"
```

यह placeholder है; exact endpoint आपके own lab पर depend करेगा.

---

# 15. John the Ripper — Password Hash Auditing

Install:

```bash
sudo apt install john -y
```

Check:

```bash
john --list=formats
```

**काम:** अपने test hashes/password policy की strength audit करना.

> केवल अपने test hashes या explicitly authorized password audit में use करें. किसी दूसरे व्यक्ति के credentials crack करने के लिए नहीं.

Safe commands सीखने के लिए:

```bash
john --help
```

कुछ Kali builds में:

```bash
john
```

usage screen दिखाता है.

---

# 16. Hashcat — Password Recovery/Auditing

Install:

```bash
sudo apt install hashcat -y
```

Check:

```bash
hashcat --version
hashcat --help
```

Benchmark support environment पर depend करता है. Android/Rootless में GPU acceleration उपलब्ध न भी हो सकती है.

> अपने hashes/lab data तक सीमित रखें.

---

# 17. Metasploit Framework — Security Testing Framework

Install:

```bash
sudo apt install metasploit-framework -y
```

Start console:

```bash
msfconsole
```

Basic console navigation:

```text
help
search
info
show options
back
exit
```

Safe example:

```text
search type:auxiliary
```

Metasploit powerful exploitation framework है. Exploit/payload steps केवल intentionally vulnerable local VM/lab/CTF पर करें.

---

# 18. Gobuster — Content/DNS Enumeration

Install:

```bash
sudo apt install gobuster -y
```

Help:

```bash
gobuster --help
gobuster dir --help
```

Safe use: अपने local web lab में known test wordlist के साथ directory discovery सीखें.

Example pattern:

```text
gobuster dir -u http://127.0.0.1:PORT -w YOUR_TEST_WORDLIST
```

Third-party sites पर बिना permission enumeration न करें.

---

# 19. WhatWeb — Web Technology Identification

Install:

```bash
sudo apt install whatweb -y
```

Public documentation example:

```bash
whatweb https://example.com
```

Local server:

```bash
whatweb http://127.0.0.1:8000
```

**काम:** server/web technology fingerprints की basic information देखना.

---

# 20. Searchsploit — Exploit-DB Offline Search

Install:

```bash
sudo apt install exploitdb -y
```

Help:

```bash
searchsploit -h
```

Search example:

```bash
searchsploit apache
```

**काम:** known vulnerability/exploit references को offline database में search करना.

> Search result मिलना यह साबित नहीं करता कि कोई system vulnerable है. Exploit execution केवल authorized lab में करें.

---

# 21. Kali Metapackages

Kali tools groups search:

```bash
apt search kali-tools
```

Examples environment/version के हिसाब से available हो सकते हैं:

```text
kali-tools-information-gathering
kali-tools-vulnerability
kali-tools-web
kali-tools-passwords
kali-tools-sniffing-spoofing
kali-tools-forensics
```

Package details पहले देखें:

```bash
apt show kali-tools-web
```

फिर जरूरत होने पर install करें:

```bash
sudo apt install kali-tools-web
```

> सारे Kali tools एक साथ install करना phone storage/RAM के लिए अक्सर शानदार बुरा idea है. जरूरत के हिसाब से tools install करें.

---

# 22. Rootless NetHunter Limitations

Android + NetHunter Rootless में कुछ tools/features सीमित हो सकते हैं:

- raw Wi-Fi monitor mode
- packet injection
- USB Wi-Fi adapter access
- kernel-level features
- some privileged network operations
- GPU acceleration
- desktop GUI without KeX/VNC

Command fail होने का मतलब हमेशा गलत installation नहीं होता; Android/kernel permissions भी कारण हो सकते हैं.

---

# 23. Recommended Learning Order

```text
1. ip / ss / ping
2. dig / nslookup
3. curl / wget
4. nmap on localhost/lab
5. tcpdump / Wireshark with own capture
6. OpenSSL
7. local web lab
8. Burp Suite
9. Nikto / WhatWeb
10. intentionally vulnerable lab tools
11. password auditing with your own hashes
12. Metasploit only in authorized vulnerable labs
```

---

# 🧪 Combined Safe Lab

Terminal 1:

```bash
mkdir -p ~/kali-tools-lab
cd ~/kali-tools-lab
echo "Termux-Sathi Kali Tools Lab" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:

```bash
curl -I http://127.0.0.1:8000
ss -tuln | grep 8000
nmap -sV -p 8000 127.0.0.1
whatweb http://127.0.0.1:8000
```

इस lab से HTTP, ports, service detection और web fingerprinting अपने device पर safely समझ सकते हैं.

---

# 🎯 आगे क्या जोड़ेंगे

अगले versions में tools को अलग-अलग practical chapters में expand किया जा सकता है:

- Nmap deep practical
- Wireshark filters
- Burp Suite local web lab
- Web security lab setup
- Digital forensics tools
- OSINT tools
- Log analysis
- Defensive monitoring
- CTF practice workflow


---

# 📦 Complete Kali Toolsets / Metapackages

Kali ke hundreds of tools ko category-wise install karne ka official tareeka metapackages hain.

## Almost every Kali tool

```bash
sudo apt update
sudo apt install kali-linux-everything -y
```

> Ye bahut bada installation hai. Android/NetHunter Rootless me storage, RAM, GUI aur hardware restrictions ko dhyan me rakho.

## Main system collections

```bash
sudo apt install kali-linux-core -y
sudo apt install kali-linux-headless -y
sudo apt install kali-linux-default -y
sudo apt install kali-linux-large -y
sudo apt install kali-linux-arm -y
sudo apt install kali-linux-nethunter -y
sudo apt install kali-tools-top10 -y
```

## Complete category collections

### Information Gathering
```bash
sudo apt install kali-tools-information-gathering -y
```

### Vulnerability Analysis
```bash
sudo apt install kali-tools-vulnerability -y
```

### Web Applications
```bash
sudo apt install kali-tools-web -y
```

### Database
```bash
sudo apt install kali-tools-database -y
```

### Password Auditing
```bash
sudo apt install kali-tools-passwords -y
```

### Wireless
```bash
sudo apt install kali-tools-wireless -y
```

### Wi-Fi / 802.11
```bash
sudo apt install kali-tools-802-11 -y
```

### Bluetooth
```bash
sudo apt install kali-tools-bluetooth -y
```

### RFID
```bash
sudo apt install kali-tools-rfid -y
```

### Software Defined Radio
```bash
sudo apt install kali-tools-sdr -y
```

### Reverse Engineering
```bash
sudo apt install kali-tools-reverse-engineering -y
```

### Exploitation Frameworks
```bash
sudo apt install kali-tools-exploitation -y
```

### Social Engineering
```bash
sudo apt install kali-tools-social-engineering -y
```

### Sniffing / Spoofing
```bash
sudo apt install kali-tools-sniffing-spoofing -y
```

### Post Exploitation
```bash
sudo apt install kali-tools-post-exploitation -y
```

### Digital Forensics
```bash
sudo apt install kali-tools-forensics -y
```

### Reporting
```bash
sudo apt install kali-tools-reporting -y
```

### Fuzzing
```bash
sudo apt install kali-tools-fuzzing -y
```

### Cryptography / Steganography
```bash
sudo apt install kali-tools-crypto-stego -y
```

### Hardware
```bash
sudo apt install kali-tools-hardware -y
```

### GPU
```bash
sudo apt install kali-tools-gpu -y
```

### VoIP
```bash
sudo apt install kali-tools-voip -y
```

### Windows Resources
```bash
sudo apt install kali-tools-windows-resources -y
```

### Kali Practice Labs
```bash
sudo apt install kali-linux-labs -y
```

## Apne current Kali me available categories dekho

```bash
apt search '^kali-tools-'
apt search '^kali-linux-'
```

Installed metapackages:

```bash
dpkg -l | grep -E 'kali-tools|kali-linux'
```

## Important Android / NetHunter Rootless Note

Package install ho jana aur feature ka hardware-level par kaam karna alag baat hai. Monitor mode, packet injection, raw Bluetooth, RFID, SDR, USB aur GPU tools ko compatible hardware/kernel/permissions chahiye ho sakte hain.

Safe learning order:

```text
Linux → Networking → Nmap → HTTP/Web → Packet Analysis
→ Forensics → Reverse Engineering → Vulnerability Assessment
→ Authorized Lab Testing → Reporting
```

> Offensive categories ke commands ko apne isolated lab/CTF tak rakho. Tool ka naam install kar lena permission ka substitute nahi hota.


---

# 🧪 Practical Chapters — Batch 1

Is section me commands ko khud run karke tool ka output samjhenge.

## Practical Lab Setup

Terminal 1:

```bash
mkdir -p ~/kali-tools-lab
cd ~/kali-tools-lab
echo '<h1>Termux-Sathi Lab</h1>' > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:

```bash
curl http://127.0.0.1:8000
```

Expected text:

```text
<h1>Termux-Sathi Lab</h1>
```

---

## Tool 1 — Nmap

Install:

```bash
sudo apt install nmap -y
```

Purpose: apne host/lab ke ports aur services identify karna.

Basic localhost:

```bash
nmap 127.0.0.1
```

Specific lab port:

```bash
nmap -p 8000 127.0.0.1
```

Service detection on local lab:

```bash
nmap -sV -p 8000 127.0.0.1
```

Expected idea:

```text
8000/tcp open  http
```

Useful options:

```text
-p     port choose
-sV    service/version detection
-oN    normal output file
```

Save result:

```bash
nmap -sV -p 8000 127.0.0.1 -oN nmap-result.txt
cat nmap-result.txt
```

---

## Tool 2 — curl

Install:

```bash
sudo apt install curl -y
```

Fetch local page:

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

Save:

```bash
curl -o page.html http://127.0.0.1:8000
cat page.html
```

---

## Tool 3 — wget

Install:

```bash
sudo apt install wget -y
```

Download local page:

```bash
wget -O downloaded.html http://127.0.0.1:8000
cat downloaded.html
```

Purpose: HTTP/HTTPS/FTP resources download karna.

---

## Tool 4 — WhatWeb

Install:

```bash
sudo apt install whatweb -y
```

Own local server identify:

```bash
whatweb http://127.0.0.1:8000
```

Purpose: web technology/fingerprint information collect karna.

---

## Tool 5 — Nikto

Install:

```bash
sudo apt install nikto -y
```

Help:

```bash
nikto -Help
```

Safe target rule: Nikto automated web checks karta hai, isliye practice intentionally vulnerable/local web lab par hi karein.

---

## Tool 6 — DNS: dig

Install:

```bash
sudo apt install dnsutils -y
```

Lookup:

```bash
dig example.com
```

Short answer:

```bash
dig +short example.com
```

Record type:

```bash
dig example.com A
```

Purpose: DNS resolution aur records troubleshoot karna.

---

## Tool 7 — nslookup

```bash
nslookup example.com
```

Purpose: simple domain/IP DNS lookup.

---

## Tool 8 — ss

Listening sockets:

```bash
ss -tuln
```

Port 8000:

```bash
ss -tuln | grep 8000
```

Purpose: local sockets aur listening services inspect karna.

---

## Tool 9 — tcpdump

Install:

```bash
sudo apt install tcpdump -y
```

Version:

```bash
tcpdump --version
```

Interfaces:

```bash
tcpdump -D
```

Offline PCAP read:

```bash
tcpdump -r capture.pcap
```

> Capture sirf apne/authorized traffic ka karein. Rootless Android me live capture permissions limited ho sakti hain.

---

## Tool 10 — tshark

Install:

```bash
sudo apt install tshark -y
```

Version:

```bash
tshark --version
```

Offline PCAP:

```bash
tshark -r capture.pcap
```

Packet count:

```bash
tshark -r capture.pcap | wc -l
```

Purpose: Wireshark ka command-line packet analyzer.

---

## Tool 11 — Wireshark

Install:

```bash
sudo apt install wireshark -y
```

Version:

```bash
wireshark --version
```

GUI start:

```bash
wireshark
```

NetHunter Rootless me GUI ke liye KeX/VNC desktop ki zarurat ho sakti hai.

---

## Tool 12 — Netcat

Install:

```bash
sudo apt install netcat-openbsd -y
```

Terminal 1, localhost listener:

```bash
nc -l 127.0.0.1 9000
```

Terminal 2:

```bash
nc 127.0.0.1 9000
```

Ab dono terminals me simple text type karke local TCP connection samjho.

> Is chapter me Netcat ko local networking lab ke liye use kiya gaya hai, remote shells ke liye nahi.

---

## Tool 13 — OpenSSL

Version:

```bash
openssl version
```

SHA-256:

```bash
echo "Termux-Sathi" > sample.txt
openssl dgst -sha256 sample.txt
```

Random bytes:

```bash
openssl rand -hex 16
```

Purpose: cryptography/TLS utilities aur hashing.

---

## Tool 14 — sha256sum

```bash
echo "Termux-Sathi" > evidence.txt
sha256sum evidence.txt
```

Change file:

```bash
echo "changed" >> evidence.txt
sha256sum evidence.txt
```

Hash change hoga. Forensics me integrity verify karne ka basic concept yahi hai.

---

## Tool 15 — file

```bash
file evidence.txt
file /bin/bash
```

Purpose: file type identify karna.

---

## Tool 16 — strings

```bash
strings /bin/ls | head
```

Purpose: binary/file ke printable strings inspect karna.

---

## Tool 17 — Binwalk

Install:

```bash
sudo apt install binwalk -y
```

Help:

```bash
binwalk --help
```

Own/training firmware sample inspect:

```bash
binwalk firmware.bin
```

Purpose: firmware/binary me embedded signatures/data identify karna.

---

## Tool 18 — ExifTool

Install:

```bash
sudo apt install libimage-exiftool-perl -y
```

Metadata:

```bash
exiftool image.jpg
```

Purpose: image/document metadata inspect karna.

---

## Tool 19 — GDB

Install:

```bash
sudo apt install gdb -y
```

Version:

```bash
gdb --version
```

Simple own program:

```bash
cat > hello.c <<'EOF'
#include <stdio.h>
int main() {
    printf("Termux-Sathi\\n");
    return 0;
}
EOF

sudo apt install gcc -y
gcc -g hello.c -o hello
gdb ./hello
```

Inside GDB:

```text
break main
run
list
quit
```

Purpose: apne/training programs ko debug aur analyze karna.

---

## Tool 20 — Radare2

Install:

```bash
sudo apt install radare2 -y
```

Open own binary:

```bash
r2 ./hello
```

Inside:

```text
aaa
afl
q
```

Purpose: reverse-engineering framework. Practice own binaries/CTF samples par karein.

---

# 🔐 Controlled-Lab Security Tools

Neeche ke tools powerful hain. Inka beginner chapter installation, help aur authorized lab workflow tak limited hai.

## Tool 21 — SQLMap

```bash
sudo apt install sqlmap -y
sqlmap --version
sqlmap --help
```

Purpose: SQL injection testing automation.

Practice: intentionally vulnerable local apps/CTFs only. Real websites par automated testing permission ke bina mat karein.

---

## Tool 22 — John the Ripper

```bash
sudo apt install john -y
john --help
```

Purpose: password/hash auditing.

Practice only with hashes/passwords created specifically for your own lab.

---

## Tool 23 — Hashcat

```bash
sudo apt install hashcat -y
hashcat --version
hashcat --help
```

Purpose: password/hash auditing. Rootless Android me GPU acceleration unavailable ho sakti hai.

---

## Tool 24 — Hydra

```bash
sudo apt install hydra -y
hydra -h
```

Purpose: authentication auditing.

Practice only against an intentionally configured local training service with test credentials. Third-party account guessing is not a lab.

---

## Tool 25 — Metasploit Framework

```bash
sudo apt install metasploit-framework -y
msfconsole
```

Inside:

```text
version
help
exit
```

Purpose: authorized penetration-testing framework.

Exploit/payload exercises ko isolated intentionally vulnerable VM/CTF tak rakhein.

---

## Tool 26 — Searchsploit

```bash
sudo apt install exploitdb -y
searchsploit --help
searchsploit apache
```

Purpose: local Exploit-DB index search karna. Search result milna ye prove nahi karta ki koi system vulnerable hai.

---

## Tool 27 — Burp Suite

```bash
sudo apt install burpsuite -y
burpsuite
```

Purpose: web request/response inspection aur authorized web testing.

Safe first lab: apne local web application ka HTTP traffic inspect karein. GUI ke liye KeX/VNC required ho sakta hai.

---

# 🛠 Common Errors

### command not found

```bash
which TOOL
apt search TOOL
```

### package not found

```bash
sudo apt update
apt search TOOL
```

### permission denied

```bash
ls -l FILE
id
```

Blindly `chmod 777` mat lagao. Pehle permission problem samjho.

### GUI not opening

Rootless Kali me graphical tools ko KeX/VNC desktop/display configuration chahiye ho sakti hai.

### hardware feature missing

Wi-Fi monitor mode, Bluetooth, SDR, RFID, USB aur GPU tools ko compatible hardware/kernel access chahiye.

---

# 🎯 Batch 1 Challenge

1. Local Python server start karo.
2. Nmap se port 8000 verify karo.
3. curl se page fetch karo.
4. WhatWeb se local page inspect karo.
5. ss se listening socket dekho.
6. sample file ka SHA-256 nikalo.
7. file aur strings commands use karo.
8. own C program compile karke GDB me open karo.
9. Kali tool ke `--help` output ko read karna practice karo.
10. Har command ka output apne notes me explain karo.

## Next Practical Batch

Agla batch:

```text
Aircrack-ng fundamentals (hardware-safe)
Kismet basics
APKTool
JADX
YARA
Foremost
Autopsy
Wfuzz safe local lab
Gobuster safe local lab
Lynis system audit
Git-based security lab setup
KeX/GUI tools
```


---

# 🧪 Practical Chapters — Batch 2

Ye batch web testing, forensics, reverse engineering, local proxying aur wireless diagnostics cover karta hai. Sab practical apne localhost, apne files ya authorized lab tak rakhein.

## Tool 26 — Wireshark

**Kaam:** network packets ko graphical interface me analyze karna.

Install:

```bash
sudo apt install wireshark -y
```

Version:

```bash
wireshark --version
```

Saved PCAP open:

```bash
wireshark lab.pcap
```

Headless/Rootless Kali me GUI ke liye KeX/VNC/desktop environment chahiye ho sakta hai.

---

## Tool 27 — Gobuster

**Kaam:** web directories/files discover karna.

Install:

```bash
sudo apt install gobuster -y
```

Help:

```bash
gobuster -h
```

Local lab banao:

```bash
mkdir -p ~/gobuster-lab/admin
echo "home" > ~/gobuster-lab/index.html
echo "admin" > ~/gobuster-lab/admin/index.html
cd ~/gobuster-lab
python3 -m http.server 8000 --bind 127.0.0.1
```

Doosre terminal me wordlist:

```bash
printf "admin\nlogin\ntest\n" > words.txt
```

Local scan:

```bash
gobuster dir -u http://127.0.0.1:8000 -w words.txt
```

Expected idea:

```text
/admin
```

---

## Tool 28 — Wfuzz

Install:

```bash
sudo apt install wfuzz -y
```

Help:

```bash
wfuzz --help
```

Local-only fuzz:

```bash
printf "admin\nlogin\ntest\n" > words.txt
wfuzz -z file,words.txt --hc 404 http://127.0.0.1:8000/FUZZ
```

Ye intentionally local training server par resource discovery dikhata hai.

---

## Tool 29 — Burp Suite

Install:

```bash
sudo apt install burpsuite -y
```

Start:

```bash
burpsuite
```

Use case:

```text
Proxy
HTTP history
Repeater
Decoder
Web request inspection
```

Beginner practice ke liye apne localhost web app ko browser + Burp proxy ke through inspect karein.

> Doosre users ka traffic intercept karna ya unke accounts/sessions inspect karna is guide ka part nahi hai.

---

## Tool 30 — OWASP ZAP

Install:

```bash
sudo apt install zaproxy -y
```

Version/help:

```bash
zaproxy -version
zaproxy -h
```

GUI:

```bash
zaproxy
```

Local test target:

```text
http://127.0.0.1:8000
```

Automated scanning sirf own/local intentionally vulnerable lab par karein.

---

## Tool 31 — YARA

**Kaam:** files ko custom patterns/rules se identify/classify karna.

Install:

```bash
sudo apt install yara -y
```

Sample file:

```bash
echo "Termux-Sathi training sample" > sample.txt
```

Rule banao:

```bash
cat > sample.yar <<'EOF'
rule TermuxSathiSample {
    strings:
        $text = "Termux-Sathi"
    condition:
        $text
}
EOF
```

Run:

```bash
yara sample.yar sample.txt
```

Expected:

```text
TermuxSathiSample sample.txt
```

---

## Tool 32 — Foremost

**Kaam:** file signatures ke basis par recovery/carving.

Install:

```bash
sudo apt install foremost -y
```

Help:

```bash
foremost -h
```

Own disk image/file par:

```bash
foremost -i training.img -o recovered
```

> Real evidence par kaam karte waqt original image ko read-only preserve karna forensic best practice hai.

---

## Tool 33 — Autopsy

**Kaam:** forensic filesystem analysis ka browser-based interface.

Install:

```bash
sudo apt install autopsy -y
```

Start:

```bash
autopsy
```

Default local interface usually localhost par web UI provide karta hai.

Use only copied forensic images/test data par, original evidence ko modify na karein.

---

## Tool 34 — Radare2

Install:

```bash
sudo apt install radare2 -y
```

Version:

```bash
r2 -v
```

Apna test binary:

```bash
cat > demo.c <<'EOF'
#include <stdio.h>
int main() {
    puts("Termux-Sathi");
    return 0;
}
EOF

gcc demo.c -o demo
```

Analyze:

```bash
r2 -A ./demo
```

Inside r2:

```text
afl
pdf @ main
q
```

---

## Tool 35 — Rizin

Install:

```bash
sudo apt install rizin -y
```

Help:

```bash
rizin -h
```

Analyze own binary:

```bash
rizin -A ./demo
```

Inside:

```text
afl
pdf @ main
q
```

---

## Tool 36 — Aircrack-ng

Install:

```bash
sudo apt install aircrack-ng -y
```

Help:

```bash
aircrack-ng --help
```

Wireless interfaces:

```bash
iw dev
ip link
```

Rootless Android me monitor mode/packet injection generally supported external adapter + compatible kernel/USB access par depend karta hai.

> Wi-Fi key cracking ya third-party networks par attacks is beginner guide me cover nahi kiye gaye hain.

---

## Tool 37 — Kismet

Install:

```bash
sudo apt install kismet -y
```

Version/help:

```bash
kismet --version
kismet -h
```

Capture hardware detection support environment-specific hota hai.

Rootless NetHunter me Android permissions aur hardware access restrictions ki wajah se source detect na ho sakta hai.

---

## Tool 38 — Bettercap

Install:

```bash
sudo apt install bettercap -y
```

Version/help:

```bash
bettercap -version
bettercap -help
```

Bettercap powerful network assessment framework hai. Is guide me active interception/spoofing commands intentionally include nahi kiye gaye.

---

## Tool 39 — mitmproxy

Install:

```bash
sudo apt install mitmproxy -y
```

Version:

```bash
mitmproxy --version
```

Local-only proxy:

```bash
mitmproxy --listen-host 127.0.0.1 -p 8080
```

Ye apne browser/app ke HTTP debugging ke liye use kiya ja sakta hai.

Stop:

```text
Ctrl + C
```

---

## Tool 40 — Netcat

Install:

```bash
sudo apt install netcat-traditional -y
```

Help:

```bash
nc.traditional -h
```

Safe loopback listener:

Terminal 1:

```bash
nc.traditional -l -p 9000
```

Terminal 2:

```bash
echo "Hello Termux-Sathi" | nc.traditional 127.0.0.1 9000
```

Terminal 1 par message dikh jayega.

---

## Tool 41 — OpenSSL

Check:

```bash
openssl version
```

Hash:

```bash
echo "Termux-Sathi" | openssl dgst -sha256
```

Random bytes:

```bash
openssl rand -hex 16
```

Local certificate details inspect:

```bash
openssl x509 -in certificate.pem -text -noout
```

---

## Tool 42 — Steghide

Install:

```bash
sudo apt install steghide -y
```

Help:

```bash
steghide --help
```

Image metadata/info check:

```bash
steghide info training.jpg
```

Practice sirf apni files par karein.

---

# ✅ Batch 2 Practice Challenge

1. Wireshark me apna saved PCAP open karo.
2. Local Python server par Gobuster run karo.
3. Wfuzz se same local paths test karo.
4. Burp/ZAP launch karke localhost request inspect karo.
5. YARA rule se apna sample file match karo.
6. Apne compiled binary ko Radare2/Rizin me inspect karo.
7. Netcat se loopback message bhejo.
8. OpenSSL se SHA-256 hash generate karo.
9. Aircrack/Kismet ke help aur local hardware interfaces inspect karo.
10. mitmproxy ko localhost-only mode me start karo.


---

# 🧪 Practical Chapters — Batch 3

Is batch me VPN/proxy basics, malware scanning, reverse engineering, digital forensics, disk imaging, SSH, tmux, SQLite aur troubleshooting tools cover honge.

> ⚠️ Network/privacy tools ko access-control bypass ke liye use mat karein. Disk imaging commands ko real storage devices par tabhi chalayein jab device/path 100% verify ho.

## Tool 43 — OpenVPN

**Kaam:** VPN configuration ke through authorized network se securely connect karna.

Install:

```bash
sudo apt install openvpn -y
```

Version:

```bash
openvpn --version
```

Config test:

```bash
openvpn --config training.ovpn
```

Use only VPN configuration jo aapki ho ya jiske use ki permission ho.

Stop:

```text
Ctrl + C
```

---

## Tool 44 — Tor

Install:

```bash
sudo apt install tor -y
```

Version:

```bash
tor --version
```

Start service where supported:

```bash
sudo service tor start
```

Status:

```bash
sudo service tor status
```

Rootless/proot environments me service management limited ho sakta hai.

Tor privacy network hai, permission controls bypass karne ka license nahi.

---

## Tool 45 — ProxyChains

Install:

```bash
sudo apt install proxychains4 -y
```

Config location:

```bash
ls -l /etc/proxychains4.conf
```

Help:

```bash
proxychains4 -h
```

Apne configured proxy ke through harmless connectivity test:

```bash
proxychains4 curl https://example.com
```

Proxy configuration valid hona zaroori hai.

---

## Tool 46 — ClamAV

**Kaam:** malware/signature-based file scanning.

Install:

```bash
sudo apt install clamav -y
```

Version:

```bash
clamscan --version
```

Definitions update where supported:

```bash
sudo freshclam
```

Own folder scan:

```bash
mkdir -p ~/scan-lab
echo "Termux-Sathi safe sample" > ~/scan-lab/sample.txt
clamscan -r ~/scan-lab
```

Summary only:

```bash
clamscan -r --infected ~/scan-lab
```

---

## Tool 47 — Ghidra

**Kaam:** reverse engineering aur binary analysis.

Install:

```bash
sudo apt install ghidra -y
```

Start:

```bash
ghidra
```

GUI/desktop environment required ho sakta hai.

Practice binary:

```bash
cat > ghidra-demo.c <<'EOF'
#include <stdio.h>
int main() {
    puts("Termux-Sathi Ghidra Lab");
    return 0;
}
EOF

gcc ghidra-demo.c -o ghidra-demo
```

Is apne compiled binary ko Ghidra project me import karke analyze karein.

---

## Tool 48 — Sleuth Kit

Install:

```bash
sudo apt install sleuthkit -y
```

Version/help:

```bash
fls -V
fls -h
```

Useful tools:

```text
fls     = filesystem entries
icat    = file content extraction
mmls    = partition layout
fsstat  = filesystem details
```

Use copied disk images/test images par.

---

## Tool 49 — dd

**Kaam:** raw byte-level copy.

Version/help:

```bash
dd --version
```

Safe test-file practical:

```bash
dd if=/dev/zero of=training.img bs=1M count=10 status=progress
```

Check:

```bash
ls -lh training.img
```

Copy:

```bash
dd if=training.img of=training-copy.img bs=1M status=progress
```

Verify:

```bash
sha256sum training.img training-copy.img
```

> `dd` me wrong `of=` path real disk/data overwrite kar sakta hai. Real block devices par bina full verification use mat karein.

---

## Tool 50 — GNU ddrescue

**Kaam:** failing/damaged media se recoverable data copy karna.

Install:

```bash
sudo apt install gddrescue -y
```

Version:

```bash
ddrescue --version
```

Safe file-to-file practice:

```bash
ddrescue training.img rescued.img rescue.log
```

Check:

```bash
ls -lh rescued.img rescue.log
```

Resume capability ke liye map/log file useful hoti hai.

---

## Tool 51 — ExifTool Advanced

Install:

```bash
sudo apt install libimage-exiftool-perl -y
```

All metadata:

```bash
exiftool training.jpg
```

Specific fields:

```bash
exiftool -FileName -FileSize -MIMEType training.jpg
```

Recursive scan:

```bash
exiftool -r ~/Pictures
```

Metadata may contain private location/device information, so reports share karte waqt review karein.

---

## Tool 52 — SQLite

Install:

```bash
sudo apt install sqlite3 -y
```

Version:

```bash
sqlite3 --version
```

Database create:

```bash
sqlite3 termux_sathi.db
```

Inside SQLite:

```sql
CREATE TABLE notes(
    id INTEGER PRIMARY KEY,
    text TEXT
);

INSERT INTO notes(text) VALUES('Kali tools practice');
SELECT * FROM notes;
.tables
.quit
```

CLI one-liner:

```bash
sqlite3 termux_sathi.db 'SELECT * FROM notes;'
```

---

## Tool 53 — tmux

**Kaam:** ek terminal ke andar multiple persistent terminal sessions.

Install:

```bash
sudo apt install tmux -y
```

Start:

```bash
tmux
```

Named session:

```bash
tmux new -s kali-lab
```

Detach:

```text
Ctrl+b, then d
```

Sessions:

```bash
tmux ls
```

Reattach:

```bash
tmux attach -t kali-lab
```

---

## Tool 54 — SSH Client

Install:

```bash
sudo apt install openssh-client -y
```

Version:

```bash
ssh -V
```

Connect to your own authorized server:

```bash
ssh username@SERVER_IP
```

Custom port:

```bash
ssh -p 2222 username@SERVER_IP
```

Verbose troubleshooting:

```bash
ssh -v username@SERVER_IP
```

---

## Tool 55 — SSH Server

Install:

```bash
sudo apt install openssh-server -y
```

Config syntax test:

```bash
sudo sshd -t
```

Service start where supported:

```bash
sudo service ssh start
```

Listening port:

```bash
ss -tln | grep ':22'
```

Rootless/proot Android me incoming networking/service behavior platform restrictions se affected ho sakta hai.

---

## Tool 56 — SSH Keys

Key pair:

```bash
ssh-keygen -t ed25519
```

Public key:

```bash
cat ~/.ssh/id_ed25519.pub
```

Permissions:

```bash
chmod 700 ~/.ssh
chmod 600 ~/.ssh/id_ed25519
```

> Private key `id_ed25519` kabhi share/upload mat karein. Public key `.pub` sharing ke liye hoti hai.

---

## Tool 57 — Git

Install:

```bash
sudo apt install git -y
```

Version:

```bash
git --version
```

Local-only practice:

```bash
mkdir -p ~/git-lab
cd ~/git-lab
git init
echo "Termux-Sathi" > README.md
git add README.md
git status
```

Identity configure karne ke baad:

```bash
git commit -m "Initial lab commit"
git log --oneline
```

---

## Tool 58 — Docker / Container Basics

Kali package where supported:

```bash
sudo apt install docker.io -y
```

Version:

```bash
docker --version
```

Status where system service support exists:

```bash
sudo service docker status
```

> NetHunter Rootless/proot Android me Docker daemon usually required kernel/cgroup capabilities ke bina kaam nahi karega. Package install hona support guarantee nahi hai.

---

# 🔧 Kali Troubleshooting Toolkit

## A. Command not found

```bash
which TOOL
command -v TOOL
apt search TOOL
```

## B. Package install error

```bash
sudo apt update
sudo apt --fix-broken install
sudo dpkg --configure -a
```

## C. Disk space

```bash
df -h
du -sh ~
du -h ~ | sort -h | tail
```

APT cache:

```bash
sudo apt clean
```

## D. Memory

```bash
free -h
ps aux --sort=-%mem | head
```

## E. CPU/process issue

```bash
top
ps aux --sort=-%cpu | head
```

## F. Network

```bash
ip addr
ip route
ping -c 4 1.1.1.1
nslookup example.com
curl -I https://example.com
```

## G. DNS

```bash
cat /etc/resolv.conf
dig example.com
```

## H. Port/service

```bash
ss -tuln
```

## I. Permission denied

```bash
ls -l FILE
id
```

Executable script:

```bash
chmod +x script.sh
```

## J. Broken shell config

```bash
bash -n ~/.bashrc
```

Backup restore:

```bash
cp ~/.bashrc.backup ~/.bashrc
source ~/.bashrc
```

## K. Package information

```bash
apt policy PACKAGE
apt show PACKAGE
dpkg -l | grep PACKAGE
```

## L. Logs

Traditional logs where available:

```bash
ls -lah /var/log
```

Recent kernel output access may be restricted:

```bash
dmesg | tail
```

Rootless environments can deny kernel logs.

---

# ✅ Batch 3 Practice Challenge

1. OpenVPN version check karo.
2. Tor package/version inspect karo.
3. ClamAV se apna test folder scan karo.
4. Ghidra me apna compiled binary import karo.
5. `dd` se 10 MB training image banao.
6. SHA-256 se original/copy verify karo.
7. ddrescue se test image copy karo.
8. SQLite database/table banao.
9. tmux session create-detach-attach karo.
10. SSH key pair banao aur private/public key ka difference samjho.
11. Local Git repo me first commit banao.
12. Troubleshooting flow se disk, RAM aur network check karo.


---

# 🧪 Practical Chapters — Batch 4

Is batch me system administration, diagnostics, logs, automation aur maintenance tools cover honge.

## Tool 59 — rsync

**Kaam:** files/folders efficiently copy aur synchronize karna.

Install:

```bash
sudo apt install rsync -y
```

Version:

```bash
rsync --version
```

Safe local practice:

```bash
mkdir -p ~/rsync-source ~/rsync-backup
echo "Termux-Sathi backup test" > ~/rsync-source/file.txt
rsync -av ~/rsync-source/ ~/rsync-backup/
```

Verify:

```bash
ls -lah ~/rsync-backup
cat ~/rsync-backup/file.txt
```

Dry run:

```bash
rsync -av --dry-run ~/rsync-source/ ~/rsync-backup/
```

---

## Tool 60 — rsyslog

Install:

```bash
sudo apt install rsyslog -y
```

Version:

```bash
rsyslogd -v
```

Common log directory:

```bash
ls -lah /var/log
```

Search errors:

```bash
grep -Ri "error" /var/log 2>/dev/null | head
```

Rootless environments me service/log access limited ho sakta hai.

---

## Tool 61 — journalctl

Systemd-based environments me:

```bash
journalctl --version
```

Recent logs:

```bash
journalctl -n 50
```

Current boot:

```bash
journalctl -b
```

Errors:

```bash
journalctl -p err
```

> NetHunter Rootless/proot me systemd journal available na ho sakta hai.

---

## Tool 62 — lsof

**Kaam:** kaunsi process kaunsi file/socket use kar rahi hai.

Install:

```bash
sudo apt install lsof -y
```

Version/help:

```bash
lsof -v
```

Current user files:

```bash
lsof -u "$USER" | head
```

Port 8000:

```bash
lsof -i :8000
```

---

## Tool 63 — strace

**Kaam:** program ke system calls trace karna.

Install:

```bash
sudo apt install strace -y
```

Version:

```bash
strace -V
```

Simple command trace:

```bash
strace ls
```

Output file:

```bash
strace -o trace.txt ls
head trace.txt
```

Summary:

```bash
strace -c ls
```

---

## Tool 64 — ltrace

**Kaam:** dynamic library calls trace karna.

Install:

```bash
sudo apt install ltrace -y
```

Version:

```bash
ltrace --version
```

Test:

```bash
ltrace /bin/echo "Termux-Sathi"
```

---

## Tool 65 — htop

Install:

```bash
sudo apt install htop -y
```

Run:

```bash
htop
```

Useful for CPU, RAM aur processes monitor karna.

Exit:

```text
F10
```

---

## Tool 66 — iotop

Install:

```bash
sudo apt install iotop -y
```

Run:

```bash
sudo iotop
```

> Kernel permissions/features ki wajah se Rootless Android me iotop limited ho sakta hai.

---

## Tool 67 — ncdu

**Kaam:** disk usage ko interactive way me inspect karna.

Install:

```bash
sudo apt install ncdu -y
```

Run:

```bash
ncdu ~
```

Large files/folders identify karne me useful.

---

## Tool 68 — jq

**Kaam:** JSON data parse/filter karna.

Install:

```bash
sudo apt install jq -y
```

Version:

```bash
jq --version
```

Sample JSON:

```bash
echo '{"name":"Termux-Sathi","tool":"jq","active":true}' > sample.json
```

Pretty print:

```bash
jq . sample.json
```

Single field:

```bash
jq -r '.name' sample.json
```

---

## Tool 69 — ripgrep

Install:

```bash
sudo apt install ripgrep -y
```

Version:

```bash
rg --version
```

Search:

```bash
rg "Termux-Sathi" ~
```

File type filter:

```bash
rg "import" --type py .
```

---

## Tool 70 — GNU screen

Install:

```bash
sudo apt install screen -y
```

Start:

```bash
screen
```

Named session:

```bash
screen -S kali-lab
```

Detach:

```text
Ctrl+a, then d
```

List:

```bash
screen -ls
```

Reattach:

```bash
screen -r kali-lab
```

---

## Tool 71 — cron

Install:

```bash
sudo apt install cron -y
```

User crontab:

```bash
crontab -e
```

List:

```bash
crontab -l
```

Example: har din 08:00 par harmless log entry:

```cron
0 8 * * * echo "Termux-Sathi cron test" >> $HOME/cron-test.log
```

> Rootless environment me cron daemon automatic start na ho sakta hai.

---

## Tool 72 — service command

Available services:

```bash
service --status-all 2>/dev/null
```

Specific service:

```bash
sudo service ssh status
sudo service ssh start
sudo service ssh stop
```

Systemd unavailable hone par traditional service scripts kaam kar sakte hain.

---

## Tool 73 — systemctl concept

Check:

```bash
systemctl --version
```

Service status:

```bash
systemctl status ssh
```

Enable:

```bash
sudo systemctl enable ssh
```

> NetHunter Rootless/proot me systemd PID 1 na hone ki wajah se `systemctl` fail kar sakta hai.

---

## Tool 74 — Python venv

Install support:

```bash
sudo apt install python3-venv -y
```

Create environment:

```bash
python3 -m venv ~/venvs/kali-lab
```

Activate:

```bash
source ~/venvs/kali-lab/bin/activate
```

Check:

```bash
which python
python --version
```

Deactivate:

```bash
deactivate
```

---

## Tool 75 — pipx

Install:

```bash
sudo apt install pipx -y
```

Setup path:

```bash
pipx ensurepath
```

Version:

```bash
pipx --version
```

List apps:

```bash
pipx list
```

pipx Python CLI apps ko isolated environments me install karta hai.

---

# 📦 Kali Package Maintenance

Update package lists:

```bash
sudo apt update
```

Upgrade:

```bash
sudo apt full-upgrade -y
```

Broken dependencies:

```bash
sudo apt --fix-broken install
```

Finish interrupted package config:

```bash
sudo dpkg --configure -a
```

Unused packages:

```bash
sudo apt autoremove -y
```

Cache clean:

```bash
sudo apt clean
```

Package source/status:

```bash
apt policy PACKAGE
```

---

# 💾 Backup & Restore Workflow

## 1. Backup folder

```bash
mkdir -p ~/important-data
echo "Termux-Sathi" > ~/important-data/readme.txt
```

Tar archive:

```bash
tar -czvf important-data.tar.gz ~/important-data
```

Hash:

```bash
sha256sum important-data.tar.gz > important-data.tar.gz.sha256
```

Verify:

```bash
sha256sum -c important-data.tar.gz.sha256
```

## 2. Restore

```bash
mkdir -p ~/restore-test
tar -xzvf important-data.tar.gz -C ~/restore-test
```

Check:

```bash
find ~/restore-test -maxdepth 3 -type f
```

## 3. rsync backup

```bash
mkdir -p ~/backup-copy
rsync -av --dry-run ~/important-data/ ~/backup-copy/
rsync -av ~/important-data/ ~/backup-copy/
```

---

# 🔍 Mini System Diagnostic Script

Create:

```bash
nano ~/system-check.sh
```

Paste:

```bash
#!/bin/bash

echo "=== USER ==="
whoami
id

echo
echo "=== SYSTEM ==="
uname -a

echo
echo "=== STORAGE ==="
df -h

echo
echo "=== MEMORY ==="
free -h

echo
echo "=== TOP CPU ==="
ps aux --sort=-%cpu | head

echo
echo "=== TOP MEMORY ==="
ps aux --sort=-%mem | head

echo
echo "=== NETWORK ==="
ip addr
ip route

echo
echo "=== LISTENING PORTS ==="
ss -tuln
```

Run:

```bash
chmod +x ~/system-check.sh
~/system-check.sh
```

---

# ✅ Batch 4 Practice Challenge

1. rsync ka dry run aur real copy karo.
2. `lsof -i :8000` se local server process dekho.
3. `strace -c ls` run karo.
4. htop aur ncdu explore karo.
5. jq se JSON field extract karo.
6. ripgrep se text search karo.
7. screen session create/detach/reattach karo.
8. Python venv banao aur activate karo.
9. tar + SHA-256 backup workflow complete karo.
10. Mini system diagnostic script run karo.


---

# 🧪 Practical Chapters — Batch 5

Is batch me monitoring, network diagnostics aur defensive hardening cover honge.

## Tool 76 — watch

**Kaam:** kisi command ko repeat interval par run karna.

Version:

```bash
watch --version
```

Disk monitor:

```bash
watch -n 2 df -h
```

Memory monitor:

```bash
watch -n 2 free -h
```

Stop:

```text
Ctrl + C
```

---

## Tool 77 — vmstat

Install:

```bash
sudo apt install procps -y
```

Run:

```bash
vmstat
```

Repeat every 2 seconds:

```bash
vmstat 2
```

Useful fields:

```text
r  = runnable processes
free = free memory
si/so = swap in/out
us = user CPU
sy = system CPU
id = idle CPU
```

---

## Tool 78 — iostat

Install:

```bash
sudo apt install sysstat -y
```

Run:

```bash
iostat
```

Extended stats:

```bash
iostat -xz 2
```

Rootless Android me block-device statistics limited ho sakti hain.

---

## Tool 79 — sar

Install:

```bash
sudo apt install sysstat -y
```

CPU:

```bash
sar -u 1 5
```

Memory:

```bash
sar -r 1 5
```

Network:

```bash
sar -n DEV 1 5
```

Historical sar collection ke liye sysstat service/config required ho sakta hai.

---

## Tool 80 — dstat

Install:

```bash
sudo apt install dstat -y
```

Run:

```bash
dstat
```

CPU + disk + network:

```bash
dstat -cdn
```

---

## Tool 81 — iftop

Install:

```bash
sudo apt install iftop -y
```

Run:

```bash
sudo iftop
```

Specific interface:

```bash
sudo iftop -i wlan0
```

> Interface name device ke hisaab se alag ho sakta hai. Pehle `ip addr` se verify karo.

---

## Tool 82 — nethogs

Install:

```bash
sudo apt install nethogs -y
```

Run:

```bash
sudo nethogs
```

Specific interface:

```bash
sudo nethogs wlan0
```

Rootless environment me per-process network accounting limited ho sakta hai.

---

## Tool 83 — bmon

Install:

```bash
sudo apt install bmon -y
```

Run:

```bash
bmon
```

Real-time bandwidth/interface monitoring ke liye useful.

---

## Tool 84 — traceroute

Install:

```bash
sudo apt install traceroute -y
```

Run:

```bash
traceroute example.com
```

ICMP variant:

```bash
traceroute -I example.com
```

Some networks traceroute hops hide/block kar sakte hain.

---

## Tool 85 — mtr

Install:

```bash
sudo apt install mtr-tiny -y
```

Interactive:

```bash
mtr example.com
```

Report mode:

```bash
mtr -rw example.com
```

Network path + packet loss troubleshooting ke liye useful.

---

## Tool 86 — whois

Install:

```bash
sudo apt install whois -y
```

Domain info:

```bash
whois example.com
```

IP info:

```bash
whois 1.1.1.1
```

WHOIS data incomplete/redacted ho sakta hai.

---

## Tool 87 — host

Install:

```bash
sudo apt install bind9-host -y
```

Lookup:

```bash
host example.com
```

MX:

```bash
host -t MX example.com
```

---

## Tool 88 — OpenSSL s_client

TLS inspect:

```bash
openssl s_client -connect example.com:443 -servername example.com
```

Certificate subject/issuer:

```bash
openssl s_client -connect example.com:443 -servername example.com </dev/null 2>/dev/null | openssl x509 -noout -subject -issuer -dates
```

Use public certificate inspection ya apne service troubleshooting ke liye.

---

## Tool 89 — logrotate

Install:

```bash
sudo apt install logrotate -y
```

Version:

```bash
logrotate --version
```

Config check:

```bash
cat /etc/logrotate.conf
```

Dry run/debug:

```bash
sudo logrotate -d /etc/logrotate.conf
```

> `-d` debug mode actual rotation nahi karta.

---

## Tool 90 — Fail2ban Concepts

Install:

```bash
sudo apt install fail2ban -y
```

Version:

```bash
fail2ban-client --version
```

Status where service works:

```bash
sudo fail2ban-client status
```

Fail2ban repeated abusive login attempts ko logs ke basis par temporarily block kar sakta hai.

> Rootless/proot environments me firewall integration/service management unavailable ho sakti hai.

---

## Tool 91 — AppArmor Concepts

Check:

```bash
aa-status
```

Install utilities:

```bash
sudo apt install apparmor-utils -y
```

Profiles list:

```bash
sudo aa-status
```

AppArmor support kernel feature par depend karta hai; Rootless Android me usually limited/not available ho sakta hai.

---

## Tool 92 — UFW

Install:

```bash
sudo apt install ufw -y
```

Status:

```bash
sudo ufw status verbose
```

Before enabling firewall, apne required services/SSH access ko samjho.

Example on a normal authorized Linux server:

```bash
sudo ufw allow 22/tcp
sudo ufw enable
sudo ufw status
```

> Remote server par firewall enable karne se pehle current SSH access ka rule confirm karo, warna khud ko hi bahar lock kar sakte ho.

---

## Tool 93 — nftables

Install:

```bash
sudo apt install nftables -y
```

Current ruleset:

```bash
sudo nft list ruleset
```

Tables:

```bash
sudo nft list tables
```

Rootless/proot me kernel firewall manipulation usually unavailable hota hai.

---

# 🛡️ System Hardening Checklist

## 1. Updates

```bash
sudo apt update
sudo apt full-upgrade -y
```

## 2. Unused packages

```bash
sudo apt autoremove -y
sudo apt clean
```

## 3. Listening ports review

```bash
ss -tulnp
```

Har listening service ko identify karo.

## 4. Running processes

```bash
ps aux
htop
```

## 5. User/account review

```bash
id
who
last
```

Some proot environments me `last`/login records incomplete ho sakte hain.

## 6. File permissions

```bash
ls -la ~
find ~ -maxdepth 2 -type f -perm /022 2>/dev/null | head
```

Unexpected world/group-writable sensitive files review karo.

## 7. SSH keys

```bash
ls -la ~/.ssh
chmod 700 ~/.ssh
chmod 600 ~/.ssh/id_ed25519 2>/dev/null
```

## 8. Package integrity/status

```bash
dpkg -C
sudo dpkg --configure -a
```

## 9. Logs

```bash
ls -lah /var/log
grep -Ri "failed" /var/log 2>/dev/null | head
```

## 10. Backups

```bash
tar -czf backup.tar.gz ~/important-data
sha256sum backup.tar.gz
```

Backup ko same device ke ek hi storage location par rakhna real backup nahi hota. Humans ne “single point of failure” ko kaafi baar rediscover kiya hai. 😄

---

# 🔍 Mini Monitoring Script

Create:

```bash
nano ~/monitor-check.sh
```

Paste:

```bash
#!/bin/bash

echo "=== DATE ==="
date

echo
echo "=== UPTIME ==="
uptime

echo
echo "=== MEMORY ==="
free -h

echo
echo "=== DISK ==="
df -h

echo
echo "=== TOP CPU ==="
ps aux --sort=-%cpu | head

echo
echo "=== TOP MEMORY ==="
ps aux --sort=-%mem | head

echo
echo "=== NETWORK ROUTE ==="
ip route

echo
echo "=== LISTENING PORTS ==="
ss -tuln
```

Run:

```bash
chmod +x ~/monitor-check.sh
~/monitor-check.sh
```

Watch every 5 seconds:

```bash
watch -n 5 ~/monitor-check.sh
```

---

# ✅ Batch 5 Practice Challenge

1. `watch` se memory monitor karo.
2. `vmstat 2` run karo.
3. `iostat -xz 2` try karo.
4. `sar -u 1 5` se CPU sample lo.
5. `bmon` se interface bandwidth dekho.
6. `traceroute` aur `mtr -rw` compare karo.
7. `host` aur `whois` use karo.
8. OpenSSL se TLS certificate dates inspect karo.
9. `logrotate -d` dry run karo.
10. Listening ports aur package status ke saath hardening checklist complete karo.


---

# 🧰 Complete Utilities Pack

Ye section remaining common developer, file, archive, crypto, transfer, parsing aur quality-check tools ko ek saath cover karta hai.

## Build Tools

Install:

```bash
sudo apt install build-essential make gcc g++ cmake pkg-config -y
```

Checks:

```bash
gcc --version
g++ --version
make --version
cmake --version
pkg-config --version
```

Simple C program:

```bash
cat > hello.c <<'EOF'
#include <stdio.h>
int main() {
    puts("Hello Termux-Sathi");
    return 0;
}
EOF

gcc hello.c -o hello
./hello
```

Simple C++:

```bash
cat > hello.cpp <<'EOF'
#include <iostream>
int main() {
    std::cout << "Hello C++" << std::endl;
    return 0;
}
EOF

g++ hello.cpp -o hello-cpp
./hello-cpp
```

---

## Python pip / pipx

Install:

```bash
sudo apt install python3-pip python3-venv pipx -y
```

Checks:

```bash
python3 --version
pip3 --version
pipx --version
```

Virtual environment:

```bash
python3 -m venv ~/venvs/lab
source ~/venvs/lab/bin/activate
python -m pip install --upgrade pip
deactivate
```

pipx apps:

```bash
pipx list
pipx ensurepath
```

System Python ko random `sudo pip install` se modify karne ke bajay venv/pipx use karna safer hai.

---

## Node.js / npm

Install:

```bash
sudo apt install nodejs npm -y
```

Check:

```bash
node --version
npm --version
```

Test:

```bash
node -e 'console.log("Termux-Sathi Node")'
```

---

## Go

Install:

```bash
sudo apt install golang-go -y
```

Check:

```bash
go version
```

Test:

```bash
cat > hello.go <<'EOF'
package main
import "fmt"
func main() {
    fmt.Println("Hello Go")
}
EOF

go run hello.go
```

---

## Ruby

Install:

```bash
sudo apt install ruby-full -y
```

Check:

```bash
ruby --version
gem --version
```

Test:

```bash
ruby -e 'puts "Hello Ruby"'
```

---

## Perl

Install:

```bash
sudo apt install perl -y
```

Check:

```bash
perl --version
```

Test:

```bash
perl -e 'print "Hello Perl\n";'
```

---

# Archive & Compression Tools

## ZIP / unzip

```bash
sudo apt install zip unzip -y
zip archive.zip file.txt
unzip -l archive.zip
unzip archive.zip -d restored
```

## 7-Zip

```bash
sudo apt install p7zip-full -y
7z a archive.7z file.txt
7z l archive.7z
7z x archive.7z
```

## xz

```bash
sudo apt install xz-utils -y
xz -k file.txt
xz -l file.txt.xz
unxz -k file.txt.xz
```

## zstd

```bash
sudo apt install zstd -y
zstd file.txt
unzstd file.txt.zst
```

---

# Binary Inspection Utilities

Install:

```bash
sudo apt install binutils xxd -y
```

## hexdump

```bash
hexdump -C /bin/ls | head
```

## xxd

```bash
xxd /bin/ls | head
```

## objdump

```bash
objdump -f /bin/ls
objdump -h /bin/ls
```

## readelf

```bash
readelf -h /bin/ls
readelf -S /bin/ls | head
```

## nm

```bash
nm ./hello 2>/dev/null | head
```

## strings advanced

```bash
strings -n 8 /bin/ls | head
```

---

# Crypto / Signing Tools

## GPG

Install:

```bash
sudo apt install gnupg -y
```

Version:

```bash
gpg --version
```

Hash:

```bash
gpg --print-md SHA256 file.txt
```

Key list:

```bash
gpg --list-keys
```

## age

Install:

```bash
sudo apt install age -y
```

Version:

```bash
age --version
```

Generate key:

```bash
age-keygen -o age-key.txt
```

> Private keys ko public repo/chat me paste mat karo.

---

# File Transfer Tools

## SCP

Own/authorized server:

```bash
scp file.txt username@SERVER_IP:/tmp/
```

Download:

```bash
scp username@SERVER_IP:/tmp/file.txt .
```

## SFTP

```bash
sftp username@SERVER_IP
```

Inside:

```text
pwd
ls
put file.txt
get remote.txt
exit
```

## rsync over SSH

Dry run:

```bash
rsync -av --dry-run -e ssh ~/data/ username@SERVER_IP:/backup/
```

Real sync:

```bash
rsync -av -e ssh ~/data/ username@SERVER_IP:/backup/
```

---

# Download Utilities

## aria2

Install:

```bash
sudo apt install aria2 -y
```

Check:

```bash
aria2c --version
```

HTTP download:

```bash
aria2c https://example.com
```

---

# Parallel Command Utility

Install:

```bash
sudo apt install parallel -y
```

Check:

```bash
parallel --version
```

Safe practice:

```bash
printf "one\ntwo\nthree\n" | parallel echo Item:
```

---

# Shell Quality Tools

## ShellCheck

Install:

```bash
sudo apt install shellcheck -y
```

Check:

```bash
shellcheck --version
```

Lint script:

```bash
shellcheck ~/system-check.sh
```

---

# YAML / JSON Validation

## yamllint

Install:

```bash
sudo apt install yamllint -y
```

Sample:

```bash
cat > sample.yaml <<'EOF'
name: Termux-Sathi
active: true
EOF

yamllint sample.yaml
```

## JSON validation with jq

```bash
echo '{"name":"Termux-Sathi"}' > sample.json
jq empty sample.json && echo "Valid JSON"
```

---

# Useful Text/Data Tools

Install:

```bash
sudo apt install sed gawk grep coreutils findutils -y
```

Examples:

```bash
sed 's/Termux/Kali/' file.txt
awk '{print $1}' file.txt
grep -n "text" file.txt
find . -type f -name "*.txt"
```

---

# HTTP / API Utilities

Install:

```bash
sudo apt install curl httpie -y
```

Check:

```bash
http --version
```

Local API style request:

```bash
http GET http://127.0.0.1:8000
```

---

# Terminal Editors

## nano

```bash
sudo apt install nano -y
nano notes.txt
```

## vim

```bash
sudo apt install vim -y
vim notes.txt
```

---

# File Managers

## ranger

```bash
sudo apt install ranger -y
ranger
```

## mc

```bash
sudo apt install mc -y
mc
```

---

# Process & Service Utilities

Install:

```bash
sudo apt install procps psmisc -y
```

Commands:

```bash
ps aux
pgrep bash
pstree
kill PID
pkill PROCESS_NAME
```

Use `kill -9` only as last resort.

---

# Networking Convenience Pack

Install:

```bash
sudo apt install iproute2 iputils-ping net-tools dnsutils traceroute mtr-tiny curl wget whois -y
```

Useful:

```bash
ip addr
ip route
ss -tuln
ping -c 4 1.1.1.1
dig example.com
host example.com
traceroute example.com
mtr -rw example.com
```

---

# Forensics Convenience Pack

Install:

```bash
sudo apt install sleuthkit foremost yara binwalk libimage-exiftool-perl gddrescue -y
```

Safe checks:

```bash
file sample.bin
strings sample.bin | head
sha256sum sample.bin
exiftool sample.bin
binwalk sample.bin
```

---

# Reverse Engineering Convenience Pack

Install:

```bash
sudo apt install gdb radare2 rizin apktool jadx binutils -y
```

Checks:

```bash
gdb --version
r2 -v
rizin -v
apktool --version
jadx --version
```

---

# Web Testing Convenience Pack

Install:

```bash
sudo apt install burpsuite zaproxy gobuster wfuzz whatweb nikto sqlmap -y
```

Safe localhost commands:

```bash
whatweb http://127.0.0.1:8000
nikto -h http://127.0.0.1:8000
```

Automated testing ko localhost/CTF/authorized application tak rakho.

---

# Kali Security Tool Help Pack

High-risk tools ke liye beginner guide me safe help/version commands:

```bash
nmap --help
sqlmap --help
hydra -h
john --help
hashcat --help
aircrack-ng --help
msfconsole
```

Metasploit console me:

```text
help
version
exit
```

Exploit, credential attacks, interception, spoofing aur destructive workflows sirf authorized isolated labs ke context me hi practice honi chahiye.

---

# ✅ Complete Utilities Pack Challenge

1. C aur C++ program compile karo.
2. Python venv create karo.
3. Node, Go, Ruby, Perl version/test run karo.
4. ZIP, 7z, xz aur zstd archive practice karo.
5. `xxd`, `readelf`, `objdump` se apna binary inspect karo.
6. GPG SHA-256 calculate karo.
7. ShellCheck se apna script lint karo.
8. YAML aur JSON validate karo.
9. `rsync --dry-run` se backup test karo.
10. Networking convenience pack commands run karo.
11. Forensics pack se apni sample file inspect karo.
12. Reverse engineering tools ke version/help verify karo.

---



---

# ✅ Course Completion Status

The Kali tools section is now organized as a complete learning set rather than an endless batch queue.

Covered areas include:

```text
Kali metapackages
Linux/admin basics
network diagnostics
packet analysis
web testing
password-auditing tool references
wireless/hardware limitations
forensics
reverse engineering
VPN/proxy basics
system monitoring
hardening
developer/build tools
archives/compression
crypto/signing
SSH/file transfer
backup/restore
Android NetHunter Rootless limitations
safe localhost labs
troubleshooting
quick-reference commands
```

For packages that change across Kali releases, always verify the current package/tool name with:

```bash
apt search '^kali-tools-'
apt search '^kali-linux-'
apt search TOOL_NAME
```

High-risk offensive tools are intentionally limited here to installation, help, concepts, and isolated lab/CTF boundaries.

# 🏁 Guide Status

Ab repository me:

```text
Kali metapackage/category index
Networking practicals
Web testing practicals
Forensics
Reverse engineering
Wireless/hardware notes
VPN/proxy basics
System administration
Monitoring
Hardening
Developer tools
Archive/compression
Crypto/signing
File transfer
Parsing/linting
Troubleshooting
Backup/restore
Android/NetHunter Rootless limitations
```

Kali me hundreds of packages hote hain aur package list time ke saath change hoti rehti hai. Isliye exact exhaustive list ke liye:

```bash
apt search '^kali-tools-'
apt search '^kali-linux-'
apt list 2>/dev/null | less
```

Aur maximum official collection ke liye:

```bash
sudo apt install kali-linux-everything -y
```

> Android/NetHunter Rootless par `kali-linux-everything` practical choice hamesha nahi hota. Storage, RAM, GUI, kernel aur hardware support pehle check karo.
