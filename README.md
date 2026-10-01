# 📱 Termux-Sathi

Android पर Termux और Kali NetHunter Rootless सीखने के लिए step-by-step Hindi guide.

> ⚠️ यह project learning और authorized practice के लिए है. Commands अपने device, lab, CTF या permission वाले system पर ही चलाएँ.

## 🐉 Kali Linux Complete Learning Hub

अब Kali section को एक complete structured hub में organize किया गया है:

- **[KALI_MASTER_INDEX.md](KALI_MASTER_INDEX.md)** — master navigation, official tool categories और learning routes
- **[KALI_TOOLS_GUIDE.md](KALI_TOOLS_GUIDE.md)** — large category guide, install commands, practicals और troubleshooting
- **[KALI_TOOLS_PRACTICAL.md](KALI_TOOLS_PRACTICAL.md)** — step-by-step hands-on practical chapters
- **[KALI_QUICK_REFERENCE.md](KALI_QUICK_REFERENCE.md)** — fast command cheat sheet
- **[NETHUNTER_ROOTLESS_GUIDE.md](NETHUNTER_ROOTLESS_GUIDE.md)** — Android/NetHunter Rootless setup, KeX और limitations
- **[SAFE_LABS.md](SAFE_LABS.md)** — reusable localhost/filesystem practice labs

### One-command Kali collections

```bash
sudo apt update
sudo apt full-upgrade -y
sudo apt install kali-tools-top10 -y
```

Default Kali toolset:

```bash
sudo apt install kali-linux-default -y
```

Maximum Kali collection:

```bash
sudo apt install kali-linux-everything -y
```

> Android/NetHunter Rootless पर `kali-linux-everything` blindly install करना practical नहीं हो सकता. Storage, RAM, GUI, kernel और hardware limitations पहले check करें.

---

## 🚀 Run Termux-Sathi Toolkit

Repo clone:

```bash
git clone https://github.com/dogra1212k/Termux-sathi-.git
cd Termux-sathi-
```

Setup:

```bash
chmod +x setup.sh
./setup.sh
```

Launcher:

```bash
./termux-sathi.sh
```

Launcher me available practicals:

```text
System Check
Network Lab
Nmap Local Lab
Backup Lab
Python Practice
Git Practice
Quick Reference
Kali Master Index
Safe Labs Guide
```

Individual labs:

```bash
bash labs/system-check.sh
bash labs/network-lab.sh
bash labs/nmap-lab.sh
bash labs/backup-lab.sh
python3 labs/python-lab.py
bash labs/git-lab.sh
```

GitHub Actions automatically Bash aur Python syntax check karta hai.

---

## 📚 Learning Path

1. Termux Setup
2. Kali NetHunter Rootless
3. Linux Basic Commands
4. Permissions, Users & Package Management
5. Processes, System Info & Networking
6. File Search, Text Processing & Archives
7. Bash Scripting Basics
8. Python Basics
9. Git & GitHub Basics
10. Linux Networking Practical & Troubleshooting
11. Linux Users, Environment Variables & Shell Customization

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

# Part 8 — Python Basics in Kali/Termux

Python check:

```bash
python3 --version
```

Agar Python installed nahi hai:

```bash
sudo apt update
sudo apt install python3 -y
```

Python shell start:

```bash
python3
```

Exit:

```python
exit()
```

## 1. First Python Program

```bash
nano hello.py
```

Paste:

```python
print("Hello from Termux-Sathi")
```

Run:

```bash
python3 hello.py
```

Expected:

```text
Hello from Termux-Sathi
```

## 2. Variables

```python
name = "Arun"
tool = "Termux"
system = "Kali"

print(name)
print(tool)
print(system)
```

## 3. User Input

```python
name = input("Apna naam likho: ")
print("Hello", name)
```

## 4. Numbers

```python
a = 10
b = 5

print(a + b)
print(a - b)
print(a * b)
print(a / b)
```

## 5. if / else

```python
number = int(input("Number likho: "))

if number > 10:
    print("Number 10 se bada hai")
else:
    print("Number 10 ya usse chhota hai")
```

## 6. for Loop

```python
for number in range(1, 6):
    print(number)
```

## 7. while Loop

```python
count = 1

while count <= 5:
    print("Count:", count)
    count += 1
```

## 8. Lists

