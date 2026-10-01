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

## 🚀 Next

**Part 9 — Git & GitHub Basics in Termux/Kali**

Topics:
- git install
- git config
- clone
- status
- add
- commit
- branch
- pull
- push
- GitHub authentication basics

---

## 🛡️ Safety

यह repository Linux/Termux learning के लिए है. Security-related commands/tools को केवल अपने systems, labs, CTFs या स्पष्ट permission वाले environments पर use करें.
