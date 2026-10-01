package com.termuxsathi.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private final int BG = Color.rgb(7,10,14);
    private final int PANEL = Color.rgb(15,21,28);
    private final int PANEL2 = Color.rgb(19,27,36);
    private final int TEXT = Color.rgb(232,240,244);
    private final int MUTED = Color.rgb(143,158,173);
    private final int GREEN = Color.rgb(62,230,132);
    private final int CYAN = Color.rgb(88,200,255);
    private final int YELLOW = Color.rgb(255,214,102);
    private final int RED = Color.rgb(255,115,115);

    private TextView terminal;
    private EditText input;
    private ScrollView terminalScroll;
    private File currentDir;
    private final List<String> history = new ArrayList<>();
    private SharedPreferences prefs;
    private int lastLesson = 0;
    private int completedLessons = 0;
    private int quizScore = 0;
    private int quizQuestion = 0;
    private int quizCurrentScore = 0;
    private String lastCommand = "help";
    private String currentTopic = "Linux Terminal Basics";
    private String currentTool = "";

    private final String[] lessonTitles = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = getFilesDir();
        prefs = getSharedPreferences("termux_sathi_progress", MODE_PRIVATE);
        lastLesson = prefs.getInt("lastLesson", 0);
        completedLessons = prefs.getInt("completedLessons", 0);
        quizScore = prefs.getInt("quizScore", 0);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setPadding(dp(14), dp(16), dp(14), dp(14));

        root.addView(buildHeader());
        root.addView(space(10));
        root.addView(buildQuickBar());
        root.addView(space(10));

        terminalScroll = new ScrollView(this);
        terminalScroll.setFillViewport(true);
        terminalScroll.setBackground(round(PANEL, 14));

        terminal = new TextView(this);
        terminal.setTextColor(TEXT);
        terminal.setTextSize(13.5f);
        terminal.setTypeface(Typeface.MONOSPACE);
        terminal.setPadding(dp(14), dp(14), dp(14), dp(16));
        terminal.setTextIsSelectable(true);
        terminalScroll.addView(terminal);

        root.addView(terminalScroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

        root.addView(space(10));
        root.addView(buildInputRow());

        setContentView(root);
        bootScreen();
    }

    private View buildHeader() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(16), dp(14), dp(16), dp(14));
        box.setBackground(round(PANEL2, 16));

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView icon = label(">_", 20, GREEN, Typeface.BOLD);
        icon.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        top.addView(icon);

        LinearLayout names = new LinearLayout(this);
        names.setOrientation(LinearLayout.VERTICAL);
        names.setPadding(dp(12),0,0,0);
        names.addView(label("Termux-Sathi Learning Terminal",18,TEXT,Typeface.BOLD));
        names.addView(label("Offline • Self-contained • Safe Practice",11,GREEN,Typeface.BOLD));
        top.addView(names,new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        top.addView(label("v3.0",11,CYAN,Typeface.BOLD));
        box.addView(top);

        TextView hint = label("Commands, lessons aur labs isi app ke andar chalenge.",12,MUTED,Typeface.NORMAL);
        hint.setPadding(0,dp(10),0,0);
        box.addView(hint);
        return box;
    }

    private View buildQuickBar() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);

        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.addView(chip("HELP",v->runCommand("help")),weight(1));
        row1.addView(chip("LESSONS",v->runCommand("lessons")),weight(1));
        row1.addView(chip("TOOLS",v->runCommand("kali-tools")),weight(1));
        row1.addView(chip("CLEAR",v->runCommand("clear")),weight(1));
        box.addView(row1);

        box.addView(space(7));

        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        row2.addView(chip("📖 EXPLAIN",v->runCommand("explain")),weight(1));
        row2.addView(chip("🎓 LESSON",v->runCommand("subject")),weight(1));
        row2.addView(chip(">_ TERMINAL",v->runCommand("terminal")),weight(1));
        box.addView(row2);
        return box;
    }

    private View buildInputRow() {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);

        TextView prompt = label("$",18,GREEN,Typeface.BOLD);
        prompt.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
        prompt.setGravity(Gravity.CENTER);
        row.addView(prompt,new LinearLayout.LayoutParams(dp(34),dp(48)));

        input = new EditText(this);
        input.setSingleLine(true);
        input.setTextColor(TEXT);
        input.setHintTextColor(MUTED);
        input.setHint("command likho...  e.g. lesson 1");
        input.setTextSize(14);
        input.setTypeface(Typeface.MONOSPACE);
        input.setPadding(dp(12),0,dp(12),0);
        input.setBackground(round(PANEL2,12));
        input.setImeOptions(EditorInfo.IME_ACTION_GO);
        input.setOnEditorActionListener((v,actionId,event)->{
            if(actionId==EditorInfo.IME_ACTION_GO ||
                    (event!=null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
                submitInput(); return true;
            }
            return false;
        });
        row.addView(input,new LinearLayout.LayoutParams(0,dp(48),1f));

        TextView run = label("RUN",13,BG,Typeface.BOLD);
        run.setGravity(Gravity.CENTER);
        run.setBackground(round(GREEN,12));
        run.setOnClickListener(v->submitInput());
        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(dp(72),dp(48));
        rp.setMargins(dp(8),0,0,0);
        row.addView(run,rp);
        return row;
    }

    private void submitInput(){
        String cmd=input.getText().toString().trim();
        input.setText("");
        if(!cmd.isEmpty()) runCommand(cmd);
    }

    private void bootScreen(){
        terminal.setText("");
        appendGreen("TERMUX-SATHI LEARNING TERMINAL v3.0\n");
        append("Safe training shell ready. External app ki zarurat nahi.\n\n");
        appendYellow("Start: "); append("help\n");
        appendYellow("Course: "); append("lessons   |   lesson 1   |   next   |   progress\n");
        appendYellow("Practice: "); append("practice linux   |   practice network\n");
        appendYellow("Tools: "); append("kali-tools   |   tool nmap   |   tool burpsuite\n");
        appendYellow("Explain: "); append("explain = terminal me kya likhna hai + usse kya hoga\n");
        appendYellow("Lesson: "); append("subject = current lesson/tool ki full theory + commands\n");
        appendYellow("Test: "); append("quiz   |   challenge   |   cheatsheet   |   badges\n\n");
        append("Filesystem commands app ke private sandbox me real files par kaam karte hain.\n");
        appendPrompt();
    }

    private void runCommand(String raw){
        String cmd=raw.trim();
        if(cmd.isEmpty()) return;
        history.add(cmd);
        appendGreen("\n$ "+cmd+"\n");

        String[] parts=cmd.split("\\s+");
        String base=parts[0].toLowerCase(Locale.ROOT);
        if(!base.equals("explain") && !base.equals("why")) lastCommand = cmd;

        try{
            switch(base){
                case "help": showHelp(); break;
                case "clear": terminal.setText(""); break;
                case "lessons": showLessons(); break;
                case "progress": showProgress(); break;
                case "next": showNextLesson(); break;
                case "quiz": startQuiz(); break;
                case "answer": checkQuizAnswer(parts); break;
                case "cheatsheet": showCheatSheet(); break;
                case "badges": showBadges(); break;
                case "challenge": showChallenge(); break;
                case "lesson": showLesson(parts); break;
                case "practice": showPractice(parts); break;
                case "kali-tools": showKaliTools(); break;
                case "terminal": bootScreen(); break;
                case "subject": showCurrentSubject(); break;
                case "explain":
                case "why": showExplainScreen(lastCommand); break;
                case "tool": showTool(parts); break;
                case "pwd": append(pathOf(currentDir)+"\n"); break;
                case "ls": listFiles(); break;
                case "cd": changeDir(parts); break;
                case "mkdir": makeDir(parts); break;
                case "touch": touch(parts); break;
                case "cat": cat(parts); break;
                case "echo": echoCommand(cmd); break;
                case "rm": remove(parts); break;
                case "history": showHistory(); break;
                case "whoami": append("student\n"); break;
                case "uname": append("Linux termux-sathi 6.x android-sandbox arm64\n"); break;
                case "date": append(new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy",Locale.getDefault()).format(new Date())+"\n"); break;
                case "ip": simulatedIp(parts); break;
                case "ping": simulatedPing(parts); break;
                case "nmap": simulatedNmap(parts); break;
                case "apt":
                case "pkg": simulatedPackage(cmd); break;
                case "git": simulatedGit(parts); break;
                case "python":
                case "python3":
                    appendYellow("Python learning mode\n");
                    append("Try: lesson 15\nBuilt-in lessons aur exercises available hain.\n");
                    break;
                case "reset": resetSandbox(); break;
                default:
                    appendRed("command not found: "+base+"\n");
                    append("help type karo supported commands ke liye.\n");
            }
        }catch(Exception e){
            appendRed("Error: "+e.getMessage()+"\n");
        }
        if(!base.equals("clear")) appendPrompt();
        scrollToBottom();
    }

    private void showHelp(){
        appendYellow("SUPPORTED COMMANDS\n");
        append("Learning:\n  lessons\n  lesson N\n  next\n  progress\n  quiz\n  answer A|B|C\n  challenge\n  cheatsheet\n  badges\n  practice linux\n  practice network\n  kali-tools\n  explain\n  subject\n  terminal\n  tool NAME\n\n");
        append("Filesystem:\n  pwd  ls  cd  mkdir  touch  cat  echo  rm\n\n");
        append("System/training:\n  whoami  uname  date  history  clear  reset\n");
        append("  ip addr  ip route  ping HOST  nmap TARGET\n");
        append("  apt install PKG  pkg install PKG  git status\n\n");
        append("Example:\n  mkdir practice\n  cd practice\n  touch notes.txt\n");
        append("  echo Hello Linux > notes.txt\n  cat notes.txt\n");
    }

    private void showLessons(){
        appendYellow("COURSE LESSONS\n");
        for(int i=0;i<lessonTitles.length;i++){
            append(String.format(Locale.ROOT,"%2d. %s\n",i+1,lessonTitles[i]));
        }
        append("\nOpen with: lesson 1\n");
    }

    private void showLesson(String[] parts){
        if(parts.length<2){append("Usage: lesson N\n");return;}
        try{
            int n=Integer.parseInt(parts[1]);
            if(n<1||n>lessonTitles.length){appendRed("Lesson 1-"+lessonTitles.length+" choose karo.\n");return;}
            appendYellow("LESSON "+n+" — "+lessonTitles[n-1]+"\n\n");
            append(lessonBody(n)+"\n");
            lastLesson = n;
            currentTopic = lessonTitles[n-1];
            currentTool = "";
            if (n > completedLessons) completedLessons = n;
            prefs.edit()
                    .putInt("lastLesson", lastLesson)
                    .putInt("completedLessons", completedLessons)
                    .apply();
            appendGreen("\n✓ Progress saved. Next: "+(n < lessonTitles.length ? "lesson "+(n+1) : "course complete")+"\n");
        }catch(NumberFormatException e){
            appendRed("Example: lesson 3\n");
        }
    }

    private String lessonBody(int n){
        switch(n){
            case 1:return "Terminal text commands se computer ko instructions dene ka interface hai.\nTry: whoami, pwd, date.";
            case 2:return "pwd=current directory, ls=files, cd=directory change.\nTry: mkdir demo; cd demo; pwd; cd ..";
            case 3:return "mkdir folder banata hai, touch file banata hai.\nTry: mkdir lab; cd lab; touch notes.txt; ls";
            case 4:return "cat content dikhata hai. echo text likhta hai. > overwrite, >> append.\nTry: echo Hello > notes.txt; cat notes.txt";
            case 5:return "Permissions: r=read, w=write, x=execute. Numeric: 4 read, 2 write, 1 execute. 755 common executable mode.";
            case 6:return "Process running program hota hai. Linux tools: ps, top, kill. Training app arbitrary Android processes control nahi karta.";
            case 7:return "Kali/Debian: apt update / apt install. Termux: pkg update / pkg install. Yahan install simulated hai.";
            case 8:return "Networking: IP=device address, port=service endpoint, TCP/UDP=transport, DNS=name mapping.";
            case 9:return "ip addr interfaces/IP dikhata hai; ip route routing path. Try: ip addr";
            case 10:return "ping connectivity test hai. Try: ping 127.0.0.1";
            case 11:return "Nmap host/port discovery tool hai. Practice sirf localhost/lab/authorized target par. Try: nmap 127.0.0.1";
            case 12:return "curl HTTP requests inspect karta hai; wget downloads karta hai. Is offline app me web access intentionally disabled hai.";
            case 13:return "DNS tools: dig, host, nslookup. Record types: A, AAAA, MX, TXT, CNAME.";
            case 14:return "Bash scripts commands ko automate karte hain. Variables, if/else, loops, functions important concepts hain.";
            case 15:return "Python basics: print, variables, input, if/else, loops, functions, lists, files.";
            case 16:return "Git: init repo, add stage, commit snapshot, log history. Try: git status";
            case 17:return "Hash file fingerprint hota hai. SHA-256 integrity verification ke liye useful hai.";
            case 18:return "Forensics: evidence preserve, hash, metadata, filesystem analysis. Tools: file, strings, ExifTool, YARA.";
            case 19:return "Kali categories: information gathering, web, passwords, wireless, forensics, reverse engineering, reporting.";
            case 20:return "NetHunter Rootless me userland tools milte hain, but monitor mode, injection, kernel modules aur some hardware features limited hote hain.";
            default:return "";
        }
    }


    private void showProgress(){
        int percent = (int)Math.round((completedLessons * 100.0) / lessonTitles.length);
        appendYellow("COURSE PROGRESS\n");
        append("Completed: "+completedLessons+"/"+lessonTitles.length+" lessons\n");
        append("Progress: "+percent+"%\n");
        append("Last lesson: "+(lastLesson==0 ? "Not started" : lastLesson+" — "+lessonTitles[lastLesson-1])+"\n");
        append("Quiz best score: "+quizScore+"/5\n");
        append("\n"+progressBar(percent)+"\n");
    }

    private String progressBar(int percent){
        int filled = percent / 10;
        StringBuilder b = new StringBuilder("[");
        for(int i=0;i<10;i++) b.append(i<filled ? "#" : "-");
        return b.append("] ").append(percent).append("%").toString();
    }

    private void showNextLesson(){
        int next = lastLesson <= 0 ? 1 : Math.min(lastLesson + 1, lessonTitles.length);
        showLesson(new String[]{"lesson", String.valueOf(next)});
    }

    private void startQuiz(){
        quizQuestion = 1;
        quizCurrentScore = 0;
        appendYellow("QUIZ MODE START\n");
        append("5 questions. Answer with: answer A\n\n");
        showCurrentQuizQuestion();
    }

    private void showCurrentQuizQuestion(){
        String q="";
        switch(quizQuestion){
            case 1: q="1) pwd kya dikhata hai?\nA) current directory  B) password  C) process\n"; break;
            case 2: q="2) ls ka use?\nA) files list  B) login  C) network scan\n"; break;
            case 3: q="3) mkdir kya banata hai?\nA) file  B) directory  C) user\n"; break;
            case 4: q="4) Nmap ka safe beginner use?\nA) localhost/lab scan  B) random targets  C) password theft\n"; break;
            case 5: q="5) SHA-256 kis kaam me useful?\nA) integrity hash  B) video edit  C) Wi-Fi password\n"; break;
        }
        append(q);
    }

    private void checkQuizAnswer(String[] parts){
        if(quizQuestion<1 || quizQuestion>5){
            append("Quiz active nahi hai. Start with: quiz\n");
            return;
        }
        if(parts.length<2){
            append("Usage: answer A\n");
            return;
        }
        String a=parts[1].toUpperCase(Locale.ROOT);
        String correct = quizQuestion==3 ? "B" : "A";
        if(a.equals(correct)){
            quizCurrentScore++;
            appendGreen("✓ Correct!\n");
        }else{
            appendRed("✗ Incorrect. Correct answer: "+correct+"\n");
        }
        quizQuestion++;
        if(quizQuestion>5){
            appendYellow("\nQUIZ COMPLETE\n");
            append("Score: "+quizCurrentScore+"/5\n");
            if(quizCurrentScore>quizScore){
                quizScore=quizCurrentScore;
                prefs.edit().putInt("quizScore",quizScore).apply();
                appendGreen("New best score saved!\n");
            }
            append(quizCurrentScore>=4 ? "Excellent. Fundamentals strong ho rahe hain.\n" :
                    quizCurrentScore>=3 ? "Good. Weak topics ko lessons se revise karo.\n" :
                            "Lessons 1-10 revise karo, phir quiz dubara try karo.\n");
            quizQuestion=0;
        }else{
            showCurrentQuizQuestion();
        }
    }

    private void showCheatSheet(){
        appendYellow("QUICK CHEAT SHEET\n");
        append("pwd              current directory\n");
        append("ls               files/folders list\n");
        append("cd DIR           directory change\n");
        append("mkdir DIR        folder create\n");
        append("touch FILE       file create\n");
        append("cat FILE         file read\n");
        append("echo TEXT > F    file write\n");
        append("whoami           current user\n");
        append("ip addr          network interfaces\n");
        append("ip route         routing table\n");
        append("ping HOST        connectivity test\n");
        append("nmap TARGET      safe training scan\n");
        append("lessons          course list\n");
        append("next             next lesson\n");
        append("progress         course progress\n");
        append("quiz             start quiz\n");
        append("challenge        practical challenge\n");
    }

    private void showBadges(){
        appendYellow("LEARNING BADGES\n");
        append((completedLessons>=1 ? "✓" : "○")+" First Step — lesson 1 complete\n");
        append((completedLessons>=5 ? "✓" : "○")+" Linux Rookie — 5 lessons\n");
        append((completedLessons>=10 ? "✓" : "○")+" Terminal Learner — 10 lessons\n");
        append((completedLessons>=20 ? "✓" : "○")+" Course Finisher — 20 lessons\n");
        append((quizScore>=3 ? "✓" : "○")+" Quiz Pass — score 3/5\n");
        append((quizScore==5 ? "✓" : "○")+" Perfect Score — 5/5\n");
    }

    private void showChallenge(){
        appendYellow("DAILY CHALLENGE\n");
        append("1) mkdir challenge\n");
        append("2) cd challenge\n");
        append("3) touch note.txt\n");
        append("4) echo Linux practice > note.txt\n");
        append("5) cat note.txt\n");
        append("6) cd ..\n");
        append("7) practice network\n");
        append("\nGoal: bina help dekhe sequence complete karo.\n");
    }

    private void showPractice(String[] parts){
        if(parts.length<2){append("practice linux | practice network | practice files\n");return;}
        String t=parts[1].toLowerCase(Locale.ROOT);
        currentTool = t;
        currentTopic = t.toUpperCase(Locale.ROOT)+" Tool";
        if(t.equals("linux")){
            appendYellow("GUIDED LAB — LINUX BASICS\n");
            append("1) pwd\n2) mkdir lab1\n3) cd lab1\n4) touch hello.txt\n5) echo Hello Termux-Sathi > hello.txt\n6) cat hello.txt\n7) ls\n");
        }else if(t.equals("network")){
            appendYellow("GUIDED LAB — NETWORK BASICS\n");
            append("1) ip addr\n2) ip route\n3) ping 127.0.0.1\n4) nmap 127.0.0.1\n5) tool nmap\n");
        }else if(t.equals("files")){
            appendYellow("GUIDED LAB — FILES\n");
            append("mkdir docs\ncd docs\ntouch notes.txt\necho Kali basics > notes.txt\ncat notes.txt\nls\n");
        }else appendRed("Unknown practice topic.\n");
    }

    private void showExplainScreen(String command){
        terminal.setText("");
        appendGreen("📖 EXPLAIN — TERMINAL ME KYA LIKHNA HAI?\n");
        append("────────────────────────────────\n");
        appendYellow("Last command\n");
        append("  "+command+"\n\n");

        String[] p = command.trim().split("\\s+");
        String base = p.length>0 ? p[0].toLowerCase(Locale.ROOT) : "";

        appendYellow("Terminal me kya likho\n");
        append("  "+suggestCommand(base)+"\n\n");

        appendYellow("Enter dabane ke baad kya hoga\n");
        append(explainWork(base, command)+"\n\n");

        appendYellow("Is command ka matlab\n");
        append(explainPurpose(base)+"\n\n");

        appendYellow("Output me kya dekhna hai\n");
        append(explainOutput(base)+"\n\n");

        appendYellow("Practice sequence\n");
        append(explainExample(base)+"\n\n");

        appendYellow("Important\n");
        append(explainTip(base)+"\n\n");

        appendGreen("Current topic ko detail me padhne ke liye LESSON icon dabao.\n");
    }

    private String suggestCommand(String base){
        switch(base){
            case "pwd": return "pwd";
            case "ls": return "ls";
            case "cd": return "mkdir demo   phir   cd demo";
            case "mkdir": return "mkdir practice";
            case "touch": return "touch notes.txt";
            case "cat": return "echo Hello > notes.txt   phir   cat notes.txt";
            case "echo": return "echo Linux practice > notes.txt";
            case "ip": return "ip addr   ya   ip route";
            case "ping": return "ping 127.0.0.1";
            case "nmap": return "nmap 127.0.0.1";
            case "git": return "git status";
            case "tool": return "tool nmap";
            case "lesson": return "lesson 1";
            case "apt":
            case "pkg": return "apt update   ya   pkg install nmap  (simulation)";
            default: return "help";
        }
    }

    private String explainPurpose(String base){
        switch(base){
            case "pwd": return "pwd = print working directory. Yeh batata hai ki tum abhi kis folder ke andar ho.";
            case "ls": return "ls current folder ke files aur folders ki list dikhata hai.";
            case "cd": return "cd directory change karta hai. Matlab terminal ko doosre folder me le jata hai.";
            case "mkdir": return "mkdir naya folder banata hai.";
            case "touch": return "touch nayi empty file banata hai ya existing file ka timestamp update karta hai.";
            case "cat": return "cat text file ka content terminal me dikhata hai.";
            case "echo": return "echo text ko screen par dikhata hai ya > / >> ke saath file me likh sakta hai.";
            case "ip": return "ip Linux networking command family hai. Interfaces aur routes samajhne me use hoti hai.";
            case "ping": return "ping connectivity test ka basic tool hai. Yeh check karta hai ki target reachable hai ya nahi.";
            case "nmap": return "Nmap network discovery aur port/service assessment tool hai. Is app me sirf safe simulation hoti hai.";
            case "apt":
            case "pkg": return "Package manager command software packages ko update/install/manage karne ke liye hoti hai.";
            case "git": return "Git files aur code ki version history manage karta hai.";
            case "tool": return "tool command kisi Kali tool ki learning card kholta hai.";
            case "lesson": return "lesson command structured topic ko step-by-step samjhata hai.";
            default: return "Yeh Termux-Sathi learning command hai. Iska purpose command ko practice ke saath samajhna hai.";
        }
    }

    private String explainWork(String base, String command){
        switch(base){
            case "pwd": return "App ke private learning sandbox ka current path read karke print karta hai.";
            case "ls": return "Current directory ki entries read karke directory/file ke roop me list karta hai.";
            case "cd": return "Requested folder ko validate karta hai aur sandbox ke andar hi current location badalta hai.";
            case "mkdir": return "Sandbox ke andar requested naam ka directory create karta hai.";
            case "touch": return "Sandbox path verify karke file create karta hai.";
            case "cat": return "Requested file ko read-only mode me kholkar line-by-line dikhata hai.";
            case "echo": return "Text ko parse karke output ya sandbox file me write/append karta hai.";
            case "ip": return "Training network information dikhata hai. Real Android network settings change nahi hoti.";
            case "ping": return "Connectivity output simulate karta hai. Real packets send nahi karta.";
            case "nmap": return "Training scan output simulate karta hai. Real target scan nahi hota.";
            case "apt":
            case "pkg": return "Package installation ka learning flow simulate karta hai. APK ke andar real Kali package install nahi hota.";
            case "git": return "Basic Git status/init/log behavior ko training mode me demonstrate karta hai.";
            default: return "Command: "+command+" ko learning shell ke rules ke hisaab se process kiya gaya.";
        }
    }

    private String explainOutput(String base){
        switch(base){
            case "pwd": return "~/ ka matlab app ka private home sandbox hai.";
            case "ls": return "[DIR] folder ko dikhata hai. Bina [DIR] wali entry file hai.";
            case "ip": return "lo = loopback. wlan0 = example wireless interface. CIDR jaise /24 network size batata hai.";
            case "ping": return "bytes reply size, time latency, transmitted/received packet result dikhate hain.";
            case "nmap": return "PORT service endpoint hai, STATE open/closed status, SERVICE expected protocol/service name.";
            case "apt":
            case "pkg": return "Resolving dependency check ko, Installing package setup ko represent karta hai.";
            case "git": return "branch current development line hai; working tree clean matlab pending changes nahi.";
            default: return "Output ki har line command ke result ko dikhati hai. Error aaye to command spelling aur required argument check karo.";
        }
    }

    private String explainExample(String base){
        switch(base){
            case "pwd": return "Try: pwd";
            case "ls": return "Try: mkdir demo  →  ls";
            case "cd": return "Try: mkdir demo  →  cd demo  →  pwd  →  cd ..";
            case "mkdir": return "Try: mkdir practice  →  ls";
            case "touch": return "Try: touch notes.txt  →  ls";
            case "cat": return "Try: echo Hello > notes.txt  →  cat notes.txt";
            case "echo": return "Try: echo Linux practice > notes.txt";
            case "ip": return "Try: ip addr   aur   ip route";
            case "ping": return "Try: ping 127.0.0.1";
            case "nmap": return "Try: nmap 127.0.0.1   (simulation only)";
            case "git": return "Try: git status";
            default: return "Try: help   ya   lessons";
        }
    }

    private String explainTip(String base){
        if(base.equals("nmap") || base.equals("ping"))
            return "Network tools ko apne localhost, apne lab, CTF ya authorized system par hi practice karo.";
        if(base.equals("rm")) return "Delete command ko hamesha path dekh kar use karo. App directory removal ko block karta hai.";
        return "Command ko ratne se zyada useful hai: purpose samjho, ek example chalao, output padho.";
    }

    private void showKaliGuideScreen(){
        terminal.setText("");
        appendGreen("🐉 KALI TOOLS GUIDE\n");
        append("Step-by-step learning library\n");
        append("────────────────────────────────\n\n");

        appendYellow("1. Information Gathering\n");
        append("   nmap • whois • dig • dnsrecon • whatweb\n");
        append("   Kaam: target/lab ke exposed information ko samajhna.\n\n");

        appendYellow("2. Web Security Learning\n");
        append("   burpsuite • nikto • gobuster • sqlmap\n");
        append("   Kaam: apne training web app me requests, paths aur vulnerabilities samajhna.\n\n");

        appendYellow("3. Password Auditing\n");
        append("   john • hashcat • hydra\n");
        append("   Kaam: apne test hashes/accounts ki password strength audit karna.\n\n");

        appendYellow("4. Packet / Network Analysis\n");
        append("   wireshark • tshark • tcpdump\n");
        append("   Kaam: traffic aur PCAP files ko inspect karna.\n\n");

        appendYellow("5. Wireless Concepts\n");
        append("   aircrack-ng • kismet\n");
        append("   Kaam: wireless security concepts. Android/root/kernel limitations apply.\n\n");

        appendYellow("6. Forensics\n");
        append("   yara • exiftool • foremost • autopsy\n");
        append("   Kaam: files, metadata aur evidence analysis.\n\n");

        appendYellow("7. Reverse Engineering\n");
        append("   gdb • radare2 • rizin • ghidra • jadx\n");
        append("   Kaam: binaries/apps ka structure aur behavior samajhna.\n\n");

        appendYellow("8. System / Defensive Audit\n");
        append("   lynis • clamav\n");
        append("   Kaam: system configuration aur defensive checks.\n\n");

        appendGreen("Tool kholne ka format: tool nmap\n");
        append("Har tool card me purpose, safe use aur practical direction milegi.\n");
    }

    private void showCurrentSubject(){
        terminal.setText("");
        appendGreen("🎓 LESSON / TOOL EXPLAINER\n");
        append("────────────────────────────────\n");
        appendYellow("Current topic: "+currentTopic+"\n\n");

        if(!currentTool.isEmpty()){
            append("Yeh Kali/Linux security tool learning page hai.\n");
            append("Tool: "+currentTool+"\n");
            append("Purpose: "+toolPurpose(currentTool)+"\n\n");
            appendYellow("Command format\n");
            append(toolCommand(currentTool)+"\n\n");
            appendYellow("Command parts ka meaning\n");
            append(toolCommandMeaning(currentTool)+"\n\n");
            appendYellow("Safe practical\n");
            append(toolSafePractice(currentTool)+"\n\n");
            appendYellow("Restricted / sensitive area\n");
            append("Agar tool credential attacks, exploitation, wireless attacks ya payloads se related hai, app sirf lab/CTF/simulation workflow samjhata hai. Real unauthorized target instructions intentionally nahi deta.\n");
        }else{
            int n = lastLesson<=0 ? 1 : lastLesson;
            append("Lesson "+n+": "+lessonTitles[n-1]+"\n\n");
            append(lessonBody(n)+"\n\n");
            appendYellow("Try these commands\n");
            append(lessonCommands(n)+"\n\n");
            appendYellow("Har command ka kaam\n");
            append(lessonCommandMeaning(n)+"\n");
        }
    }

    private String lessonCommands(int n){
        if(n<=4) return "pwd\nls\nmkdir demo\ncd demo\ntouch notes.txt\necho Hello > notes.txt\ncat notes.txt";
        if(n<=10) return "whoami\nuname\nip addr\nip route\nping 127.0.0.1";
        if(n==11) return "nmap 127.0.0.1";
        if(n<=20) return "help\nkali-tools\ntool nmap\nprogress";
        if(n<=24) return "ip addr\nip route\nping 127.0.0.1\ntool wireshark";
        if(n<=32) return "tool burpsuite\ntool sqlmap\ntool john\ntool hashcat\ntool hydra";
        if(n<=40) return "tool wireshark\ntool aircrack-ng\ntool metasploit\ntool gdb\ntool jadx";
        if(n<=48) return "tool yara\ntool lynis\nlesson "+n;
        return "lesson "+n+"\nchallenge\nquiz";
    }

    private String lessonCommandMeaning(int n){
        if(n<=4) return "pwd location batata hai; ls list dikhata hai; mkdir folder banata hai; cd folder change karta hai; touch file banata hai; echo likhta hai; cat read karta hai.";
        if(n<=10) return "whoami user batata hai; uname system info; ip addr interfaces; ip route routing; ping connectivity test.";
        if(n==11) return "nmap TARGET ports/services assessment ka syntax hai. Is app me simulation only.";
        if(n<=24) return "Networking commands ko output reading aur troubleshooting ke liye use karo.";
        if(n<=32) return "Web/password tools ko sirf local lab, dummy accounts, test hashes aur CTF me samjho.";
        if(n<=40) return "Wireless/exploitation/reversing topics me concepts, artifacts aur lab workflow focus hai.";
        return "Commands topic ko inspect, simulate ya defensive analysis karne ke liye diye gaye hain.";
    }

    private String toolPurpose(String t){
        switch(t){
            case "nmap": return "Host, port aur service discovery/assessment.";
            case "burpsuite": return "Web request/response interception aur testing.";
            case "sqlmap": return "SQL injection assessment automation in a permitted lab.";
            case "john": return "Test hashes ki password auditing.";
            case "hashcat": return "Offline password hash auditing.";
            case "hydra": return "Authentication testing against test accounts/services.";
            case "wireshark":
            case "tshark":
            case "tcpdump": return "Network packets capture/analysis.";
            case "aircrack-ng": return "Wireless security learning and lab analysis.";
            case "metasploit": return "Exploit-development/testing framework concepts in isolated labs.";
            case "gdb": return "Native binary debugging.";
            case "jadx": return "Android APK decompilation for your own/test apps.";
            case "yara": return "Pattern-based malware/file classification.";
            case "lynis": return "Linux system auditing and hardening.";
            default: return "Kali/Linux security tool. Use 'kali-tools' for category context.";
        }
    }

    private String toolCommand(String t){
        switch(t){
            case "nmap": return "nmap 127.0.0.1";
            case "burpsuite": return "tool burpsuite   (concept card)";
            case "sqlmap": return "tool sqlmap   (lab concept card)";
            case "john": return "tool john   (test-hash workflow)";
            case "hashcat": return "tool hashcat   (offline test-hash workflow)";
            case "hydra": return "tool hydra   (dummy-account lab workflow)";
            case "wireshark":
            case "tshark":
            case "tcpdump": return "tool "+t+"   (packet-analysis lesson)";
            case "aircrack-ng": return "tool aircrack-ng   (wireless lab concepts)";
            case "metasploit": return "tool metasploit   (isolated-lab concepts)";
            case "gdb": return "tool gdb";
            case "jadx": return "tool jadx";
            case "yara": return "tool yara";
            case "lynis": return "tool lynis";
            default: return "tool "+t;
        }
    }

    private String toolCommandMeaning(String t){
        if(t.equals("nmap")) return "nmap program name hai; 127.0.0.1 localhost hai, yani apna device/lab endpoint.";
        return "'tool' app ka learning command hai; uske baad tool ka naam likhne se uska lesson card khulta hai.";
    }

    private String toolSafePractice(String t){
        if(t.equals("nmap")) return "nmap 127.0.0.1 chalao → output dekho → EXPLAIN icon dabao.";
        if(t.equals("john")||t.equals("hashcat")||t.equals("hydra")) return "Dummy password/test hash lesson padho; real credentials/accounts par use mat karo.";
        if(t.equals("metasploit")||t.equals("sqlmap")||t.equals("aircrack-ng")) return "Isolated intentionally-vulnerable lab/CTF concept page use karo. App real attack execute nahi karta.";
        return "tool "+t+" kholo → LESSON icon dabao → purpose, command meaning aur safe workflow padho.";
    }

    private void showKaliTools(){
        terminal.setText("");
        appendGreen("KALI LINUX TOOLS — LEARNING INDEX\n");
        append("Categories aur common Kali packages. Actual Kali me hundreds of packages hote hain; APK un sab binaries ko bundle nahi karta. Learning index yahan app ke andar hai.\n\n");

        appendYellow("Information Gathering\n");
        append("nmap, masscan, netdiscover, whois, dnsenum, dnsrecon, fierce, recon-ng, theHarvester, whatweb, wafw00f, amass\n\n");
        appendYellow("Vulnerability Analysis\n");
        append("nikto, nuclei, lynis, openvas/gvm concepts, searchsploit\n\n");
        appendYellow("Web Application\n");
        append("burpsuite, zap concepts, gobuster, dirb, dirsearch, feroxbuster, sqlmap, wfuzz, ffuf, commix concepts\n\n");
        appendYellow("Password / Authentication Auditing\n");
        append("john, hashcat, hydra, medusa, crunch, cewl, wordlists\n\n");
        appendYellow("Wireless\n");
        append("aircrack-ng suite, kismet, reaver/bully concepts, hcxdumptool concepts\n\n");
        appendYellow("Sniffing / Spoofing / Traffic Analysis\n");
        append("wireshark, tshark, tcpdump, ettercap concepts, bettercap concepts\n\n");
        appendYellow("Exploitation Frameworks — lab concepts\n");
        append("metasploit-framework, searchsploit, exploitdb concepts\n\n");
        appendYellow("Forensics\n");
        append("autopsy, sleuthkit, binwalk, foremost, exiftool, volatility concepts, yara, strings, file\n\n");
        appendYellow("Reverse Engineering\n");
        append("gdb, radare2, rizin, ghidra, jadx, apktool concepts, strace, ltrace\n\n");
        appendYellow("Reporting / Defensive / Utility\n");
        append("lynis, clamav, openssl, curl, wget, git, python, bash, netcat concepts, socat concepts\n\n");

        append("Open: tool nmap   |   tool burpsuite   |   tool john   |   tool metasploit\n");
        append("Then LESSON icon dabao for full explanation.\n");
    }

    private void showTool(String[] parts){
        if(parts.length<2){append("Usage: tool NAME\n");return;}
        String t=parts[1].toLowerCase(Locale.ROOT);
        switch(t){
            case "nmap":
                appendYellow("NMAP — SUBJECTIVE GUIDE\n");
                append("Purpose: hosts, ports aur services discover/assess karna.\n");
                append("Basic syntax: nmap TARGET\n");
                append("Safe practical: nmap 127.0.0.1\n");
                append("Output: PORT = endpoint, STATE = open/closed, SERVICE = detected service.\n");
                append("App behavior: real scan nahi hota; educational simulation hoti hai.\n");
                append("Rule: localhost, apna lab, CTF ya authorized systems only.\n");
                append("Next step: nmap 127.0.0.1  →  explain\n");
                break;
            case "wireshark":
            case "tshark":
                appendYellow("WIRESHARK / TSHARK\n");
                append("Purpose: packet capture analysis. Offline PCAP analysis defensive learning ke liye useful hai.\n");
                break;
            case "burpsuite":
                appendYellow("BURP SUITE\n");
                append("Purpose: web requests inspect/debug karna. Training web app ke saath use hota hai.\n");
                break;
            case "sqlmap":
                appendYellow("SQLMAP\n");
                append("Purpose: SQL injection assessment automation. Practice only intentionally vulnerable local lab/CTF par.\n");
                break;
            case "john":
            case "hashcat":
            case "hydra":
                appendYellow(t.toUpperCase(Locale.ROOT)+"\n");
                append("Purpose: password/authentication auditing. Use only test hashes/accounts in your own lab.\n");
                break;
            case "metasploit":
                appendYellow("METASPLOIT\n");
                append("Purpose: security testing framework. Is learning app me exploitation execute nahi hota; concepts/lab use only.\n");
                break;

            case "aircrack-ng":
                appendYellow("AIRCRACK-NG — WIRELESS LAB CONCEPT\n");
                append("Purpose: wireless security auditing concepts. Android hardware/root/kernel limits apply.\nPractice: isolated lab captures/CTF material only.\n");
                break;
            case "jadx":
                appendYellow("JADX\n");
                append("Purpose: Android APK bytecode/resources inspect karna, apne/test apps par.\n");
                break;
            case "lynis":
                appendYellow("LYNIS\n");
                append("Purpose: Linux system audit aur hardening recommendations.\n");
                break;
            case "tcpdump":
                appendYellow("TCPDUMP\n");
                append("Purpose: packet capture/inspection concepts. App live capture execute nahi karta.\n");
                break;
            case "yara":
                appendYellow("YARA\n");
                append("Purpose: files ko pattern rules se classify karna. Defensive malware/forensics workflows me useful.\n");
                break;
            case "gdb":
                appendYellow("GDB\n");
                append("Purpose: native programs debug karna aur execution inspect karna.\n");
                break;
            default:
                append("Tool info not built-in yet. Try: nmap, wireshark, burpsuite, sqlmap, john, hashcat, hydra, metasploit, yara, gdb\n");
        }
    }

    private void listFiles(){
        File[] files=currentDir.listFiles();
        if(files==null||files.length==0){append("(empty)\n");return;}
        Arrays.sort(files);
        for(File f:files) append((f.isDirectory()?"[DIR] ":"      ")+f.getName()+"\n");
    }

    private void changeDir(String[] parts){
        if(parts.length<2){currentDir=getFilesDir();append(pathOf(currentDir)+"\n");return;}
        if(parts[1].equals("..")){
            File p=currentDir.getParentFile();
            if(p!=null && isInsideSandbox(p)) currentDir=p;
            append(pathOf(currentDir)+"\n"); return;
        }
        File next=new File(currentDir,parts[1]);
        if(next.isDirectory() && isInsideSandbox(next)) currentDir=next;
        else appendRed("Directory not found.\n");
    }

    private void makeDir(String[] parts){
        if(parts.length<2){append("Usage: mkdir NAME\n");return;}
        File d=new File(currentDir,parts[1]);
        append(d.mkdirs()||d.isDirectory()?"Directory created.\n":"Could not create directory.\n");
    }

    private void touch(String[] parts) throws Exception{
        if(parts.length<2){append("Usage: touch FILE\n");return;}
        File f=new File(currentDir,parts[1]);
        if(!isInsideSandbox(f)){appendRed("Blocked path.\n");return;}
        if(f.exists()) f.setLastModified(System.currentTimeMillis());
        else f.createNewFile();
        append("File ready.\n");
    }

    private void cat(String[] parts) throws Exception{
        if(parts.length<2){append("Usage: cat FILE\n");return;}
        File f=new File(currentDir,parts[1]);
        if(!f.isFile()||!isInsideSandbox(f)){appendRed("File not found.\n");return;}
        BufferedReader br=new BufferedReader(new FileReader(f));
        String line;
        while((line=br.readLine())!=null) append(line+"\n");
        br.close();
    }

    private void echoCommand(String cmd) throws Exception{
        int appendPos=cmd.indexOf(">>");
        int writePos=cmd.indexOf(">");
        boolean app=appendPos>=0;
        int pos=app?appendPos:writePos;
        if(pos<0){append(cmd.substring(4).trim()+"\n");return;}
        String text=cmd.substring(4,pos).trim();
        String file=cmd.substring(pos+(app?2:1)).trim();
        if(file.isEmpty()){appendRed("Filename missing.\n");return;}
        File f=new File(currentDir,file);
        if(!isInsideSandbox(f)){appendRed("Blocked path.\n");return;}
        FileWriter fw=new FileWriter(f,app);
        fw.write(text+"\n");
        fw.close();
        append("Written to "+file+"\n");
    }

    private void remove(String[] parts){
        if(parts.length<2){append("Usage: rm FILE\n");return;}
        File f=new File(currentDir,parts[1]);
        if(!isInsideSandbox(f)){appendRed("Blocked path.\n");return;}
        if(f.isDirectory()){appendRed("Directory removal disabled in learning mode.\n");return;}
        append(f.delete()?"Removed.\n":"File not found.\n");
    }

    private void showHistory(){
        for(int i=0;i<history.size();i++) append((i+1)+"  "+history.get(i)+"\n");
    }

    private void simulatedIp(String[] parts){
        if(parts.length>1 && parts[1].equals("route")){
            append("default via 10.0.2.2 dev wlan0\n10.0.2.0/24 dev wlan0\n");
        }else{
            append("1: lo    127.0.0.1/8\n2: wlan0 10.0.2.15/24\n");
            appendYellow("(training simulation)\n");
        }
    }

    private void simulatedPing(String[] parts){
        String host=parts.length>1?parts[1]:"127.0.0.1";
        append("PING "+host+" (training simulation)\n");
        append("64 bytes from "+host+": time=1.1 ms\n64 bytes from "+host+": time=0.9 ms\n");
        append("--- 2 packets transmitted, 2 received ---\n");
    }

    private void simulatedNmap(String[] parts){
        String target=parts.length>1?parts[1]:"127.0.0.1";
        append("Starting Nmap training simulation for "+target+"\n");
        append("PORT     STATE SERVICE\n");
        append("8000/tcp open  http-alt\n");
        append("Nmap done: 1 host up\n");
        appendYellow("Simulation only. Real scanning is not performed.\n");
    }

    private void simulatedPackage(String cmd){
        append("Package manager training simulation\n");
        if(cmd.contains("install")) append("Resolving package... done\nInstalling... done (simulated)\n");
        else if(cmd.contains("update")) append("Package lists updated (simulated)\n");
        else append("Try: apt update | apt install nmap\n");
    }

    private void simulatedGit(String[] parts){
        String sub=parts.length>1?parts[1]:"";
        if(sub.equals("status")) append("On branch learning\nnothing to commit, working tree clean\n");
        else if(sub.equals("init")) append("Initialized empty Git repository (simulation)\n");
        else if(sub.equals("log")) append("a1b2c3d Initial learning commit\n");
        else append("Try: git status | git init | git log\n");
    }

    private void resetSandbox(){
        deleteChildren(getFilesDir());
        currentDir=getFilesDir();
        append("Learning sandbox reset.\n");
    }

    private void deleteChildren(File dir){
        File[] fs=dir.listFiles();
        if(fs==null) return;
        for(File f:fs){
            if(f.isDirectory()) deleteChildren(f);
            f.delete();
        }
    }

    private boolean isInsideSandbox(File f){
        try{
            String base=getFilesDir().getCanonicalPath();
            String p=f.getCanonicalPath();
            return p.equals(base)||p.startsWith(base+File.separator);
        }catch(Exception e){return false;}
    }

    private String pathOf(File f){
        try{
            String base=getFilesDir().getCanonicalPath();
            String p=f.getCanonicalPath();
            if(p.equals(base)) return "~/";
            return "~"+p.substring(base.length());
        }catch(Exception e){return "~/";}
    }

    private void appendPrompt(){ appendGreen("\nstudent@termux-sathi:"+pathOf(currentDir)+" $ "); }
    private void append(String s){ terminal.append(s); }
    private void appendGreen(String s){ appendColored(s,GREEN); }
    private void appendYellow(String s){ appendColored(s,YELLOW); }
    private void appendRed(String s){ appendColored(s,RED); }

    private void appendColored(String s,int color){
        int start=terminal.length();
        terminal.append(s);
        android.text.Spannable sp=(android.text.Spannable)terminal.getText();
        sp.setSpan(new android.text.style.ForegroundColorSpan(color),start,terminal.length(),android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void scrollToBottom(){
        terminalScroll.post(()->terminalScroll.fullScroll(View.FOCUS_DOWN));
    }

    private TextView chip(String s,View.OnClickListener l){
        TextView t=label(s,11,TEXT,Typeface.BOLD);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(4),dp(10),dp(4),dp(10));
        t.setBackground(round(PANEL2,10));
        t.setOnClickListener(l);
        return t;
    }

    private LinearLayout.LayoutParams weight(float w){
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,w);
        p.setMargins(dp(3),0,dp(3),0);
        return p;
    }

    private TextView label(String s,float size,int color,int style){
        TextView t=new TextView(this);
        t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT,style);
        return t;
    }

    private GradientDrawable round(int color,int radius){
        GradientDrawable g=new GradientDrawable();
        g.setColor(color); g.setCornerRadius(dp(radius));
        return g;
    }

    private View space(int h){
        View v=new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(1,dp(h)));
        return v;
    }

    private int dp(int v){
        return (int)(v*getResources().getDisplayMetrics().density+0.5f);
    }
}
