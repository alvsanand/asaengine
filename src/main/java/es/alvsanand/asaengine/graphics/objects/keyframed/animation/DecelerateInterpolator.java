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

public class DecelerateInterpolator implements Interpolator {
	private float mFactor = 1.0f;

	public DecelerateInterpolator() {
	}

	/**
	 * Constructor
	 * 
	 * @param factor
	 *            Degree to which the animation should be eased. Setting factor
	 *            to 1.0f produces an upside-down y=x^2 parabola. Increasing
	 *            factor above 1.0f makes exaggerates the ease-out effect (i.e.,
	 *            it starts even faster and ends evens slower)
	 */
	public DecelerateInterpolator(float factor) {
		mFactor = factor;
	}

	@Override
	public float getInterpolation(float input) {
		if (mFactor == 1.0f) {
			return (float) (1.0f - (1.0f - input) * (1.0f - input));
		} else {
			return (float) (1.0f - Math.pow((1.0f - input), 2 * mFactor));
		}
	}
}
