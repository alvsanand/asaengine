package es.alvsanand.asaengine.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;
import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class FileIO {
	private static AssetManager assets;
	private static String externalStoragePath;

	public static void initFileIO(AssetManager assets) {
		FileIO.assets = assets;
		FileIO.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	public static InputStream readAsset(String fileName) throws ASAIOException {
		if(assets == null){
			throw new ASAIOException("Error in FileIO.readAsset(): FileIO is not initialized");
		}
		
		try{
			return assets.open(fileName);
		}
		catch(IOException ioException){
			throw new ASAIOException("Error in FileIO.readAsset()", ioException);
		}
		
	}

	public static InputStream readFile(String fileName) throws ASAIOException {
		if(assets == null){
			throw new ASAIOException("Error in FileIO.readFile(): FileIO is not initialized");
		}
		
		try{
			return new FileInputStream(externalStoragePath + fileName);
		}
		catch(IOException ioException){
			throw new ASAIOException("Error in FileIO.readFile()", ioException);
		}
	}

	public static OutputStream writeFile(String fileName) throws ASAIOException {
		if(assets == null){
			throw new ASAIOException("Error in FileIO.writeFile(): FileIO is not initialized");
		}
		
		try{
			return new FileOutputStream(externalStoragePath + fileName);
		}
		catch(IOException ioException){
			throw new ASAIOException("Error in FileIO.writeFile()", ioException);
		}
	}
}
