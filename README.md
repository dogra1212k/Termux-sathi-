# 📱 Termux-Sathi

Android पर Termux और Kali NetHunter Rootless सीखने के लिए step-by-step Hindi guide.

> ⚠️ यह project learning और authorized practice के लिए है. Commands अपने device, lab, CTF या permission वाले system पर ही चलाएँ.

## 📚 Learning Path

1. Termux Setup
2. Kali NetHunter Rootless
3. Linux Basic Commands
4. Permissions, Users & Package Management
5. Processes, System Info & Networking
6. File Search, Text Processing & Archives
7. Bash Scripting Basics

---

# Part 1 — Termux Basic Setup

```bash
pkg update
pkg upgrade
pkg install proot-distro
proot-distro list
```

**काम:** Termux packages update/upgrade करना और supported Linux distributions देखना.

---

# Part 2 — Kali NetHunter Rootless

```bash
termux-setup-storage
pkg install wget
wget -O install-nethunter-termux https://offs.ec/2MceZWr
chmod +x install-nethunter-termux
./install-nethunter-termux
```

Kali start:

```bash
nethunter
```

या:

```bash
nh
```

Verify:

```bash
whoami
pwd
uname -a
```

Update Kali:

```bash
sudo apt update
sudo apt full-upgrade -y
```

---

# Part 3 — Linux Basic Commands

```bash
pwd
ls
ls -l
ls -a
mkdir practice
cd practice
cd ..
cd ~
touch file1.txt
echo "Hello Termux-Sathi" > file1.txt
echo "Linux practical running" >> file1.txt
cat file1.txt
cp file1.txt file2.txt
mv file2.txt notes.txt
rm file1.txt
clear
history
whoami
uname -a
df -h
du -sh .
```

### Useful meanings

- `pwd` → current directory
- `ls` → files/folders
- `mkdir` → folder बनाना
- `cd` → directory बदलना
- `touch` → file बनाना
- `cat` → file पढ़ना
- `cp` → copy
- `mv` → move/rename
- `rm` → delete

> ⚠️ `rm` और `rm -r` ध्यान से चलाएँ.

---

# Part 4 — Permissions, Users & Packages

Permissions देखें:

```bash
ls -l
```

Executable permission:

```bash
chmod +x script.sh
chmod 755 script.sh
chmod 644 notes.txt
```

Simple script:

```bash
#!/bin/bash
echo "Termux-Sathi practical running"
```

Run:

```bash
chmod +x script.sh
./script.sh
```

User/group:

```bash
whoami
id
```

Ownership:

```bash
sudo chown kali notes.txt
sudo chown kali:kali notes.txt
```

APT:

```bash
sudo apt update
sudo apt upgrade -y
sudo apt install nano -y
apt search python
apt show nano
sudo apt remove nano -y
sudo apt autoremove
sudo apt clean
```

Help/location:

```bash
which python
whereis python
type cd
ls --help
man ls
```

---

# Part 5 — Processes, System Info & Networking

Processes:

```bash
ps
ps aux
top
```

Optional:

```bash
sudo apt install htop -y
htop
```

Background jobs:

```bash
sleep 300 &
jobs
ps aux | grep sleep
kill PID
pkill sleep
```

System info:

```bash
free -h
df -h
du -sh .
hostname
uname -a
uname -m
```

Networking:

```bash
ip addr
ip route
ping -c 4 1.1.1.1
ping -c 4 google.com
```

HTTP tools:

```bash
sudo apt install curl -y
curl --version
curl -I https://example.com
wget https://example.com/file.txt
```

> कुछ networks ping/ICMP block करते हैं. Ping fail होना हमेशा internet बंद होने का मतलब नहीं.

---

# Part 6 — File Search, Text Processing & Archives

Practice data:

```bash
mkdir -p ~/part6-lab
cd ~/part6-lab
echo "apple" > fruits.txt
echo "banana" >> fruits.txt
echo "mango" >> fruits.txt
echo "apple" >> fruits.txt
echo "orange" >> fruits.txt
```

Search:

```bash
find . -name "fruits.txt"
find . -name "*.txt"
grep "apple" fruits.txt
grep -n "apple" fruits.txt
grep -i "APPLE" fruits.txt
```

Read/filter:

```bash
head -n 2 fruits.txt
tail -n 2 fruits.txt
wc -l fruits.txt
sort fruits.txt
sort fruits.txt | uniq
sort fruits.txt | uniq -c
```

Archive:

```bash
tar -cvf backup.tar fruits.txt
tar -tvf backup.tar
mkdir extracted
tar -xvf backup.tar -C extracted
gzip fruits.txt
gunzip fruits.txt.gz
```

Compressed archive:

```bash
tar -czvf backup.tar.gz fruits.txt
mkdir restore
tar -xzvf backup.tar.gz -C restore
```

---

# Part 7 — Bash Scripting Basics

First script:

```bash
#!/bin/bash
echo "Hello from Termux-Sathi"
```

Run:

```bash
chmod +x hello.sh
./hello.sh
```

Variables:

```bash
name="Termux-Sathi"
echo "$name"
```

Input:

```bash
read -p "Apna naam likho: " name
echo "Hello $name"
```

If/else:

```bash
read -p "Number likho: " number

if [ "$number" -gt 10 ]
then
    echo "Number 10 se bada hai"
else
    echo "Number 10 ya usse chhota hai"
fi
```

For loop:

```bash
for number in {1..5}
do
    echo "$number"
done
```

While loop:

```bash
count=1
while [ "$count" -le 5 ]
do
    echo "Count: $count"
    count=$((count + 1))
done
```

Function:

```bash
greet() {
    echo "Hello $1"
}
greet "Arun"
```

Script arguments:

```bash
echo "Script: $0"
echo "First argument: $1"
echo "Total arguments: $#"
echo "All arguments: $@"
```

Exit code:

```bash
ls
echo $?
```

Syntax check:

```bash
bash -n script.sh
```

Debug:

```bash
bash -x script.sh
```

---

# Mini Project — Termux-Sathi System Tool

Create:

```bash
nano termux-sathi.sh
```

Paste:

```bash
#!/bin/bash

while true
do
clear

echo "=============================="
echo "      TERMUX-SATHI"
echo "=============================="
echo "1. Current User"
echo "2. Current Directory"
echo "3. System Information"
echo "4. Storage Information"
echo "5. Memory Information"
echo "6. Network Information"
echo "7. Ping Test"
echo "8. Exit"

read -p "Option choose karo: " option

case "$option" in
1) whoami ;;
2) pwd ;;
3) uname -a ;;
4) df -h ;;
5) free -h ;;
6) ip addr ;;
7) ping -c 4 1.1.1.1 ;;
8) echo "Termux-Sathi closed"; exit 0 ;;
*) echo "Invalid option" ;;
esac

echo
read -p "Continue ke liye Enter dabaye..."
done
```

Run:

```bash
chmod +x termux-sathi.sh
./termux-sathi.sh
```

---

## 🚀 Next

**Part 8 — Python Basics in Kali/Termux**

Topics:
- python3
- print()
- variables
- input()
- if/else
- loops
- functions
- lists
- files
- modules
- Termux-Sathi Python System Tool

---

## 🛡️ Safety

यह repository Linux/Termux learning के लिए है. Security-related commands/tools को केवल अपने systems, labs, CTFs या स्पष्ट permission वाले environments पर use करें.
