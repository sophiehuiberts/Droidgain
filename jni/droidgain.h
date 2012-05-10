/*
 * Droidgain - Copyring 2012 Kees Huiberts
 * 
 * Like all of mp3gain, my code is also released under
 * the LGPL, because I wouldn't know how to link the
 * code in a way compatible with the LGPL, and I like
 * it enough to not find out how I could.
 */

#ifdef __ANDROID__

#ifndef DROIDGAIN_H
#define DROIDGAIN_H

#include <jni.h>


jboolean Java_org_beide_droidgain_AsyncGain_mp3gain( JNIEnv*  env, jobject  thiz, jstring filename);


#endif
#endif