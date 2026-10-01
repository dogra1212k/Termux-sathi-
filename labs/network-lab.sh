#!/usr/bin/env bash
set -e

LAB_DIR="$HOME/termux-sathi-network-lab"
mkdir -p "$LAB_DIR"
cd "$LAB_DIR"

echo "Termux-Sathi Network Lab" > index.html

echo "Starting local HTTP server on 127.0.0.1:8000"
python3 -m http.server 8000 --bind 127.0.0.1 >server.log 2>&1 &
SERVER_PID=$!

cleanup() {
  kill "$SERVER_PID" 2>/dev/null || true
}
trap cleanup EXIT

sleep 1

echo
echo "=== curl ==="
curl http://127.0.0.1:8000

echo
echo "=== headers ==="
curl -I http://127.0.0.1:8000

echo
echo "=== listening port ==="
ss -tuln | grep 8000 || true

echo
echo "Local network lab complete."
