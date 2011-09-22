package es.alvsanand.asaengine.graphics.materials;

import java.util.HashMap;

import android.util.Log;

public class TextureFactory {
	private final static String TAG = "TextureFactory"; 
	
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTextureFromAsset(String asset){
		if(textures.containsKey(asset)){
			Log.i(TAG, "Loaded Texture[" + asset + "] from cache");	
			
			return textures.get(asset);
		}
		else{
			Log.i(TAG, "Loading Texture[" + asset + "]");
			
			Texture texture =  new Texture(asset, true);
			
			textures.put(asset, texture);
			
			return texture;
		}
	}
}
