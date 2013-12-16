package com.wandurr.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds
{
	public Sound	hit;
	public Sound	hit2;
	public Sound	swing;
	public Sound	swing2;
	public Sound	enemykilled;
	public Sound	gameover;

	public Sounds()
	{
		hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		hit2 = Gdx.audio.newSound(Gdx.files.internal("data/hit2.wav"));
		swing = Gdx.audio.newSound(Gdx.files.internal("data/swing.wav"));
		swing2 = Gdx.audio.newSound(Gdx.files.internal("data/swing2.wav"));
		enemykilled = Gdx.audio.newSound(Gdx.files.internal("data/enemykilled.wav"));
		gameover = Gdx.audio.newSound(Gdx.files.internal("data/gameover.wav"));
	}
}
