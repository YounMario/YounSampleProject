package com.younchen.younsampleproject.commons.fragment;

import android.text.TextUtils;

import com.younchen.younsampleproject.App;
import com.younchen.younsampleproject.commons.log.YLog;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;

import dalvik.system.DexFile;

/**
 * Created by Administrator on 2017/4/11.
 */

public class FragmentInflater {

    private static final String TAG = "FragmentInflater";

    public static List<Frag> inflate(String pkg, FilenameFilter filter) throws IOException, ClassNotFoundException {
        final String sourcePath = App.getInstance().getApplicationInfo().sourceDir;
        if (sourcePath == null || !new File(sourcePath).exists()) {
            return Collections.EMPTY_LIST;
        }
        final DexFile dexfile = new DexFile(sourcePath);
        final Enumeration<String> entries = dexfile.entries();
        //
        final String prf = pkg + ".";
        final List<Frag> result = new ArrayList<>();
        final int pointLen = pkg.split("\\.").length + 1;
        final TreeMap<String, Frag> parentCache = new TreeMap<>();
        //
        while (entries.hasMoreElements()) {
            final String cName = entries.nextElement();
            if (cName == null || cName.contains("$") || !cName.startsWith(prf)) continue;
            if (filter != null && !filter.accept(null, cName)) continue;
            //get class Name in subPackage;
            String className = cName;

            while (!TextUtils.equals(pkg, className)) {
                //get subclass package name;
                final String pName = className.subSequence(0, className.lastIndexOf('.')).toString();

                final Frag frag = parentCache.containsKey(className) ? parentCache.get(className) : new Frag(className, !className.equals(cName));
                final Frag parent = parentCache.containsKey(pName) ? parentCache.get(pName) : new Frag(pName, true);
                className = pName;
                //
                if (!frag.isParent() || frag.len() == 1) {
                    parent.add(frag);
                    if (TextUtils.equals(pName, pkg) && !result.contains(frag)) {
                        result.add(frag);//到顶层了
                    }
                    YLog.i(TAG, "frag.isParent()=" + frag.isParent() + " parent=" + frag.len() + " " + frag.getName());
                }
                parentCache.put(pName, parent);
            }
        }
        YLog.i(TAG, "result=" + result.size());
        return result;
    }
}
