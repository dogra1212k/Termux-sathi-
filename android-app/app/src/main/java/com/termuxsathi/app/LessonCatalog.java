package com.termuxsathi.app;

final class LessonCatalog {
    static final String[] TITLES = {
            "Linux Terminal Basics","pwd, ls aur cd","mkdir, touch aur files",
            "cat aur echo","Permissions ka concept","Processes ka concept",
            "Packages: apt / pkg","Networking basics","IP address aur routes",
            "Ping aur connectivity","Nmap fundamentals","HTTP: curl / wget",
            "DNS: dig / host","Bash scripting","Python basics","Git basics",
            "File hashes aur integrity","Digital forensics basics",
            "Kali tools categories","NetHunter Rootless limitations",
            "Ports, services aur protocols","TCP vs UDP","SSH fundamentals","Web requests aur headers",
            "Burp Suite basics","Content discovery basics","SQL injection concepts","XSS concepts",
            "Authentication security","Password auditing","Hydra concepts in lab","Hashcat/John workflow",
            "Wi-Fi security concepts","Aircrack-ng lab concepts","Packet capture analysis","Wireshark/Tshark",
            "Metasploit framework concepts","Vulnerability validation in lab","Reverse engineering basics","GDB/JADX basics",
            "YARA aur malware triage","Forensics evidence workflow","Privilege escalation concepts","Linux misconfiguration audit",
            "Phishing awareness aur detection","Social engineering defense","Payloads aur shells concepts","Reverse shell detection",
            "Persistence concepts aur detection","Log analysis / incident response",
            "CTF methodology","Safe hacking lab setup","Reporting findings","Responsible disclosure"
    };