```python
tools = ["Termux", "Kali", "Python"]

for tool in tools:
    print(tool)
```

## 9. Functions

```python
def greet(name):
    print("Hello", name)

greet("Arun")
```

## 10. Dictionary

```python
device = {
    "name": "Android",
    "terminal": "Termux",
    "linux": "Kali"
}

print(device["terminal"])
```

## 11. File Write

```python
with open("notes.txt", "w") as file:
    file.write("Termux-Sathi Python practical\n")
```

## 12. File Read

```python
with open("notes.txt", "r") as file:
    content = file.read()

print(content)
```

## 13. Append to File

```python
with open("notes.txt", "a") as file:
    file.write("Second line\n")
```

## 14. Modules

```python
import os
import platform

print(os.getcwd())
print(platform.system())
print(platform.machine())
print(platform.python_version())
```

## 15. Error Handling

```python
try:
    number = int(input("Number likho: "))
    print("Aapne likha:", number)
except ValueError:
    print("Valid number likho")
```

## 16. Command-Line Arguments

```python
import sys

print("Script:", sys.argv[0])
print("Arguments:", sys.argv[1:])
```

Run:

```bash
python3 args.py hello kali
```

## 17. Mini Project — Termux-Sathi Python System Tool

Create:

```bash
nano termux_sathi.py
```

Paste:

```python
import os
import platform
import subprocess

def run_command(command):
    try:
        result = subprocess.run(
            command,
            shell=True,
            text=True,
            capture_output=True
        )
        if result.stdout:
            print(result.stdout)
        if result.stderr:
            print(result.stderr)
    except Exception as error:
        print("Error:", error)

while True:
    print("\n==============================")
    print("      TERMUX-SATHI PYTHON")
    print("==============================")
    print("1. Current User")
    print("2. Current Directory")
    print("3. System Information")
    print("4. Storage Information")
    print("5. Memory Information")
    print("6. Network Information")
    print("7. Ping Test")
    print("8. Python Version")
    print("9. Exit")

    option = input("Option choose karo: ")

    if option == "1":
        run_command("whoami")
    elif option == "2":
        print(os.getcwd())
    elif option == "3":
        print("System:", platform.system())
        print("Machine:", platform.machine())
    elif option == "4":
        run_command("df -h")
    elif option == "5":
        run_command("free -h")
    elif option == "6":
        run_command("ip addr")
    elif option == "7":
        run_command("ping -c 4 1.1.1.1")
    elif option == "8":
        print(platform.python_version())
    elif option == "9":
        print("Termux-Sathi Python Tool closed")
        break
    else:
        print("Invalid option")
```

Run:

```bash
python3 termux_sathi.py
```

## 18. Python Practical Challenge

Khud ek script banao jo:

1. User ka naam le
2. Current directory dikhaye
3. Python version dikhaye
4. Ek list print kare
5. Ek file create kare
6. File ka content read kare
7. Invalid input handle kare

---

## ✅ Part 8 Complete

Ab aapko basic Python concepts ka practical idea hai:

```text
print()
variables
input()
if / else
for
while
lists
functions
dictionary
files
modules
try / except
sys.argv
```

# Part 9 — Git & GitHub Basics in Termux/Kali

Git local files aur unki history manage karta hai. GitHub remote repository hosting aur collaboration ke liye use hota hai.

## 1. Git Install

Kali me:

```bash
sudo apt update
sudo apt install git -y
```

Termux me:

```bash
pkg update
pkg install git
```

Check:

```bash
git --version
```

## 2. Git Identity Configure

```bash
git config --global user.name "Arun"
git config --global user.email "YOUR_GITHUB_EMAIL"
```

Check:

```bash
git config --global --list
```

Optional default branch:

```bash
git config --global init.defaultBranch main
```

> Public repository me commit email visible ho sakta hai. GitHub ka privacy/no-reply email bhi use kiya ja sakta hai.

## 3. Termux-Sathi Repo Clone

```bash
cd ~
git clone https://github.com/dogra1212k/Termux-sathi-.git
cd Termux-sathi-
```

Check remote:

```bash
git remote -v
```

## 4. Repository Status

```bash
git status
```

Ye batata hai kaunsi files modified, staged ya untracked hain.

## 5. File Change Practical

```bash
echo "Git practical started" > git-practice.txt
git status
```

## 6. Stage Changes

Ek file:

