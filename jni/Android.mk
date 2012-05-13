LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := mp3gain
LOCAL_SRC_FILES := mp3gain.c apetag.c id3tag.c gain_analysis.c rg_error.c \
	mpglibDBL/common.c mpglibDBL/dct64_i386.c \
	mpglibDBL/decode_i386.c mpglibDBL/interface.c \
	mpglibDBL/layer3.c mpglibDBL/tabinit.c \
	droidgain.c

include $(BUILD_SHARED_LIBRARY)