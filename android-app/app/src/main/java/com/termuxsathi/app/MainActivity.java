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

    private final String[] lessonTitles = {
            "Linux Terminal Basics","pwd, ls aur cd","mkdir, touch aur files",
            "cat aur echo","Permissions ka concept","Processes ka concept",
            "Packages: apt / pkg","Networking basics","IP address aur routes",
            "Ping aur connectivity","Nmap ka safe intro","HTTP: curl / wget",
            "DNS: dig / host","Bash scripting","Python basics","Git basics",
            "File hashes aur integrity","Digital forensics basics",
            "Kali tools categories","NetHunter Rootless limitations"
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
        top.addView(label("v2.0",11,CYAN,Typeface.BOLD));
        box.addView(top);

        TextView hint = label("Commands, lessons aur labs isi app ke andar chalenge.",12,MUTED,Typeface.NORMAL);
        hint.setPadding(0,dp(10),0,0);
        box.addView(hint);
        return box;
    }

    private View buildQuickBar() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(chip("HELP",v->runCommand("help")),weight(1));
        row.addView(chip("LESSONS",v->runCommand("lessons")),weight(1));
        row.addView(chip("TOOLS",v->runCommand("kali-tools")),weight(1));
        row.addView(chip("CLEAR",v->runCommand("clear")),weight(1));
        return row;
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
        appendGreen("TERMUX-SATHI LEARNING TERMINAL v2.0\n");
        append("Safe training shell ready. External app ki zarurat nahi.\n\n");
        appendYellow("Start: "); append("help\n");
        appendYellow("Course: "); append("lessons   |   lesson 1   |   next   |   progress\n");
        appendYellow("Practice: "); append("practice linux   |   practice network\n");
        appendYellow("Tools: "); append("kali-tools   |   tool nmap\n");
        appendYellow("Test: "); append("quiz   |   challenge\n\n");
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

        try{
            switch(base){
                case "help": showHelp(); break;
                case "clear": terminal.setText(""); break;
                case "lessons": showLessons(); break;
                case "progress": showProgress(); break;
                case "next": showNextLesson(); break;
                case "quiz": showQuiz(); break;
                case "challenge": showChallenge(); break;
                case "lesson": showLesson(parts); break;
                case "practice": showPractice(parts); break;
                case "kali-tools": showKaliTools(); break;
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
        append("Learning:\n  lessons\n  lesson N\n  next\n  progress\n  quiz\n  challenge\n  practice linux\n  practice network\n  kali-tools\n  tool NAME\n\n");
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

    private void showQuiz(){
        appendYellow("QUICK QUIZ — 5 QUESTIONS\n");
        append("1) pwd kya dikhata hai?\n");
        append("   A current directory   B password   C process\n");
        append("2) ls ka use?\n");
        append("   A files list   B login   C network scan\n");
        append("3) mkdir kya banata hai?\n");
        append("   A file   B directory   C user\n");
        append("4) Nmap ka safe beginner use?\n");
        append("   A localhost/lab scan   B random targets   C password theft\n");
        append("5) SHA-256 kis kaam me useful?\n");
        append("   A integrity hash   B video edit   C Wi-Fi password\n");
        append("\nAnswers check: quiz-answer A A B A A\n");
        append("Learning mode intentionally open-book hai. Pehle samjho, phir yaad karo.\n");
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

    private void showKaliTools(){
        appendYellow("KALI TOOL CATEGORIES\n");
        append("Information Gathering: nmap, whois, dig\n");
        append("Web: burpsuite, whatweb, nikto, gobuster, sqlmap\n");
        append("Passwords: john, hashcat, hydra\n");
        append("Wireless: aircrack-ng, kismet\n");
        append("Forensics: yara, foremost, autopsy, exiftool\n");
        append("Reverse Engineering: gdb, radare2, rizin, ghidra, jadx\n");
        append("Packet Analysis: tcpdump, tshark, wireshark\n");
        append("System Audit: lynis, clamav\n");
        append("\nUse: tool nmap\n");
    }

    private void showTool(String[] parts){
        if(parts.length<2){append("Usage: tool NAME\n");return;}
        String t=parts[1].toLowerCase(Locale.ROOT);
        switch(t){
            case "nmap":
                appendYellow("NMAP\n");
                append("Purpose: hosts/ports/services discover karna.\nSafe demo: nmap 127.0.0.1\nOnly localhost/lab/authorized systems.\n");
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