```bash
git add git-practice.txt
```

Sab current changes:

```bash
git add .
```

Check:

```bash
git status
```

## 7. Commit

```bash
git commit -m "Add Git practice file"
```

Commit local Git history me snapshot save karta hai.

Recent commits:

```bash
git log --oneline
```

## 8. Branch Basics

Branches dekho:

```bash
git branch
```

Nayi branch banao aur switch karo:

```bash
git switch -c practice-branch
```

Purane Git versions me:

```bash
git checkout -b practice-branch
```

Main branch par wapas:

```bash
git switch main
```

## 9. Remote Updates Check

```bash
git fetch
```

`fetch` remote changes download karta hai, lekin unhe current branch me automatically merge nahi karta.

## 10. Pull

```bash
git pull
```

`pull` remote changes fetch karke current branch me integrate karta hai.

Apna kaam start karne se pehle aam taur par:

```bash
git status
git pull
```

karna useful hai.

## 11. GitHub Authentication

GitHub password-based Git authentication use nahi karta. HTTPS ke liye browser-based GitHub CLI login ya personal access token use kiya ja sakta hai. SSH bhi supported hai.

### GitHub CLI Install

Kali:

```bash
sudo apt install gh -y
```

Termux:

```bash
pkg install gh
```

Check:

```bash
gh --version
```

Login:

```bash
gh auth login
```

GitHub.com choose karo, phir HTTPS aur browser login method follow kar sakte ho.

Status check:

```bash
gh auth status
```

> Password, personal access token ya SSH private key ko README, screenshot, chat ya public file me kabhi save/share mat karo.

## 12. Push

Current branch push:

```bash
git push
```

Nayi branch pehli baar:

```bash
git push -u origin practice-branch
```

`-u` upstream set karta hai, jisse agle push/pull commands chhote ho jate hain.

## 13. Safe Branch Workflow

Project ke liye recommended practice:

```bash
git switch main
git pull
git switch -c update-readme
```

File edit karo:

```bash
nano README.md
```

Phir:

```bash
git status
git add README.md
git commit -m "Update README"
git push -u origin update-readme
```

Uske baad GitHub par Pull Request create ki ja sakti hai.

## 14. Undo Staging

Galti se staged file:

```bash
git restore --staged git-practice.txt
```

File ke unstaged local changes discard karne ke liye:

```bash
git restore git-practice.txt
```

> `git restore` local changes hata sakta hai. Run karne se pehle `git diff` dekhna achhi habit hai.

## 15. Changes Compare

Unstaged difference:

```bash
git diff
```

Staged difference:

```bash
git diff --staged
```

## 16. Useful Git Commands

```text
git clone       = repository ki local copy
git status      = current state
git add         = changes stage
git commit      = snapshot save
git log         = history
git branch      = branches
git switch      = branch change
git fetch       = remote updates download
git pull        = remote changes integrate
git push        = commits GitHub par upload
git diff        = changes compare
git remote -v   = remote URLs
```

## 🧪 Full Git Practical

```bash
cd ~
git clone https://github.com/dogra1212k/Termux-sathi-.git
cd Termux-sathi-

git switch -c my-practice

echo "My Git practice" > practice.txt

git status
git add practice.txt
git commit -m "Add practice file"
git log --oneline

git push -u origin my-practice
```

## 🎯 Practice Challenge

1. Repo clone karo.
2. Nayi branch banao.
3. Ek text file create karo.
4. `git status` check karo.
5. File stage karo.
6. Commit karo.
7. `git log --oneline` check karo.
8. GitHub authentication verify karo.
9. Branch push karo.
10. GitHub par branch verify karo.

---

## ✅ Part 9 Complete

Ab aap Git/GitHub ke basic workflow ko samajhte ho:

```text
clone → branch → edit → status → add → commit → pull/fetch → push
```

# Part 10 — Linux Networking Practical & Troubleshooting

Networking ko samajhne ka best tareeka hai pehle apne device aur apne lab ko samajhna. Random public systems par commands chalana learning nahi, headache manufacturing hai.

## 1. Localhost Kya Hai?

Localhost aapke khud ke device ko refer karta hai.

Common address:

```text
127.0.0.1
```

IPv6 localhost:

```text
::1
```

Check:

```bash
ping -c 4 127.0.0.1
```

Agar response milta hai, local networking stack basic level par kaam kar raha hai.

