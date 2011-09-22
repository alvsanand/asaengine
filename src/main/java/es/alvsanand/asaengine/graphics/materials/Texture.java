package es.alvsanand.asaengine.graphics.materials;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import es.alvsanand.asaengine.error.ASARuntimeException;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.util.io.FileIO;
import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class Texture {
	protected String fileName;
	protected int textureId;
	protected int minFilter;
	protected int magFilter;
	public int width;
	public int height;
	protected boolean mipmapped;

	Texture(String fileName) {
		this(fileName, false);
	}

	Texture(String fileName, boolean mipmapped) {
		this.fileName = fileName;
		this.mipmapped = mipmapped;
		load();
	}

	private void load() {
		int[] textureIds = new int[1];
		OpenGLRenderer.gl.glGenTextures(1, textureIds, 0);
		textureId = textureIds[0];

		InputStream in = null;
		try {
			in = FileIO.readAsset(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			if (mipmapped) {
				createMipmaps(bitmap);
			} else {
				OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
				OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				width = bitmap.getWidth();
				height = bitmap.getHeight();
				bitmap.recycle();
			}
		} catch (ASAIOException e) {
			throw new ASARuntimeException("Couldn't load texture '" + fileName + "'", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
	}

	private void createMipmaps(Bitmap bitmap) {
		OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);

		int level = 0;
		int newWidth = width;
		int newHeight = height;
		while (true) {
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			newWidth = newWidth / 2;
			newHeight = newHeight / 2;
			if (newWidth <= 0 && newHeight <=0)
				break;

			Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
			bitmap.recycle();
			bitmap = newBitmap;
			level++;
		}

		OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		bitmap.recycle();
	}

	public void reload() {
		load();
		bind();
		setFilters(minFilter, magFilter);
		OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}

	public void setFilters(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		OpenGLRenderer.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
		OpenGLRenderer.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	}

	public void bind() {
		OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}

	public void dispose() {
		OpenGLRenderer.gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId };
		OpenGLRenderer.gl.glDeleteTextures(1, textureIds, 0);
	}
}