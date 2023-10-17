package gioco.sound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
/**
 * gestisce un file audio , permette di interrompere , modificare il volume , ricominciare , iniziare e porre in loop
 */
public class Sound {
	private Clip clip;

	public Sound(Clip clip) {
		this.clip = clip;
	}

	public void loop() {
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void loop(int a ) {
		if (clip != null) {
			clip.loop(a);
		}
	}
	
	public boolean isRunning() {
		if(clip!=null) {
			return clip.isRunning();
		}
		return false;
	}

	public void start() {
		if (clip != null) {
			if (clip.getFramePosition() == clip.getFrameLength())
				reset();
			clip.start();
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void restart() {
		if (clip != null) { 
			clip.stop();		
			reset();
			clip.start();
		}
	}
	
	public void reset() {
		if(clip!=null) {
			clip.setFramePosition(0);
			clip.setMicrosecondPosition(0);
		}
	}

	public void reduceVolume() {
		if (clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float value = gainControl.getValue();
			value -= 1.0f;
			if (value >= gainControl.getMinimum())
				gainControl.setValue(value);
		}
	}

	public void setVolume(float v) {
		if (clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if (v < 0)
				v = 0.0f;
			if (v > 100)
				v = 100.0f;
			float value = gainControl.getMaximum()
					- (gainControl.getMaximum() - gainControl.getMinimum()) * ((100 - v) / 100f);
			if (value < gainControl.getMinimum())
				value = gainControl.getMinimum();
			gainControl.setValue(value);
		}
	}

	public void incrementVolume() {
		if (clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float value = gainControl.getValue();
			value += 1.0f;
			if (value <= gainControl.getMaximum())
				gainControl.setValue(value);
		}
	}
}
