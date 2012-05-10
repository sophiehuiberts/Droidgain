LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := mp3gain
LOCAL_SRC_FILES := droidgain.c mp3gain.c gain_analysis.c \
									 apetag.c id3tag.c rg_error.c

include $(BUILD_SHARED_LIBRARY)