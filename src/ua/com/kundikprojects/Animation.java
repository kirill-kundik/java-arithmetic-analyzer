package ua.com.kundikprojects;

import java.awt.Color;
import java.util.Map;

import org.pushingpixels.trident.Timeline;

public class Animation {

	private Timeline timeline;

	/**
	 * duration = 0 for default final duration with 1500 ms
	 * 
	 * @param c
	 * @param map
	 * @param duration
	 */
	public Animation(Object c, Map<String, Color[]> map, int duration) {

		timeline = new Timeline(c);

		timeline.setDuration(duration);

		for (String eachString : map.keySet()) {
			Color[] color = map.get(eachString);
			timeline.addPropertyToInterpolate(eachString, color[0], color[1]);
		}

	}

	public void play() {
		timeline.play();
	}

	public void playReverse() {
		timeline.playReverse();
	}
}
