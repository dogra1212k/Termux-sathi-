#!/usr/bin/env bash
set -e

BASE="$HOME/termux-sathi-backup-lab"
SRC="$BASE/data"
RESTORE="$BASE/restored"

mkdir -p "$SRC" "$RESTORE"
echo "Termux-Sathi backup test" > "$SRC/file.txt"

cd "$BASE"
tar -czf backup.tar.gz data
sha256sum backup.tar.gz > backup.tar.gz.sha256

echo "=== Hash verification ==="
sha256sum -c backup.tar.gz.sha256

rm -rf "$RESTORE"
mkdir -p "$RESTORE"
tar -xzf backup.tar.gz -C "$RESTORE"

echo
echo "=== Restored files ==="
find "$RESTORE" -type f -maxdepth 3

echo
echo "Backup lab complete."
