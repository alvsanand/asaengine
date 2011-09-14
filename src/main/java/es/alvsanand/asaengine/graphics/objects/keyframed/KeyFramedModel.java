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
package es.alvsanand.asaengine.graphics.objects.keyframed;

import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.keyframed.animation.Animation;
import es.alvsanand.asaengine.graphics.textures.Texture;
import es.alvsanand.asaengine.math.Vector3;

public class KeyFramedModel extends Object3D{
	protected final KeyFrame[] keyFrames;
	
	protected Animation animation;
	
	protected final Animation[] animations;

	public KeyFramedModel(Vector3 position, KeyFrame[] keyFrames, Animation[] animations) {
		super(position);
		
		this.keyFrames = keyFrames;
		this.animations = animations;
		
		if(animations!=null && animations.length>0){
			animation = animations[0];
		}
	} 

	@Override
	public void render() {
		if(animation!=null){
			int keyFrame = animation.getKeyFrame() + animation.getKeyFrameOffset() - 1;
			
			if(keyFrame>-1 && keyFrame<keyFrames.length){
				keyFrames[keyFrame].getMesh().setPosition(position);
				
				keyFrames[keyFrame].getMesh().setRx(rx);
				keyFrames[keyFrame].getMesh().setRy(ry);
				keyFrames[keyFrame].getMesh().setRz(rz);
				keyFrames[keyFrame].getMesh().setSx(sx);
				keyFrames[keyFrame].getMesh().setSy(sy);
				keyFrames[keyFrame].getMesh().setSz(sz);
				
				keyFrames[keyFrame].render();
			}
		}
	}

	@Override
	public void dispose() {
		for(int i=0; i<keyFrames.length; i++){
			keyFrames[i].dispose();
		}
	}
	
	public void activateAnimation(int animation){
		if(animation <= animations.length){
			this.animation =  animations[animation];
		}		
	}

	public void start() {
		animation.start();
	}

	public void pause() {
		animation.pause();
	}

	public void resume() {
		animation.resume();
	}

	public boolean isPaused() {
		return animation.isPaused();
	}

	public boolean isEnded() {
		return animation.isEnded();
	}

	public boolean isStarted() {
		return animation.isStarted();
	}
	
	public void setTexture(Texture texture){
		for(int i=0; i<keyFrames.length; i++){
			keyFrames[i].setTexture(texture);
		}
	}
}
