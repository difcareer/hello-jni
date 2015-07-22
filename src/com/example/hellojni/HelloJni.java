/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hellojni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;


public class HelloJni extends Activity {

    private Set<Class> loadedClasses = new HashSet<Class>();
    private Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tableSize = getTableSize();
        for (int i = 0; i < tableSize; i++) {
            Class clazz = getLoadedClass(i);
            if(clazz!=null && clazz.getCanonicalName()!=null){
                loadedClasses.add(clazz);
                Log.e("loadedClass", clazz.getCanonicalName());
                classLoaders.add(clazz.getClassLoader());
            }
        }
        Log.e("loadedClass", "class size:"+loadedClasses.size()+"");
        for(ClassLoader cl:classLoaders){
            Log.e("loadedClass",cl.toString());
        }
        Log.e("loadedClass","classloader size:"+classLoaders.size()+"");
    }

    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String stringFromJNI();

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native String unimplementedStringFromJNI();

    public native Class getLoadedClass(int index);

    public native int getTableSize();


    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("hello-jni");
    }
}