    static String body(int n){
        switch(n){
            case 1:return "Terminal text commands se computer ko instructions dene ka interface hai.\nTry: whoami, pwd, date.";
            case 2:return "pwd=current directory, ls=files, cd=directory change.\nTry: mkdir demo\ncd demo\npwd\ncd ..";
            case 3:return "mkdir folder banata hai, touch file banata hai.\nTry: mkdir lab\ncd lab\ntouch notes.txt\nls";
            case 4:return "cat content dikhata hai. echo text likhta hai. > overwrite, >> append.\nTry: echo Hello > notes.txt\ncat notes.txt";
            case 5:return "Permissions: r=read, w=write, x=execute. Numeric: 4 read, 2 write, 1 execute. 755 common executable mode.";
            case 6:return "Process running program hota hai. Linux tools: ps, top, kill. Training app arbitrary Android processes control nahi karta.";
            case 7:return "Kali/Debian: apt update / apt install. Termux: pkg update / pkg install. Yahan install simulated hai.";
            case 8:return "Networking: IP=device address, port=service endpoint, TCP/UDP=transport, DNS=name mapping.";
            case 9:return "ip addr interfaces/IP dikhata hai; ip route routing path. Try: ip addr. Output mein example IP aur interface identify karo; yeh real device details nahi hain.";
            case 10:return "ping connectivity test hai. Try: ping 127.0.0.1. Is app mein replies simulated hain; transmitted, received aur time fields samjho.";
            case 11:return "Nmap host/port discovery tool hai. Practice sirf localhost/lab/authorized target par. Try: nmap 127.0.0.1";
            case 12:return "curl HTTP requests inspect karta hai; wget downloads karta hai. Is offline app me web access intentionally disabled hai.";
            case 13:return "DNS tools: dig, host, nslookup. Record types: A, AAAA, MX, TXT, CNAME. A IPv4 address aur AAAA IPv6 address represent karta hai.";
            case 14:return "Bash scripts commands ko automate karte hain. Variables, if/else, loops, functions important concepts hain.";
            case 15:return "Python basics: print, variables, input, if/else, loops, functions, lists, files.";
            case 16:return "Git: init repo, add stage, commit snapshot, log history. Try: git status. Is app mein Git output simulated hai; actual repository create nahi hoti.";
            case 17:return "Hash file fingerprint hota hai. SHA-256 integrity verification ke liye useful hai.";
            case 18:return "Forensics: evidence preserve, hash, metadata, filesystem analysis. Tools: file, strings, ExifTool, YARA.";
            case 19:return "Kali categories: information gathering, web, passwords, wireless, forensics, reverse engineering, reporting.";
            case 20:return "NetHunter Rootless me userland tools milte hain, but monitor mode, injection, kernel modules aur some hardware features limited hote hain.";
            case 21:return "Ports service endpoints hain; HTTP aksar 80 aur HTTPS 443 use karta hai. Protocol communication ke rules batata hai.\nPractice: nmap 127.0.0.1. Yeh sample output hai; PORT, STATE aur SERVICE columns identify karo.";
            case 22:return "TCP ordered, reliable byte stream deta hai; UDP datagrams deta hai aur delivery guarantee nahi karta. DNS aksar UDP use karta hai, lekin TCP bhi supported hai.\nExercise: file transfer ke liye reliability kyon zaroori hai?";
            case 23:return "SSH encrypted remote login protocol hai. Real Termux mein ssh user@HOST apne server ke liye use hota hai; is app mein SSH client nahi hai.\nHost key fingerprint trusted channel se verify karo. Private key ko secret rakho.";
            case 24:return "HTTP request mein method, path aur headers hote hain; response mein status, headers aur body. GET data read karta hai; POST data submit kar sakta hai.\nExercise: 200 success, 404 missing aur 500 server error ko explain karo.";
            case 25:return "Burp Suite web requests inspect karne ka proxy toolkit hai. Is app mein tool ka introduction milta hai, proxy run nahi hota.\nPractice: tool burpsuite. Apni test app ke request method, URL aur response code ka meaning samjho.";
            case 26:return "Content discovery website ke routes aur resources ko samajhna hai. Apni local test site ke documented routes ki inventory banao.\nMissing resource ka 404 response aur authenticated route ka access check compare karo.";
            case 27:return "SQL injection tab hoti hai jab untrusted input SQL query structure badal deta hai. Defense: parameterized queries aur least-privilege database accounts.\nExercise: user input ko SQL string se jodne ke bajay bound parameter kyon use karte hain?";
            case 28:return "XSS mein untrusted content browser mein script ki tarah interpret hota hai. Defense: context-aware output encoding, safe DOM APIs aur CSP.\nExercise: user text dikhane ke liye textContent aur innerHTML ka farq samjho.";
            case 29:return "Authentication identity verify karta hai; authorization access decide karta hai. Strong passwords, MFA, session expiry aur server-side access checks useful controls hain.\nExercise: login ke baad bhi har protected action par permission check kyon chahiye?";
            case 30:return "Password auditing mein test accounts ya test hashes ki strength evaluate hoti hai. Password storage ke liye salted, slow password hashes use hote hain.\nPractice: tool john. Plain SHA-256 ko password storage scheme samajhne ki galti mat karo.";
            case 31:return "Hydra authentication-audit tool hai. Yahan sirf concept card hai; app login attempts nahi bhejta.\nPractice: tool hydra. Defensive exercise: failed login rate limits aur lockout logs ka review plan likho.";
            case 32:return "John aur Hashcat offline password-hash auditing tools hain. Workflow: authorized test hash, correct format, bounded test, results aur remediation.\nPractice: tool hashcat. Real passwords ya leaked credentials learning notes mein save mat karo.";
            case 33:return "Wi-Fi security mein WPA2/WPA3, strong passphrases aur updated router firmware important hain. Open networks traffic privacy guarantee nahi karte.\nExercise: apne router ki guest-network isolation aur security mode settings ko samjho.";
            case 34:return "Aircrack-ng wireless audit tools ka suite hai. Is app mein wireless capture, injection ya cracking execute nahi hota.\nPractice: tool aircrack-ng. Monitor mode hardware, drivers aur permissions par depend karta hai.";
            case 35:return "Packet captures mein endpoints, timestamps, protocols aur sensitive payload ho sakte hain. Apne synthetic sample ya authorized capture ka analysis karo.\nExercise: source, destination, protocol aur length columns identify karo.";
            case 36:return "Wireshark GUI aur Tshark CLI packet-analysis tools hain. Capture filter recording ko aur display filter visible packets ko restrict karta hai.\nPractice: tool wireshark. Sample trace mein DNS lookup aur HTTP exchange ka order samjho.";
            case 37:return "Metasploit security-testing framework hai. Module selection aur results ko samajhne ke liye isolated training environment use hota hai.\nPractice: tool metasploit. Is app mein exploitation ya payload execution nahi hota.";
            case 38:return "Vulnerability validation mein observation ko repeatable evidence se confirm karte hain. Scope, expected behavior, observed behavior aur impact note karo.\nExercise: demo app ke broken access check ki report outline banao; sensitive data collect mat karo.";
            case 39:return "Reverse engineering compiled program ka structure aur behavior samajhna hai. Static analysis file inspect karta hai; dynamic analysis controlled execution observe karta hai.\nPractice: tool jadx. Apne ya permitted sample apps se start karo.";
            case 40:return "JADX Android bytecode ko readable Java-like code mein dikhata hai; GDB native programs debug karta hai. Decompiled output original source ki exact copy nahi hota.\nPractice: tool gdb aur tool jadx. Symbols aur entry points ka meaning samjho.";
            case 41:return "YARA rules patterns se files classify karte hain; match malware hone ka final proof nahi hota. Triage mein hashes, metadata aur context combine karo.\nPractice: tool yara. Suspicious binaries ko apne everyday device par execute mat karo.";
            case 42:return "Forensics workflow: authorization, preservation, acquisition, integrity checks, analysis aur report. Original evidence preserve karke copy par work karo.\nExercise: evidence log mein source, time, hash aur handling steps ke fields banao.";
            case 43:return "Privilege escalation lower permissions se higher privileges tak pahunchne ko kehte hain. Defensive controls: patches, minimal privileges aur careful service configuration.\nExercise: unnecessary admin rights aur writable configuration files ka audit plan banao.";
            case 44:return "Linux misconfiguration audit mein permissions, exposed services, accounts aur patch state review hote hain. Findings ko verify aur prioritize karo.\nPractice: tool lynis. Is app se Android system settings change nahi hoti.";
            case 45:return "Phishing deceptive messages se credentials ya actions hasil karne ki koshish hai. Sender, domain aur unexpected attachments inspect karo; trusted channel se verify karo.\nExercise: suspicious message mein urgency aur mismatched link ko identify karo.";
            case 46:return "Social engineering human trust aur pressure ka misuse hai. Defense: independent verification, clear approval paths aur secret-sharing boundaries.\nExercise: urgent payment request ko known contact channel se confirm karne ka process likho.";
            case 47:return "Payload program ke task-performing code ko kehte hain; shell command interface hai. Security context mein inka misuse remote access ke liye ho sakta hai.\nExercise: unexpected shell process aur normal terminal session ke context ka farq samjho.";
            case 48:return "Reverse shell detection mein unusual outbound connections aur parent-child process chains correlate karte hain. Har outbound connection malicious nahi hota.\nExercise: suspicious event ka timestamp, parent process aur destination evidence note karo.";
            case 49:return "Persistence reboot ya login ke baad code ko dobara start karne ka mechanism hai. Legitimate services bhi persistence use karti hain.\nExercise: approved startup entries ki baseline banao aur unexpected changes investigate karo.";
            case 50:return "Incident response: prepare, detect, contain, investigate, recover aur improve. Logs mein time zone aur clock differences important hain.\nExercise: sample failed-login events ka timeline banao; findings aur assumptions alag rakho.";
            case 51:return "CTF intentionally designed challenges ka environment hai. Rules aur scope padho, observations note karo aur small hypotheses test karo.\nExercise: challenge command se local file task complete karo aur steps explain karo.";
            case 52:return "Safe lab ko personal data aur production systems se alag rakho. Disposable test data, snapshots aur localhost-only services useful hain.\nPractice: practice linux aur practice network. Network results yahan simulated hain.";
            case 53:return "Finding report mein title, scope, reproduction steps, evidence, impact aur remediation honi chahiye. Severity ko actual evidence ke saath justify karo.\nExercise: ek demo issue ka short report likho; passwords aur personal data redact karo.";
            case 54:return "Responsible disclosure mein owner ki published security policy aur approved contact channel follow karte hain. Scope, evidence aur impact clearly share karo.\nExercise: report ke liye minimal reproducible example aur suggested fix ka outline banao.";
            default:throw new IllegalArgumentException("Unknown lesson: "+n);
        }
    }

}
