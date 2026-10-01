import os
import platform
import shutil

print("=== TERMUX-SATHI PYTHON LAB ===")
print("Current directory:", os.getcwd())
print("System:", platform.system())
print("Machine:", platform.machine())
print("Python:", platform.python_version())

total, used, free = shutil.disk_usage("/")
print("Disk total GB:", round(total / (1024**3), 2))
print("Disk free GB:", round(free / (1024**3), 2))

name = input("Apna naam likho: ").strip() or "User"
print("Hello", name)

tools = ["Termux", "Kali", "Python"]
print("Tools:")
for tool in tools:
    print("-", tool)

print("Python lab complete.")