## 2. IP Address Check

```bash
ip addr
```

Short form:

```bash
ip a
```

Useful interfaces Android/Termux/Kali environment ke hisaab se alag ho sakte hain.

Common names:

```text
lo
wlan0
eth0
```

Rootless/proot environment me kuch interfaces hidden ya limited ho sakte hain.

## 3. Route Check

```bash
ip route
```

Ye batata hai traffic kis route se bahar ja raha hai.

Typical line:

```text
default via 192.168.1.1 dev wlan0
```

Exact output network ke hisaab se alag hoga.

## 4. Hostname Check

```bash
hostname
```

Detailed host info ke liye:

```bash
uname -a
```

## 5. Internet Connectivity Test

Public IP ke saath:

```bash
ping -c 4 1.1.1.1
```

Domain ke saath:

```bash
ping -c 4 example.com
```

Interpretation:

- IP ping works, domain ping fails → DNS issue ho sakta hai
- Dono fail → connectivity, routing ya ICMP blocking ho sakti hai
- Ping fail hone ka matlab hamesha internet down nahi hota

## 6. DNS Basics

DNS domain name ko IP address me resolve karta hai.

Example:

```text
example.com → IP address
```

Tools install:

Kali:

```bash
sudo apt install dnsutils -y
```

Termux:

```bash
pkg install dnsutils
```

Lookup:

```bash
nslookup example.com
```

Ya:

```bash
dig example.com
```

Short result:

```bash
dig +short example.com
```

## 7. curl se HTTP Check

Install:

```bash
sudo apt install curl -y
```

Headers:

```bash
curl -I https://example.com
```

Verbose connection details:

```bash
curl -v https://example.com
```

> `-v` output me request/connection details dikh sakti hain. Tokens/cookies wali private URLs ko screenshot ya public log me share mat karo.

## 8. HTTP Status Samjho

Common status codes:

```text
200 = OK
301 = Redirect
302 = Temporary Redirect
403 = Forbidden
404 = Not Found
500 = Server Error
503 = Service Unavailable
```

Sirf status code:

```bash
curl -o /dev/null -s -w "%{http_code}\n" https://example.com
```

## 9. wget se Download Test

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
```

## 10. Listening Ports Dekhna

Kali:

```bash
ss -tuln
```

Meaning:

```text
t = TCP
u = UDP
l = listening
n = numeric addresses/ports
```

Processes ke saath:

```bash
ss -tulnp
```

Rootless environment me process info incomplete ho sakti hai.

## 11. Active Connections

```bash
ss -tun
```

Ye active TCP/UDP connections dikhata hai.

## 12. Safe Local HTTP Server

Practice folder:

```bash
mkdir -p ~/network-lab
cd ~/network-lab
```

File banao:

```bash
echo "Hello from Termux-Sathi local server" > index.html
```

Server start:

```bash
python3 -m http.server 8000 --bind 127.0.0.1
```

Ab same device par doosre terminal/session me:

```bash
curl http://127.0.0.1:8000
```

Expected:

```text
Hello from Termux-Sathi local server
```

Server stop:

```text
Ctrl + C
```

> `127.0.0.1` bind karne se server sirf local device par available rahega. Beginner practice ke liye ye safer default hai.

## 13. Port Check with ss

Server run karte waqt:

```bash
ss -tuln | grep 8000
```

Aapko port 8000 listening state me dikh sakta hai.

## 14. Local Server Header Check

```bash
curl -I http://127.0.0.1:8000
```

## 15. DNS Troubleshooting Flow

Step 1:

```bash
ping -c 4 1.1.1.1
```

Step 2:

```bash
ping -c 4 example.com
```

Step 3:

```bash
nslookup example.com
```

Step 4:

```bash
curl -I https://example.com
```

Is order se problem ko layer-by-layer samajhna easy hota hai.

## 16. Route Troubleshooting

```bash
ip route
```

Agar default route hi missing ho, internet traffic normal tareeke se bahar nahi jayega.

## 17. Interface Troubleshooting

```bash
ip addr
```

Check karo:

- interface UP hai ya nahi
- IP assigned hai ya nahi
- loopback available hai ya nahi

## 18. Port Concept

Network service usually ek port par listen karti hai.

Common examples:

```text
22   = SSH
53   = DNS
80   = HTTP
443  = HTTPS
8000 = Common local dev/testing port
```

Port number service ko identify karne me help karta hai.

## 19. localhost vs LAN IP

Localhost:

```text
127.0.0.1
```

Sirf current device.

LAN IP example:

```text
192.168.x.x
10.x.x.x
```

Ye local network me device ko identify kar sakta hai.

> LAN IP par server expose karne se pehle samjho ki network ke doosre devices usse access kar sakte hain. Beginner practice me localhost better hai.

## 20. Common Network Errors

### Could not resolve host

Example:

```text
Could not resolve host
```

Possible cause:

- DNS problem
- typo in domain
- no network

Check:

```bash
nslookup example.com
```

### Connection refused

```text
Connection refused
```

Possible cause:

- service running nahi
- wrong port
- service localhost ya kisi aur interface par bound hai

Check:

```bash
ss -tuln
```

### Connection timed out

Possible cause:

- route issue
- firewall
- remote service unavailable
- network filtering

### 403 Forbidden

Server ne request receive ki, lekin access deny kiya.

Possible causes:

- authentication required
- permission policy
- blocked client/request
- protected resource

403 ko blindly “internet problem” mat samjho.

## 21. Useful Networking Commands

```text
ip addr        = interfaces/IPs
ip route       = routes/default gateway
hostname       = host name
ping           = reachability test
nslookup       = DNS lookup
dig            = DNS query
curl           = HTTP/HTTPS testing
wget           = download
ss             = sockets/ports
```

## 🧪 Full Networking Practical

```bash
cd ~
mkdir -p network-lab
cd network-lab

