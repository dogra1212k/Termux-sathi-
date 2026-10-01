> 📚 **Navigation:** [Master Index](KALI_MASTER_INDEX.md) · [Complete Guide](KALI_TOOLS_GUIDE.md) · [Practical Guide](KALI_TOOLS_PRACTICAL.md) · [Quick Reference](KALI_QUICK_REFERENCE.md) · [Rootless Guide](NETHUNTER_ROOTLESS_GUIDE.md) · [Safe Labs](SAFE_LABS.md)

# 📱 NetHunter Rootless — Android Guide

Ye guide stock, unrooted Android device par Kali NetHunter Rootless use karne ke liye hai.

> Official Kali docs ke mutabik Rootless edition unmodified Android devices par chal sakti hai, lekin full rooted NetHunter ke hardware features available nahi hote.

## 1. Install

Termux me:

```bash
termux-setup-storage
pkg install wget
wget -O install-nethunter-termux https://offs.ec/2MceZWr
chmod +x install-nethunter-termux
./install-nethunter-termux
```

## 2. Start Kali

```bash
nethunter
```

Short form:

```bash
nh
```

Root-style session inside proot:

```bash
nethunter -r
```

## 3. First Update

```bash
sudo apt update
sudo apt full-upgrade -y
```

Optional default toolset if storage allows:

```bash
sudo apt install kali-linux-default -y
```

## 4. KeX Desktop

Set password:

```bash
nethunter kex passwd
```

Start:

```bash
nethunter kex &
```

Stop:

```bash
nethunter kex stop
```

Kill sessions:

```bash
nethunter -r kex kill
```

GUI tools jaise Burp Suite, Wireshark, Ghidra, Autopsy, ZAP etc. KeX/VNC desktop environment me easier hote hain.

## 5. Rootless Capability Reality

Rootless gives:

```text
Kali CLI
Kali packages
KeX desktop
Metasploit without DB support
standard userland tools
Linux utilities
web/security tooling
forensics/reverse engineering tools
```

Rootless does NOT provide full NetHunter kernel features.

Common unavailable/limited features:

```text
Wi-Fi injection
monitor mode through internal Wi-Fi
HID attacks
BadUSB gadget features
BT Arsenal
CARsenal
EvilTwin kernel-dependent features
raw kernel module access
some USB gadget modes
some firewall/kernel operations
```

## 6. Metasploit Note

Rootless can run Metasploit, but official Kali documentation notes database support is not available in Rootless.

Safe startup:

```bash
msfconsole
```

Inside:

```text
help
version
exit
```

Use exploitation only inside intentional lab/CTF environments.

## 7. Hardware Tool Checks

Architecture:

```bash
uname -m
```

Interfaces:

```bash
ip addr
ip link
```

USB visibility:

```bash
lsusb
```

Wireless tool presence:

```bash
iw dev
aircrack-ng --help
```

Bluetooth:

```bash
bluetoothctl show
```

A tool being installed does not mean Android/proot exposes the hardware it needs.

## 8. Services

Traditional service command may work:

```bash
service --status-all 2>/dev/null
```

Example:

```bash
sudo service ssh status
```

`systemctl` may fail because proot environment often does not run systemd as PID 1.

## 9. Process/Kernel Limitations

Some process utilities may be incomplete or fail on unrooted phones.

Try:

```bash
ps aux
free -h
top
```

If `top` or kernel-dependent utilities fail, it can be a platform restriction rather than a broken Kali package.

## 10. Networking Limitations

Basic networking:

```bash
ip addr
ip route
ping -c 4 1.1.1.1
curl -I https://example.com
```

Listening sockets:

```bash
ss -tuln
```

Kernel firewall tools like nftables/UFW may not have usable privileges in Rootless.

## 11. Safe Local Web Lab

Terminal 1:

```bash
mkdir -p ~/rootless-lab
cd ~/rootless-lab
echo "NetHunter Rootless Lab" > index.html
python3 -m http.server 8000 --bind 127.0.0.1
```

Terminal 2:

```bash
curl http://127.0.0.1:8000
nmap -p 8000 127.0.0.1
whatweb http://127.0.0.1:8000
```

Ye lab Rootless me networking/web tools practice ke liye useful hai.

## 12. Safe Packet Lab

Terminal 1:

```bash
sudo tcpdump -i lo -w ~/rootless-lab/loopback.pcap
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
tshark -r ~/rootless-lab/loopback.pcap
```

If capture permissions fail, that's a Rootless limitation.

## 13. Storage

Check:

```bash
df -h
du -sh ~
```

Large metapackages can consume substantial storage.

Instead of blindly installing everything:

```bash
sudo apt install kali-tools-top10 -y
```

or choose categories:

```bash
sudo apt install kali-tools-web -y
sudo apt install kali-tools-forensics -y
sudo apt install kali-tools-reverse-engineering -y
```

## 14. Rootfs Backup

Before backup, stop NetHunter sessions.

In Termux:

```bash
tar -cJf kali-arm64.tar.xz kali-arm64
mv kali-arm64.tar.xz storage/downloads/
```

On older ARM32 devices, rootfs folder name may differ.

## 15. Troubleshooting

### sudo fails

Some devices may behave differently. Try checking:

```bash
whoami
id
```

### GUI doesn't open

```bash
nethunter kex stop
nethunter kex passwd
nethunter kex &
```

### Package missing

```bash
sudo apt update
apt search PACKAGE
```

### Disk full

```bash
df -h
sudo apt clean
```

### Broken package state

```bash
sudo apt --fix-broken install
sudo dpkg --configure -a
```

### Command installed but feature fails

Check whether it needs:

```text
custom kernel
monitor mode
raw sockets
USB gadget mode
Bluetooth low-level access
GPU/OpenCL
systemd
kernel modules
```

If yes, Rootless may not support that feature.

## 16. Recommended Rootless Tool Groups

Good fit:

```text
Linux CLI
Python/Bash
Git
Nmap on own lab
curl/wget
Burp/ZAP via KeX
SQLMap on local training app
Wireshark/tshark offline analysis
YARA
ExifTool
Binwalk
GDB
Radare2/Rizin
Ghidra
APKTool/JADX
Sleuth Kit
Foremost
Lynis
ClamAV
reporting tools
```

Often hardware/kernel limited:

```text
Aircrack monitor/injection workflows
Kismet capture hardware
Bluetooth attack hardware
RFID/SDR hardware
USB gadget/HID workflows
nftables/UFW enforcement
GPU-heavy Hashcat acceleration
Docker daemon
kernel debugging
```

## 17. Rootless Learning Order

```text
Termux
→ NetHunter install
→ Linux CLI
→ networking
→ Bash/Python
→ local web lab
→ Nmap/curl
→ packet analysis
→ web proxy tools
→ forensics
→ reverse engineering
→ reporting
```

This path avoids wasting hours on hardware features the phone cannot expose. Human beings already have enough hobbies that are secretly troubleshooting sessions. 😄
