#!/usr/bin/env bash
set -e

if ! command -v nmap >/dev/null 2>&1; then
  echo "nmap installed nahi hai. Pehle ./setup.sh run karo."
  exit 1
fi

LAB_DIR="$HOME/termux-sathi-nmap-lab"
mkdir -p "$LAB_DIR"
cd "$LAB_DIR"
echo "Nmap Lab" > index.html

python3 -m http.server 8000 --bind 127.0.0.1 >server.log 2>&1 &
SERVER_PID=$!

cleanup() {
  kill "$SERVER_PID" 2>/dev/null || true
}
trap cleanup EXIT

sleep 1

echo "=== Port check ==="
nmap -p 8000 127.0.0.1

echo
echo "=== Service detection ==="
nmap -sV -p 8000 127.0.0.1

echo
echo "Nmap local lab complete."
