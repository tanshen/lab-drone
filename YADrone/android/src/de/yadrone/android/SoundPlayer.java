package de.yadrone.android;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundPlayer {

	private SoundPool soundPool;
	private int soundID;
	private Context context;

	SoundPlayer(Context incontext) {
		this.context = incontext;
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
				float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = actualVolume / maxVolume;
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}
		});
	}


	public void loadAndPlaySound(int resId) {
		soundID = soundPool.load(context, resId, 1);
	}
	
}
