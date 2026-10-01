#!/usr/bin/env bash

BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

pause() {
  echo
  read -r -p "Continue ke liye Enter dabaye..."
}

run_lab() {
  local file="$1"
  if [ -f "$BASE_DIR/$file" ]; then
    bash "$BASE_DIR/$file"
  else
    echo "Lab file missing: $file"
  fi
  pause
}

while true; do
  clear
  cat <<'EOF'
================================
        TERMUX-SATHI
================================
1. System Check
2. Network Lab
3. Nmap Local Lab
4. Backup Lab
5. Python Practice
6. Git Practice
7. Open Quick Reference
8. Open Kali Master Index
9. Open Safe Labs Guide
10. Exit
EOF

  read -r -p "Option choose karo: " option

  case "$option" in
    1) run_lab "labs/system-check.sh" ;;
    2) run_lab "labs/network-lab.sh" ;;
    3) run_lab "labs/nmap-lab.sh" ;;
    4) run_lab "labs/backup-lab.sh" ;;
    5)
      if command -v python3 >/dev/null 2>&1; then
        python3 "$BASE_DIR/labs/python-lab.py"
      elif command -v python >/dev/null 2>&1; then
        python "$BASE_DIR/labs/python-lab.py"
      else
        echo "Python installed nahi hai."
      fi
      pause
      ;;
    6) run_lab "labs/git-lab.sh" ;;
    7)
      if command -v less >/dev/null 2>&1; then
        less "$BASE_DIR/KALI_QUICK_REFERENCE.md"
      else
        cat "$BASE_DIR/KALI_QUICK_REFERENCE.md"
      fi
      ;;
    8)
      if command -v less >/dev/null 2>&1; then
        less "$BASE_DIR/KALI_MASTER_INDEX.md"
      else
        cat "$BASE_DIR/KALI_MASTER_INDEX.md"
      fi
      ;;
    9)
      if command -v less >/dev/null 2>&1; then
        less "$BASE_DIR/SAFE_LABS.md"
      else
        cat "$BASE_DIR/SAFE_LABS.md"
      fi
      ;;
    10)
      echo "Termux-Sathi closed."
      exit 0
      ;;
    *)
      echo "Invalid option."
      pause
      ;;
  esac
done
