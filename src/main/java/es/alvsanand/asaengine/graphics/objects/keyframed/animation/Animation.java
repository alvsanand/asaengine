/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package es.alvsanand.asaengine.graphics.objects.keyframed.animation;

public class Animation {
	protected Interpolator interpolator;

	protected int keyFrameCount;

	protected int keyFrameOffset;

	protected long startTime;

	protected long lastTime;

	protected long pausedTime;

	protected boolean ended = false;

	protected boolean started = false;

	protected boolean paused = false;

	protected long startOffset;

	protected long duration;

	protected int repeatCount = 0;

	protected int repeated = 0;

	public enum RepeatMode {
		INFINITE, RESTART, REVERSE
	}

	private RepeatMode repeatMode = RepeatMode.RESTART;

	public Animation(Interpolator interpolator, int keyFrameCount, int keyFrameOffset, long frameOffset, long duration, int repeatCount,
			RepeatMode repeatMode) {
		super();
		this.interpolator = interpolator;
		this.keyFrameCount = keyFrameCount;
		this.keyFrameOffset = keyFrameOffset;

		if (keyFrameCount != 0)
			this.startOffset = (duration / keyFrameCount) * startOffset;

		this.duration = duration;
		this.repeatCount = repeatCount;
		this.repeatMode = repeatMode;
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void pause() {
		lastTime = System.currentTimeMillis();

		paused = true;
	}

	public void resume() {
		pausedTime += System.currentTimeMillis() - lastTime;

		paused = false;
	}

	public int getKeyFrame() {

		int nextKeyFrame = 0;

		if (!started) {
			nextKeyFrame = 1;
		}
		else
		{
			final long currentTime = System.currentTimeMillis();

			final long startOffset = this.startOffset;

			final long duration = this.duration;

			float normalizedTime;

			if (duration != 0) {
				normalizedTime = ((float) (currentTime - (startTime + startOffset)) - pausedTime) / (float) duration;
			} else {
				normalizedTime = currentTime < startTime ? 0.0f : 1.0f;
			}
			
			if (repeatCount != 0 && repeatCount <= repeated) {
				switch (repeatMode) {
				case REVERSE:
					if (repeatCount % 2 == 0) {
						nextKeyFrame = keyFrameCount;
					} else {
						nextKeyFrame = 1;
					}
					break;
				default:
					nextKeyFrame = keyFrameCount;
					break;
				}
			} else {
				if (paused) {
					if (duration != 0) {
						normalizedTime = ((float) (lastTime - (startTime + startOffset)) - pausedTime) / (float) duration;
					} else {
						normalizedTime = lastTime < startTime ? 0.0f : 1.0f;
					}

					nextKeyFrame = (int) (keyFrameCount * normalizedTime);

					if (normalizedTime % keyFrameCount > 0) {
						nextKeyFrame++;
					}
				} else {
					final boolean expired = normalizedTime >= 1.0f;

					float fullNormalizedTime = normalizedTime - (int) normalizedTime;

					final float interpolatedTime = interpolator.getInterpolation(fullNormalizedTime);

					if (repeated > 0) {
						switch (repeatMode) {
						case INFINITE:
							nextKeyFrame = keyFrameCount;
							break;
						case RESTART:
							nextKeyFrame = (int) (keyFrameCount * interpolatedTime);

							if (interpolatedTime % keyFrameCount > 0) {
								nextKeyFrame++;
							}
							break;
						case REVERSE:

							if (repeated % 2 == 0) {
								nextKeyFrame = (int) (keyFrameCount * interpolatedTime);

								if (interpolatedTime % keyFrameCount > 0) {
									nextKeyFrame++;
								}
							} else {
								nextKeyFrame = (int) (keyFrameCount * (1 - interpolatedTime));

								if (interpolatedTime % keyFrameCount > 0) {
									nextKeyFrame++;
								}
							}
							break;
						}
					} else {
						if (keyFrameCount == 0) {
							return keyFrameCount;
						}

						nextKeyFrame = (int) (keyFrameCount * interpolatedTime);

						if (interpolatedTime % keyFrameCount > 0) {
							nextKeyFrame++;
						}
					}

					if (expired) {
						if (((int) normalizedTime) > repeated) {
							repeated++;
						}
					}
				}
			}
		}

		return nextKeyFrame;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isEnded() {
		return ended;
	}

	public boolean isStarted() {
		return started;
	}

	public long getDuration() {
		return duration;
	}

	public int getRepeated() {
		return repeated;
	}

	public int getKeyFrameOffset() {
		return keyFrameOffset;
	}
}
