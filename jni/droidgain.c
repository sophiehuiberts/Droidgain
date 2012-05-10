/*
 * Droidgain - Copyring 2012 Kees Huiberts
 * 
 * Like all of mp3gain, my code is also released under
 * the LGPL, because I wouldn't know how to link the
 * code in a way compatible with the LGPL, and I like
 * it enough to not find out how I could.
 */

#ifdef __ANDROID

#include <jni.h>
#include "mp3gain.h"

jboolean Java_org_beide_droidgain_AsyncGain_mp3gain( JNIEnv*  env, jobject  thiz, jstring filename) {
	
	const char *utf8 = (*env)->GetStringUTFChars(env, filename, NULL);
	assert(NULL != utf8);
	
	int argc = 3;
	char** argv[argc];
	
	// For now, ignore clipping
	argv[1] = "-c";
	
	argv[2] = utf8;
	
	// Just calling mp3gain's main() turned out to be the easiest option :(
	if(0 == main(argc, **argv))
		return JNI_TRUE;
	else
		return JNI_FALSE;
}

#endif