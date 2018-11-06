/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.settings.notification;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.Utils;

public class NotificationVolumePreferenceController extends
    RingVolumePreferenceController {

    private static final String KEY_NOTIFICATION_VOLUME = "notification_volume";

    public NotificationVolumePreferenceController(Context context) {
        super(context, KEY_NOTIFICATION_VOLUME);
    }

    @Override
    public int getAvailabilityStatus() {
        return mContext.getResources().getBoolean(R.bool.config_show_notification_volume)
                && !mHelper.isSingleVolume()
                ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_NOTIFICATION_VOLUME);
    }

    @Override
    public String getPreferenceKey() {
        return KEY_NOTIFICATION_VOLUME;
    }

    @Override
    public int getAudioStream() {
        return AudioManager.STREAM_NOTIFICATION;
    }

    @Override
    public int getMuteIcon() {
        if (!Utils.isVoiceCapable(mContext)) {
            return super.getMuteIcon();
        }
        return com.android.internal.R.drawable.ic_audio_notification_mute_new;
    }

    @Override
    protected void updatePreferenceIcon() {
        if (!Utils.isVoiceCapable(mContext)) {
            super.updatePreferenceIcon();
            return;
        }
        if (mPreference != null) {
            mPreference.showIcon(mSuppressor != null
                ? com.android.internal.R.drawable.ic_audio_notification_new
                : mRingerMode == AudioManager.RINGER_MODE_VIBRATE
                    ? com.android.internal.R.drawable.ic_audio_ring_notif_vibrate
                    : com.android.internal.R.drawable.ic_audio_notification_new);
        }
    }
     public VolumeSeekBarPreference getPreference() {
        return mPreference;
    }
}
