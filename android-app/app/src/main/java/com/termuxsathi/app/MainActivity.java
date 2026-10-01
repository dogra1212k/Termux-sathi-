package com.termuxsathi.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String REPO = "https://github.com/dogra1212k/Termux-sathi-";
    private static final String CLONE_COMMAND =
            "git clone https://github.com/dogra1212k/Termux-sathi-.git\n" +
            "cd Termux-sathi-\n" +
            "chmod +x setup.sh\n" +
            "./setup.sh\n" +
            "./termux-sathi.sh";

    private final int BG = Color.rgb(10, 14, 20);
    private final int CARD = Color.rgb(20, 27, 36);
    private final int CARD_2 = Color.rgb(25, 34, 45);
    private final int TEXT = Color.rgb(240, 245, 250);
    private final int MUTED = Color.rgb(158, 170, 184);
    private final int GREEN = Color.rgb(50, 215, 120);
    private final int BLUE = Color.rgb(84, 160, 255);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(BG);
        scroll.setFillViewport(true);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(22), dp(18), dp(28));
        scroll.addView(root);

        root.addView(heroCard());
        root.addView(space(18));

        addSectionTitle(root, "Quick Start");
        root.addView(quickStartCard());
        root.addView(space(18));

        addSectionTitle(root, "Learning Hub");
        root.addView(twoColumnRow(
                actionCard("📚", "Master Index", "Complete roadmap & categories",
                        v -> openUrl(REPO + "/blob/main/KALI_MASTER_INDEX.md")),
                actionCard("🧪", "Safe Labs", "Localhost practical exercises",
                        v -> openUrl(REPO + "/blob/main/SAFE_LABS.md"))
        ));
        root.addView(space(10));
        root.addView(twoColumnRow(
                actionCard("🐉", "Kali Tools", "Commands, tools & details",
                        v -> openUrl(REPO + "/blob/main/KALI_TOOLS_GUIDE.md")),
                actionCard("📱", "Rootless Guide", "NetHunter on Android",
                        v -> openUrl(REPO + "/blob/main/NETHUNTER_ROOTLESS_GUIDE.md"))
        ));

        root.addView(space(18));
        addSectionTitle(root, "Actions");
        root.addView(wideAction("⌨", "Open Termux", "Launch Termux app", v -> openTermux()));
        root.addView(space(10));
        root.addView(wideAction("↗", "Open GitHub Repository", "Browse source, guides and updates",
                v -> openUrl(REPO)));

        root.addView(space(18));
        root.addView(safetyCard());

        setContentView(scroll);
    }

    private View heroCard() {
        LinearLayout box = verticalBox(CARD, 22);
        box.setPadding(dp(22), dp(22), dp(22), dp(22));

        TextView badge = text("● READY", 12, GREEN, Typeface.BOLD);
        badge.setPadding(dp(10), dp(5), dp(10), dp(5));
        badge.setBackground(round(Color.rgb(18, 63, 43), 30));
        LinearLayout.LayoutParams badgeParams = wrap();
        badgeParams.gravity = Gravity.START;
        box.addView(badge, badgeParams);

        TextView title = text("Termux-Sathi", 30, TEXT, Typeface.BOLD);
        title.setPadding(0, dp(14), 0, dp(6));
        box.addView(title);

        TextView sub = text("Termux + Kali NetHunter Rootless learning toolkit", 15, MUTED, Typeface.NORMAL);
        box.addView(sub);

        TextView version = text("Android Companion • v1.1", 12, BLUE, Typeface.BOLD);
        version.setPadding(0, dp(16), 0, 0);
        box.addView(version);

        return box;
    }

    private View quickStartCard() {
        LinearLayout box = verticalBox(CARD_2, 18);
        box.setPadding(dp(18), dp(18), dp(18), dp(18));

        TextView title = text("Setup in Termux", 18, TEXT, Typeface.BOLD);
        box.addView(title);

        TextView desc = text("Clone repo, run setup, then launch Termux-Sathi.", 13, MUTED, Typeface.NORMAL);
        desc.setPadding(0, dp(5), 0, dp(14));
        box.addView(desc);

        TextView code = text(
                "git clone github.com/dogra1212k/Termux-sathi-.git\n" +
                "cd Termux-sathi- && ./setup.sh\n" +
                "./termux-sathi.sh",
                13, Color.rgb(210, 255, 225), Typeface.MONOSPACE
        );
        code.setPadding(dp(14), dp(12), dp(14), dp(12));
        code.setBackground(round(Color.rgb(8, 18, 14), 12));
        box.addView(code, match());

        TextView copy = pillButton("Copy setup commands", GREEN, Color.rgb(5, 25, 15));
        copy.setOnClickListener(v -> {
            copyText("Termux-Sathi setup", CLONE_COMMAND);
            toast("Setup commands copied ✓");
        });
        LinearLayout.LayoutParams p = match();
        p.setMargins(0, dp(14), 0, 0);
        box.addView(copy, p);

        return box;
    }

    private View actionCard(String icon, String title, String desc, View.OnClickListener click) {
        LinearLayout card = verticalBox(CARD, 18);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setOnClickListener(click);
        card.setClickable(true);

        TextView ico = text(icon, 25, TEXT, Typeface.NORMAL);
        card.addView(ico);

        TextView t = text(title, 16, TEXT, Typeface.BOLD);
        t.setPadding(0, dp(10), 0, dp(5));
        card.addView(t);

        TextView d = text(desc, 12, MUTED, Typeface.NORMAL);
        card.addView(d);

        return card;
    }

    private View wideAction(String icon, String title, String desc, View.OnClickListener click) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(15), dp(16), dp(15));
        row.setBackground(round(CARD, 16));
        row.setOnClickListener(click);
        row.setClickable(true);

        TextView ico = text(icon, 22, GREEN, Typeface.BOLD);
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(dp(38), dp(38));
        ico.setGravity(Gravity.CENTER);
        ico.setBackground(round(Color.rgb(15, 50, 34), 12));
        row.addView(ico, ip);

        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(LinearLayout.VERTICAL);
        labels.setPadding(dp(14), 0, 0, 0);
        TextView t = text(title, 16, TEXT, Typeface.BOLD);
        TextView d = text(desc, 12, MUTED, Typeface.NORMAL);
        labels.addView(t);
        labels.addView(d);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        row.addView(labels, lp);

        TextView arrow = text("›", 30, MUTED, Typeface.NORMAL);
        row.addView(arrow);

        return row;
    }

    private View safetyCard() {
        LinearLayout card = verticalBox(Color.rgb(36, 30, 18), 16);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));

        TextView t = text("🛡 Safe Learning", 15, Color.rgb(255, 212, 100), Typeface.BOLD);
        card.addView(t);

        TextView d = text(
                "Security commands ko sirf apne device, localhost, lab, CTF ya explicit permission wale system par use karein.",
                12, Color.rgb(218, 205, 170), Typeface.NORMAL
        );
        d.setPadding(0, dp(7), 0, 0);
        card.addView(d);

        return card;
    }

    private LinearLayout twoColumnRow(View left, View right) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        p1.setMargins(0, 0, dp(5), 0);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        p2.setMargins(dp(5), 0, 0, 0);

        row.addView(left, p1);
        row.addView(right, p2);
        return row;
    }

    private void addSectionTitle(LinearLayout root, String title) {
        TextView t = text(title, 14, MUTED, Typeface.BOLD);
        t.setAllCaps(true);
        t.setLetterSpacing(0.08f);
        t.setPadding(dp(2), 0, 0, dp(10));
        root.addView(t);
    }

    private LinearLayout verticalBox(int color, int radius) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setBackground(round(color, radius));
        l.setElevation(dp(2));
        return l;
    }

    private TextView pillButton(String label, int bg, int fg) {
        TextView t = text(label, 15, fg, Typeface.BOLD);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(14), dp(13), dp(14), dp(13));
        t.setBackground(round(bg, 14));
        t.setClickable(true);
        return t;
    }

    private TextView text(String value, float size, int color, int style) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT, style);
        return t;
    }

    private GradientDrawable round(int color, int radiusDp) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(dp(radiusDp));
        return g;
    }

    private View space(int dp) {
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(1, dp(dp)));
        return v;
    }

    private LinearLayout.LayoutParams match() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private LinearLayout.LayoutParams wrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void copyText(String label, String text) {
        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text));
    }

    private void openUrl(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            toast("Browser open nahi ho paya");
        }
    }

    private void openTermux() {
        Intent launch = getPackageManager().getLaunchIntentForPackage("com.termux");
        if (launch != null) {
            startActivity(launch);
        } else {
            copyText("Termux-Sathi setup", CLONE_COMMAND);
            toast("Termux nahi mili. Setup commands copy kar diye.");
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