echo "Termux-Sathi Network Lab" > index.html

ip addr
ip route
ping -c 4 127.0.0.1
ping -c 4 1.1.1.1
nslookup example.com
curl -I https://example.com
```

Local server:

```bash
python3 -m http.server 8000 --bind 127.0.0.1
```

Doosre terminal/session me:

```bash
curl http://127.0.0.1:8000
ss -tuln | grep 8000
```

## 🎯 Practice Challenge

1. Localhost ping karo.
2. Apna IP/interface dekho.
3. Default route identify karo.
4. Public IP ping test karo.
5. Domain resolve karo.
6. HTTPS headers check karo.
7. Local `index.html` banao.
8. Python local server port 8000 par chalao.
9. `curl` se page open karo.
10. `ss` se listening port verify karo.

---

## ✅ Part 10 Complete

Ab aap networking ka basic troubleshooting flow samajhte ho:

```text
interface → route → IP connectivity → DNS → HTTP/service → port
```

# Part 11 — Linux Users, Environment Variables & Shell Customization

Is part me hum shell environment ko samjhenge aur safely customize karenge.

## 1. Current User Check

```bash
whoami
id
echo "$HOME"
echo "$SHELL"
```

## 2. Environment Variables Dekhna

```bash
env
printenv
```

Specific variable:

```bash
printenv HOME
echo "$HOME"
```

Common variables:

```text
HOME   = home directory
PATH   = executable search locations
USER   = current user
SHELL  = current shell
PWD    = current directory
OLDPWD = previous directory
```

## 3. PATH Samjho

```bash
echo "$PATH"
which python3
```

PATH colon-separated directories ki list hoti hai jahan shell commands search karta hai.

## 4. Temporary Variable

```bash
name="Termux-Sathi"
echo "$name"
```

## 5. export Command

```bash
export PROJECT="Termux-Sathi"
echo "$PROJECT"
printenv PROJECT
```

`export` variable ko child processes ke environment me bhi available banata hai.

## 6. Custom bin Folder

```bash
mkdir -p ~/bin
export PATH="$HOME/bin:$PATH"
```

Check:

```bash
echo "$PATH"
```

## 7. Apna Command Banana

```bash
nano ~/bin/hello-sathi
```

Paste:

```bash
#!/bin/bash
echo "Hello from Termux-Sathi"
```

Executable banao:

```bash
chmod +x ~/bin/hello-sathi
```

Run:

```bash
hello-sathi
```

## 8. Alias

```bash
alias ll='ls -lah'
alias c='clear'
alias gs='git status'
alias ..='cd ..'
```

Check:

```bash
alias
```

Remove:

```bash
unalias ll
```

## 9. .bashrc

Check:

```bash
ls -la ~/.bashrc
```

Agar file nahi ho:

```bash
touch ~/.bashrc
```

## 10. Backup Before Editing

```bash
cp ~/.bashrc ~/.bashrc.backup
```

Ye chhota backup future ki unnecessary suffering se bachata hai. Linux ko drama pasand hai, par humein zaroori nahi. 😄

## 11. Persistent Aliases

```bash
nano ~/.bashrc
```

Add:

```bash
alias ll='ls -lah'
alias gs='git status'
alias c='clear'
```

Reload:

```bash
source ~/.bashrc
```

## 12. Persistent Variable

`~/.bashrc` me:

```bash
export PROJECT="Termux-Sathi"
```

Reload:

```bash
source ~/.bashrc
echo "$PROJECT"
```

## 13. Persistent PATH

`~/.bashrc` me:

```bash
export PATH="$HOME/bin:$PATH"
```

Reload:

```bash
source ~/.bashrc
```

Verify:

```bash
which hello-sathi
```

> Existing `$PATH` ko preserve karo. Sirf `PATH="$HOME/bin"` likhne se important system paths hat sakte hain.

## 14. .bashrc Syntax Check

```bash
bash -n ~/.bashrc
```

Agar output nahi aata, basic syntax generally valid hai.

## 15. Restore .bashrc

Agar problem aaye:

```bash
cp ~/.bashrc.backup ~/.bashrc
source ~/.bashrc
```

## 16. Command History

```bash
history
history | tail
history | grep git
```

History variables:

```bash
echo "$HISTSIZE"
echo "$HISTFILE"
echo "$HISTFILESIZE"
```

Optional values:

```bash
export HISTSIZE=2000
export HISTFILESIZE=5000
```

> Passwords, tokens aur private keys ko command line me type karne se bachna chahiye. Sensitive data history me save ho sakta hai.

## 17. History Clear

```bash
history -c
```

Use carefully, kyunki troubleshooting ke waqt history useful hoti hai.

## 18. Shell Prompt Basics

Check:

```bash
echo "$PS1"
```

Temporary custom prompt:

```bash
PS1='Termux-Sathi $ '
```

Useful prompt:

```bash
PS1='\u@\h:\w\$ '
```

Meaning:

```text
\u = username
\h = hostname
\w = current directory
\$ = prompt symbol
```

Persistent prompt ke liye PS1 line `~/.bashrc` me add ki ja sakti hai.

## 19. Useful .bashrc Example

```bash
export PATH="$HOME/bin:$PATH"
export PROJECT="Termux-Sathi"

