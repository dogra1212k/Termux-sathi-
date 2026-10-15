package com.termuxsathi.app;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LearningAppTest {
    @Test public void filePracticeLessonsQuizAndPrivacyWork() throws Exception {
        android.app.Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        android.content.Context context = instrumentation.getTargetContext();
        context.getSharedPreferences("termux_sathi_progress", 0).edit().clear().commit();
        Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity activity = (MainActivity) instrumentation.startActivitySync(intent);
        try {
            instrumentation.waitForIdleSync();
            saveScreenshot(instrumentation, context, "01-learning-terminal.png");
            instrumentation.runOnMainSync(() -> {
                EditText input = activity.findViewById(R.id.command_input);
                TextView terminal = activity.findViewById(R.id.terminal_output);
                java.util.function.Consumer<String> run = command -> {
                    input.setText(command);
                    activity.findViewById(R.id.run_command).performClick();
                };
                run.accept("mkdir ../escape-test");
                assertTrue(terminal.getText().toString().contains("Blocked path"));
                assertFalse(new java.io.File(activity.getFilesDir().getParentFile(), "escape-test").exists());
                run.accept("mkdir demo"); run.accept("cd demo");
                run.accept("echo hello-world > note.txt"); run.accept("cat note.txt");
                assertTrue(terminal.getText().toString().contains("hello-world"));
                run.accept("lesson 54"); run.accept("lesson 54"); run.accept("progress");
                assertTrue(terminal.getText().toString().contains("Lessons opened: 1/54"));
                assertTrue(terminal.getText().toString().contains("Responsible disclosure"));
                run.accept("ip route");
                assertTrue(terminal.getText().toString().contains("sample addresses, not your device IP"));
                run.accept("quiz"); run.accept("answer Z"); run.accept("answer A");
                assertTrue(terminal.getText().toString().contains("2) ls ka use?"));
                run.accept("clear"); run.accept("lesson 21");
            });
            instrumentation.waitForIdleSync();
            saveScreenshot(instrumentation, context, "02-offline-lesson.png");
            instrumentation.runOnMainSync(() -> {
                EditText input = activity.findViewById(R.id.command_input);
                input.setText("privacy");
                activity.findViewById(R.id.run_command).performClick();
            });
            instrumentation.waitForIdleSync();
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }
    private void saveScreenshot(android.app.Instrumentation instrumentation,
            android.content.Context context, String name) throws Exception {
        java.io.File directory = new java.io.File(context.getExternalFilesDir(null), "screenshots");
        if (!directory.isDirectory() && !directory.mkdirs()) throw new java.io.IOException("Screenshot directory");
        android.graphics.Bitmap screenshot = instrumentation.getUiAutomation().takeScreenshot();
        assertNotNull(screenshot);
        try (java.io.FileOutputStream stream = new java.io.FileOutputStream(new java.io.File(directory, name))) {
            assertTrue(screenshot.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream));
        } finally { screenshot.recycle(); }
    }
}
