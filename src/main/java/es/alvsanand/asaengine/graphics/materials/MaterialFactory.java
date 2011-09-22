package es.alvsanand.asaengine.graphics.materials;

import java.util.HashMap;

import android.util.Log;

public class MaterialFactory {
	private final static String TAG = "MaterialFactory";
	
	private static HashMap<String, Material> materials = new HashMap<String, Material>();
	
	public static Material getMaterial(String materialId){
		if(materials.containsKey(materialId)){
			Log.i(TAG, "Loaded Material[" + materialId + "] from cache");
			
			return materials.get(materialId);
		}
		else{
			return null;
		}
	}
	
	public static void addMaterial(String materialId, Material material){
		Log.i(TAG, "Added Material[" + materialId + "] to cache");
		
		materials.put(materialId, material);
	}
}