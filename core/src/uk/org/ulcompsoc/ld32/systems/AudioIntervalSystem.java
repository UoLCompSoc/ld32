package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import uk.org.ulcompsoc.ld32.util.AudioManager;

/**
 * Created by Samy Narrainen on 18/04/2015.
 * Description: Simulates the functionality of a thread, checking
 * if the working queue of the AudioManager needs to be manipulated
 */
public class AudioIntervalSystem extends IntervalSystem {

    /**
     * The AudioManager that this IntervalSystem will run every interval
     */
    private AudioManager audioManager;

    public AudioIntervalSystem (float interval, AudioManager audioManager) {
        super(interval);
        this.audioManager = audioManager;
    }

    @Override
    protected void updateInterval() {
        audioManager.run();
    }
}