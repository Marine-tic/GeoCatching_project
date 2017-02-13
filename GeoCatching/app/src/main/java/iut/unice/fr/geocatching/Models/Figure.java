package iut.unice.fr.geocatching.Models;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import iut.unice.fr.geocatching.R;

/**
 * This class manages the figure that will be used in the game
 */
public class Figure {

    private int id = 0;
    private String name;
    private int bigPictureId;
    private int smallPictureId;
    private int imageButtonId;
    private int soundId;
    private MediaPlayer mediaPlayer;
    private ObjectAnimator objectAnimator;


    public Figure(int id, String name, int bigPictureId, int smallPictureId, int imageButtonId, int soundId) {
        this.id = id;
        this.name = name;
        this.bigPictureId = bigPictureId;
        this.smallPictureId = smallPictureId;
        this.imageButtonId = imageButtonId;
        this.soundId = soundId;
        this.objectAnimator = new ObjectAnimator();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getBigPictureId() {
        return bigPictureId;
    }

    public int getSmallPictureId() {
        return smallPictureId;
    }

    public int getImageButtonId() {
        return imageButtonId;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void playSound(Context context) {
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.start();
    }

    public void cancelSound(Context context) {
        mediaPlayer.stop();
    }


    public void animateFigure(Context context, ImageButton figureImageButton) {

        Animation figureAnimation = AnimationUtils.loadAnimation(context, R.anim.figure_animation);

        figureImageButton.setImageResource(bigPictureId);
        figureImageButton.startAnimation(figureAnimation);
        figureImageButton.setImageResource(smallPictureId);
        playSound(context);
    }


    public ObjectAnimator getObjectAnimator() {
        return objectAnimator;
    }

    public void setObjectAnimator(ObjectAnimator objectAnimator) {
        this.objectAnimator = objectAnimator;
    }
}
