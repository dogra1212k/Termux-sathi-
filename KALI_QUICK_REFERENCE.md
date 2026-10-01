# ⚡ Kali Quick Reference

Fast command reference for Termux-Sathi. Detailed explanation ke liye main guides dekhein.

## System

```bash
whoami
id
uname -a
hostname
uptime
free -h
df -h
du -sh .
lsblk
```

## Files

```bash
pwd
ls -lah
cd PATH
mkdir DIR
touch FILE
cp SRC DST
mv SRC DST
rm FILE
find . -name "*.txt"
file FILE
stat FILE
```

## Text

```bash
cat FILE
less FILE
head FILE
tail FILE
grep "text" FILE
grep -R "text" .
rg "text" .
sort FILE
uniq FILE
wc -l FILE
sed 's/old/new/' FILE
awk '{print $1}' FILE
```

## Permissions

```bash
ls -l
chmod +x script.sh
chmod 755 script.sh
chmod 644 file.txt
chown USER FILE
```

## Packages

```bash
sudo apt update
sudo apt full-upgrade -y
apt search PACKAGE
apt show PACKAGE
sudo apt install PACKAGE -y
sudo apt remove PACKAGE -y
sudo apt autoremove -y
sudo apt clean
dpkg -l
```

## Processes

```bash
ps aux
top
htop
pgrep NAME
pstree
kill PID
pkill NAME
jobs
bg
fg
```

## Networking

```bash
ip addr
ip route
ss -tuln
ss -tun
ping -c 4 1.1.1.1
host example.com
dig example.com
nslookup example.com
traceroute example.com
mtr -rw example.com
curl -I https://example.com
wget https://example.com
```

## Local Lab

```bash
mkdir -p ~/kali-lab
cd ~/kali-lab
echo "Termux-Sathi" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Second terminal:

```bash
curl http://127.0.0.1:8000
ss -tuln | grep 8000
nmap -p 8000 127.0.0.1
```

## Nmap — authorized/local only

```bash
nmap 127.0.0.1
nmap -p 8000 127.0.0.1
nmap -sV -p 8000 127.0.0.1
```

## Packet Analysis

```bash
tcpdump -D
sudo tcpdump -i lo
sudo tcpdump -i lo -w lab.pcap
tshark -r lab.pcap
tshark -r lab.pcap -Y http
```

## Web Diagnostics

```bash
curl -I http://127.0.0.1:8000
curl -v http://127.0.0.1:8000
whatweb http://127.0.0.1:8000
nikto -h http://127.0.0.1:8000
```

## DNS

```bash
dig example.com
dig +short example.com
host example.com
nslookup example.com
```

## Forensics

```bash
sha256sum FILE
md5sum FILE
file FILE
strings FILE | head
exiftool FILE
binwalk FILE
yara RULE.yar FILE
```

## Binary Inspection

```bash
xxd FILE | head
hexdump -C FILE | head
readelf -h BINARY
objdump -f BINARY
nm BINARY | head
strings -n 8 BINARY | head
```

## GDB

```bash
gdb ./program
```

Inside:

```text
break main
run
next
continue
quit
```

## Archives

```bash
tar -czvf backup.tar.gz DIR
tar -tzvf backup.tar.gz
tar -xzvf backup.tar.gz
zip archive.zip FILE
unzip -l archive.zip
7z a archive.7z FILE
7z l archive.7z
```

## Hash / Crypto

```bash
sha256sum FILE
openssl dgst -sha256 FILE
openssl rand -hex 16
gpg --print-md SHA256 FILE
```

## SSH

```bash
ssh -V
ssh user@SERVER
ssh -p 2222 user@SERVER
ssh-keygen -t ed25519
scp FILE user@SERVER:/tmp/
sftp user@SERVER
```

## Git

```bash
git status
git add .
git commit -m "message"
git log --oneline
git branch
git switch -c branch-name
git pull
git push
```

## Python

```bash
python3 --version
python3 script.py
python3 -m venv ~/venvs/lab
source ~/venvs/lab/bin/activate
deactivate
```

## Bash

```bash
bash script.sh
bash -n script.sh
bash -x script.sh
shellcheck script.sh
```

## Monitoring

```bash
watch -n 2 free -h
vmstat 2
iostat -xz 2
sar -u 1 5
bmon
iftop
nethogs
lsof -i :8000
```

## Troubleshooting

```bash
which TOOL
command -v TOOL
apt search TOOL
sudo apt update
sudo apt --fix-broken install
sudo dpkg --configure -a
df -h
free -h
ip addr
ip route
ss -tuln
cat /etc/resolv.conf
```

## Kali Metapackages

```bash
sudo apt install kali-tools-top10 -y
sudo apt install kali-tools-information-gathering -y
sudo apt install kali-tools-vulnerability -y
sudo apt install kali-tools-web -y
sudo apt install kali-tools-passwords -y
sudo apt install kali-tools-wireless -y
sudo apt install kali-tools-forensics -y
sudo apt install kali-tools-reverse-engineering -y
sudo apt install kali-tools-reporting -y
```

Maximum collection:

```bash
sudo apt install kali-linux-everything -y
```

> Rootless Android me package install hona hardware capability guarantee nahi karta.
