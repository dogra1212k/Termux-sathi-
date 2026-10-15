package com.termuxsathi.app;

import java.io.File;
import java.io.IOException;

/** Canonical boundary checks shared by every practice filesystem operation. */
final class SandboxPaths {
    private final File root;

    SandboxPaths(File root) { this.root = root; }

    boolean contains(File candidate) {
        try {
            String base = root.getCanonicalPath();
            String path = candidate.getCanonicalPath();
            return path.equals(base) || path.startsWith(base + File.separator);
        } catch (IOException error) {
            return false;
        }
    }
}