alias ll='ls -lah'
alias gs='git status'
alias c='clear'

export HISTSIZE=2000
export HISTFILESIZE=5000

PS1='\u@\h:\w\$ '
```

Syntax check:

```bash
bash -n ~/.bashrc
```

Reload:

```bash
source ~/.bashrc
```

## 🧪 Full Practical

```bash
cp ~/.bashrc ~/.bashrc.backup
mkdir -p ~/bin

cat > ~/bin/hello-sathi <<'EOF'
#!/bin/bash
echo "Hello from Termux-Sathi"
EOF

chmod +x ~/bin/hello-sathi

echo 'export PATH="$HOME/bin:$PATH"' >> ~/.bashrc
echo "alias ll='ls -lah'" >> ~/.bashrc

bash -n ~/.bashrc
source ~/.bashrc

hello-sathi
ll
```

## 🎯 Practice Challenge

1. Current user check karo.
2. HOME aur SHELL variables dekho.
3. PATH print karo.
4. Temporary variable banao.
5. Alias banao.
6. `~/bin` folder PATH me add karo.
7. Custom command banao.
8. `.bashrc` backup lo.
9. Alias persistent banao.
10. `bash -n ~/.bashrc` se syntax verify karo.

---

## ✅ Part 11 Complete

Ab aap samajhte ho:

```text
env
printenv
PATH
export
alias
unalias
.bashrc
source
history
PS1
```

## 🚀 Next

**Part 12 — Python Automation for Termux**

Topics:
- os
- pathlib
- subprocess
- shutil
- file automation
- system info
- logs
- safe command runner
- mini Termux-Sathi automation tool

---

## 🛡️ Safety

यह repository Linux/Termux learning के लिए है. Security-related commands/tools को केवल अपने systems, labs, CTFs या स्पष्ट permission वाले environments पर use करें.
