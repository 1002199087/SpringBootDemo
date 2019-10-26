package com.demo.webdemo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrintUtil {
    public static void d(String title, String content) {
        content = content.replace("\n", "\n║");
        System.out.print("╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n");
        System.out.print("║" + title + "\n");
        System.out.print("╟───────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n");
        System.out.print("║" + content + "\n");
        System.out.print("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n");
    }

    public static void d(String title, Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(o);
        content = content.replace("\n", "\n║");
        d(title, content);
    }
}
