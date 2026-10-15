package com.termuxsathi.app;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.nio.file.Files;
import static org.junit.Assert.*;

public class LearningSafetyTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test public void everyAdvertisedLessonHasContent() {
        assertEquals(54, LessonCatalog.TITLES.length);
        for (int n = 1; n <= LessonCatalog.TITLES.length; n++) {
            assertFalse(LessonCatalog.TITLES[n - 1].trim().isEmpty());
            assertTrue("Missing body for lesson " + n, LessonCatalog.body(n).length() >= 80);
        }
    }

    @Test public void traversalAndSiblingPrefixesCannotEscapeSandbox() throws Exception {
        File root = temporary.newFolder("files");
        SandboxPaths sandbox = new SandboxPaths(root);
        assertTrue(sandbox.contains(root));
        assertTrue(sandbox.contains(new File(root, "demo/note.txt")));
        assertTrue(sandbox.contains(new File(root, "demo/../notes.txt")));
        assertFalse(sandbox.contains(new File(root, "../shared_prefs/progress.xml")));
        assertFalse(sandbox.contains(new File(root, "demo/../../outside")));
        assertFalse(sandbox.contains(new File(root.getParentFile(), "files-other/note.txt")));
    }

    @Test public void symlinkCannotEscapeSandbox() throws Exception {
        File root = temporary.newFolder("files");
        File outside = temporary.newFolder("outside");
        Files.createSymbolicLink(new File(root, "link").toPath(), outside.toPath());
        assertFalse(new SandboxPaths(root).contains(new File(root, "link/secret.txt")));
    }
}
