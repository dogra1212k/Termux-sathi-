#!/usr/bin/env bash
set -e

LAB="$HOME/termux-sathi-git-lab"
rm -rf "$LAB"
mkdir -p "$LAB"
cd "$LAB"

git init
git config user.name "Termux-Sathi Lab"
git config user.email "termux-sathi@example.invalid"

echo "Termux-Sathi Git Lab" > README.md
git add README.md
git commit -m "Initial lab commit"

echo
echo "=== git status ==="
git status

echo
echo "=== git log ==="
git log --oneline

echo
echo "Git lab complete."
