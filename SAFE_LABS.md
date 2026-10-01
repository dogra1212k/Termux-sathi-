> 📚 **Navigation:** [Master Index](KALI_MASTER_INDEX.md) · [Complete Guide](KALI_TOOLS_GUIDE.md) · [Practical Guide](KALI_TOOLS_PRACTICAL.md) · [Quick Reference](KALI_QUICK_REFERENCE.md) · [Rootless Guide](NETHUNTER_ROOTLESS_GUIDE.md) · [Safe Labs](SAFE_LABS.md)

# 🧪 Safe Practice Labs

Ye labs Termux-Sathi ke Kali guides ke saath reuse karne ke liye hain. Sab targets localhost, apni files, ya intentionally-created training data hain.

> ⚠️ Random public IP/domain/account/Wi-Fi ko practice target mat banao.

# Lab 1 — Local HTTP Server

Terminal 1:

```bash
mkdir -p ~/labs/http
cd ~/labs/http
echo '<h1>Termux-Sathi Lab</h1>' > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:

```bash
curl http://127.0.0.1:8000
curl -I http://127.0.0.1:8000
nmap -p 8000 127.0.0.1
whatweb http://127.0.0.1:8000
```

# Lab 2 — Directory Discovery

Create paths:

```bash
mkdir -p ~/labs/web/admin ~/labs/web/docs
echo home > ~/labs/web/index.html
echo admin > ~/labs/web/admin/index.html
echo docs > ~/labs/web/docs/index.html
cd ~/labs/web
python3 -m http.server 8000 --bind 127.0.0.1
```

Wordlist:

```bash
printf "admin\ndocs\nlogin\ntest\n" > ~/labs/words.txt
```

Gobuster:

```bash
gobuster dir -u http://127.0.0.1:8000 -w ~/labs/words.txt
```

Wfuzz:

```bash
wfuzz -z file,~/labs/words.txt --hc 404 http://127.0.0.1:8000/FUZZ
```

# Lab 3 — Packet Capture

Terminal 1:

```bash
sudo tcpdump -i lo -w ~/labs/http-lab.pcap
```

Terminal 2:

```bash
curl http://127.0.0.1:8000
```

Stop capture:

```text
Ctrl + C
```

Analyze:

```bash
tshark -r ~/labs/http-lab.pcap
tshark -r ~/labs/http-lab.pcap -Y http
```

# Lab 4 — Netcat Loopback

Terminal 1:

```bash
nc.traditional -l -p 9000
```

Terminal 2:

```bash
echo "Hello Termux-Sathi" | nc.traditional 127.0.0.1 9000
```

# Lab 5 — Hash / Integrity

```bash
mkdir -p ~/labs/integrity
echo "original data" > ~/labs/integrity/file.txt
cd ~/labs/integrity
sha256sum file.txt > hashes.txt
sha256sum -c hashes.txt
```

Modify:

```bash
echo "changed" >> file.txt
sha256sum -c hashes.txt
```

Observe verification failure.

# Lab 6 — YARA

```bash
mkdir -p ~/labs/yara
echo "Termux-Sathi training sample" > ~/labs/yara/sample.txt
```

Rule:

```bash
cat > ~/labs/yara/sample.yar <<'EOF'
rule TermuxSathiSample {
    strings:
        $a = "Termux-Sathi"
    condition:
        $a
}
EOF
```

Run:

```bash
yara ~/labs/yara/sample.yar ~/labs/yara/sample.txt
```

# Lab 7 — File Metadata

Use your own image:

```bash
exiftool training.jpg
file training.jpg
sha256sum training.jpg
```

Review before sharing because metadata may contain device/location details.

# Lab 8 — Binary Analysis

Create:

```bash
mkdir -p ~/labs/re
cd ~/labs/re
cat > demo.c <<'EOF'
#include <stdio.h>
int main() {
    puts("Termux-Sathi RE Lab");
    return 0;
}
EOF
gcc demo.c -o demo
```

Inspect:

```bash
file demo
strings demo | grep Termux
readelf -h demo
objdump -f demo
nm demo | head
```

GDB:

```bash
gdb ./demo
```

Inside:

```text
break main
run
next
continue
quit
```

Radare2:

```bash
r2 -A ./demo
```

Inside:

```text
afl
pdf @ main
q
```

# Lab 9 — Disk Image Practice

Create a harmless test image:

```bash
mkdir -p ~/labs/disk
cd ~/labs/disk
dd if=/dev/zero of=training.img bs=1M count=10 status=progress
```

Copy:

```bash
dd if=training.img of=copy.img bs=1M status=progress
```

Compare:

```bash
sha256sum training.img copy.img
```

ddrescue:

```bash
ddrescue training.img rescued.img rescue.log
```

> Real block devices par `dd` tabhi use karein jab input/output path fully verified ho.

# Lab 10 — SQLite

```bash
mkdir -p ~/labs/sqlite
cd ~/labs/sqlite
sqlite3 lab.db
```

Inside:

```sql
CREATE TABLE notes(id INTEGER PRIMARY KEY, text TEXT);
INSERT INTO notes(text) VALUES('Termux-Sathi');
SELECT * FROM notes;
.quit
```

# Lab 11 — Bash Script

```bash
mkdir -p ~/labs/bash
cat > ~/labs/bash/check.sh <<'EOF'
#!/bin/bash
echo "User: $(whoami)"
echo "Dir: $(pwd)"
echo "Kernel: $(uname -a)"
df -h
EOF
chmod +x ~/labs/bash/check.sh
bash -n ~/labs/bash/check.sh
~/labs/bash/check.sh
```

# Lab 12 — Python

```bash
mkdir -p ~/labs/python
cat > ~/labs/python/system.py <<'EOF'
import os
import platform
print("Directory:", os.getcwd())
print("System:", platform.system())
print("Machine:", platform.machine())
EOF
python3 ~/labs/python/system.py
```

# Lab 13 — Git

```bash
mkdir -p ~/labs/git
cd ~/labs/git
git init
echo "Termux-Sathi" > README.md
git add README.md
git status
git commit -m "Initial lab commit"
git log --oneline
```

Configure identity first if Git asks for it.

# Lab 14 — SSH Keys

```bash
mkdir -p ~/labs/ssh
ssh-keygen -t ed25519 -f ~/labs/ssh/id_ed25519
ls -lah ~/labs/ssh
```

Public:

```bash
cat ~/labs/ssh/id_ed25519.pub
```

Never publish the private key without `.pub`.

# Lab 15 — Archive / Restore

```bash
mkdir -p ~/labs/backup/data
echo "backup test" > ~/labs/backup/data/file.txt
cd ~/labs/backup
tar -czvf data.tar.gz data
sha256sum data.tar.gz > data.tar.gz.sha256
sha256sum -c data.tar.gz.sha256
mkdir restored
tar -xzvf data.tar.gz -C restored
find restored -type f
```

# Lab 16 — Monitoring

```bash
watch -n 2 free -h
```

Other terminal:

```bash
vmstat 2
```

Disk:

```bash
df -h
ncdu ~
```

# Lab 17 — JSON/YAML

JSON:

```bash
echo '{"name":"Termux-Sathi","active":true}' > ~/labs/sample.json
jq . ~/labs/sample.json
jq -r '.name' ~/labs/sample.json
```

YAML:

```bash
cat > ~/labs/sample.yaml <<'EOF'
name: Termux-Sathi
active: true
EOF
yamllint ~/labs/sample.yaml
```

# Lab 18 — TLS Certificate Inspection

```bash
openssl s_client -connect example.com:443 -servername example.com </dev/null 2>/dev/null | openssl x509 -noout -subject -issuer -dates
```

This inspects public certificate metadata only.

# Lab 19 — Web Proxy Practice

Start mitmproxy locally:

```bash
mitmproxy --listen-host 127.0.0.1 -p 8080
```

Configure only your own browser/app to use localhost proxy and inspect your own requests.

# Lab 20 — System Audit

```bash
sudo lynis audit system
```

Also review:

```bash
ss -tuln
ps aux
df -h
free -h
ls -lah /var/log
```

# Practice Rule

Har lab ke baad 4 cheezein note karo:

```text
1. Command
2. Expected output
3. Actual output
4. Error + fix
```

Yahi boring-looking habit actual skill banati hai. Sirf command collection banana shell history ko strong karta hai, operator ko nahi. 😄
