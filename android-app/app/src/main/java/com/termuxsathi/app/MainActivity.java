package com.termuxsathi.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String REPO =
            "https://github.com/dogra1212k/Termux-sathi-";

    private static final String CLONE_COMMAND =
            "git clone https://github.com/dogra1212k/Termux-sathi-.git\n" +
            "cd Termux-sathi-\n" +
            "chmod +x setup.sh\n" +
            "./setup.sh\n" +
            "./termux-sathi.sh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(40, 50, 40, 50);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(root);

        TextView title = new TextView(this);
        title.setText("Termux-Sathi");
        title.setTextSize(30f);
        title.setTextColor(Color.rgb(20, 80, 55));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 12);
        root.addView(title, matchWrap());

        TextView subtitle = new TextView(this);
        subtitle.setText("Termux + Kali NetHunter Rootless learning toolkit");
        subtitle.setTextSize(16f);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 28);
        root.addView(subtitle, matchWrap());

        addButton(root, "Copy Setup Commands", v -> {
            copyText("Termux-Sathi setup", CLONE_COMMAND);
            toast("Setup commands copied");
        });

        addButton(root, "Open GitHub Repository", v -> openUrl(REPO));

        addButton(root, "Open Master Index", v ->
                openUrl(REPO + "/blob/main/KALI_MASTER_INDEX.md"));

        addButton(root, "Open Kali Tools Guide", v ->
                openUrl(REPO + "/blob/main/KALI_TOOLS_GUIDE.md"));

        addButton(root, "Open Safe Labs", v ->
                openUrl(REPO + "/blob/main/SAFE_LABS.md"));

        addButton(root, "Open Rootless Guide", v ->
                openUrl(REPO + "/blob/main/NETHUNTER_ROOTLESS_GUIDE.md"));

        addButton(root, "Open Termux", v -> openTermux());

        TextView note = new TextView(this);
        note.setText(
                "\nSafety: security commands ko sirf apne device, localhost, " +
                "lab, CTF ya explicit permission wale system par use karein.\n\n" +
                "App commands ko copy/open karta hai. Android sandbox ke andar " +
                "Kali/root commands silently execute nahi karta."
        );
        note.setTextSize(14f);
        note.setPadding(0, 24, 0, 0);
        root.addView(note, matchWrap());

        setContentView(scrollView);
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private void addButton(LinearLayout root, String text, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(16f);
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params = matchWrap();
        params.setMargins(0, 10, 0, 10);
        root.addView(button, params);
    }

    private void copyText(String label, String text) {
        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text));
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void openTermux() {
        Intent launch =
                getPackageManager().getLaunchIntentForPackage("com.termux");

        if (launch != null) {
            startActivity(launch);
        } else {
            copyText("Termux-Sathi setup", CLONE_COMMAND);
            toast("Termux app nahi mili. Setup commands clipboard me copy kar diye.");
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
