#!/usr/bin/env bash

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
free -h 2>/dev/null || true

echo
echo "=== NETWORK ==="
ip addr 2>/dev/null || true
ip route 2>/dev/null || true

echo
echo "=== LISTENING PORTS ==="
ss -tuln 2>/dev/null || true
