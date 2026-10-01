#!/usr/bin/env bash
set -e

echo "================================"
echo "      Termux-Sathi Setup"
echo "================================"

if command -v apt >/dev/null 2>&1; then
  echo "[1/4] Updating package lists..."
  sudo apt update

  echo "[2/4] Installing core packages..."
  sudo apt install -y     git python3 python3-venv curl wget nmap nano tmux     iproute2 iputils-ping dnsutils openssh-client     jq ripgrep shellcheck unzip zip tar

elif command -v pkg >/dev/null 2>&1; then
  echo "[1/4] Updating Termux packages..."
  pkg update -y

  echo "[2/4] Installing core packages..."
  pkg install -y     git python curl wget nmap nano tmux     openssh jq ripgrep shellcheck unzip zip tar

else
  echo "Supported package manager not found."
  exit 1
fi

echo "[3/4] Making project scripts executable..."
chmod +x termux-sathi.sh 2>/dev/null || true
find labs -type f -name "*.sh" -exec chmod +x {} \; 2>/dev/null || true

echo "[4/4] Running basic checks..."
command -v git >/dev/null && git --version
command -v python3 >/dev/null && python3 --version || command -v python >/dev/null && python --version
command -v curl >/dev/null && curl --version | head -n 1
command -v nmap >/dev/null && nmap --version | head -n 1

echo
echo "Setup complete."
echo "Run: ./termux-sathi.sh"
