package es.alvsanand.asaengine.graphics.objects;

import java.io.InputStream;
import java.util.HashMap;

import android.util.Log;
import es.alvsanand.asaengine.graphics.objects.error.MeshNotFound;
import es.alvsanand.asaengine.graphics.objects.loaders.obj.ObjLoader;
import es.alvsanand.asaengine.util.io.FileIO;
import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class MeshFactory {
	private final static String TAG = "MeshFactory"; 
	
	public static enum MeshType{
		OBJ
	}
	
	private static HashMap<String, Mesh> meshs = new HashMap<String, Mesh>();
	
	public static Mesh getMeshFromAsset(String asset, MeshType type) throws MeshNotFound{
		if(meshs.containsKey(asset)){
			Log.i(TAG, "Loaded Mesh[" + asset + "] from cache");	
			
			return meshs.get(asset).duplicate();
		}
		else{
			Log.i(TAG, "Loading Mesh[" + asset + "]");
			
			InputStream inputStream = null;
			try {
				inputStream = FileIO.readAsset(asset);
			} catch (ASAIOException e) {
				throw new MeshNotFound("The file " + asset + " cannot be readed", e);
			}
			
			Mesh mesh = null;
			
			switch (type) {
			case OBJ:
				mesh = ObjLoader.loadObj(inputStream);
				break;
			default:
				return null;
			}
			
			meshs.put(asset, mesh);
			
			return mesh;
		}
	}
}
