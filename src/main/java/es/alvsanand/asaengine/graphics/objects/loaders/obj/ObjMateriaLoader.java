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
package es.alvsanand.asaengine.graphics.objects.loaders.obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.materials.Material;
import es.alvsanand.asaengine.graphics.materials.MaterialFactory;
import es.alvsanand.asaengine.graphics.materials.TextureFactory;
import es.alvsanand.asaengine.util.io.FileIO;
import es.alvsanand.asaengine.util.io.error.ASAIOException;
import es.alvsanand.asaengine.util.io.error.MaterialLoadingException;

public class ObjMateriaLoader {
	private final static String NEW_MATERIAL = "newmtl";
	private final static String AMBIENT_COLOR = "Ka";
	private final static String DIFFUSE_COLOR = "Kd";
	private final static String SPECULAR_COLOR = "Ks";
	private final static String TRANSPARENCY_D_COLOR = "d";
	private final static String TRANSPARENCY_TR_COLOR = "Tr";
	private final static String DIFFUSE_TEX_MAP = "map_Kd";

	public static void readMaterialFromLib(String libID) throws MaterialLoadingException{
		String libIDNor = getNormalizedFileName(libID);

		InputStream fileIn = null;
		
		try {
			fileIn = FileIO.readAsset(libIDNor);
		} catch (ASAIOException asaioException) {
			throw new MaterialLoadingException("Cannot load material library[" + libID + "]", asaioException);
		}

		BufferedReader buffer = new BufferedReader(new InputStreamReader(fileIn));
		String line;
		String currentMaterialId = "";

		try {
			Material material = null;
			
			while ((line = buffer.readLine()) != null) {
				String[] parts = line.split(" ");
				if (parts.length == 0)
					continue;
				String type = parts[0];

				if (type.equals(NEW_MATERIAL)) {
					if (material != null && currentMaterialId != null) {
						MaterialFactory.addMaterial(currentMaterialId, material);
					}

					if (parts.length > 1) {
						currentMaterialId = parts[1];
					}
				} else {
					if (MaterialFactory.getMaterial(currentMaterialId) == null && currentMaterialId != null) {
						if (type.equals(AMBIENT_COLOR)) {
							Color ambientColor = new Color(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
									Float.parseFloat(parts[3]), 1f);

							if (material == null) {
								material = new Material(currentMaterialId);
							}

							material.setAmbientColor(ambientColor);
						} else {
							if (type.equals(DIFFUSE_COLOR)) {
								Color diffuseColor = new Color(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
										Float.parseFloat(parts[3]), 1f);

								if (material == null) {
									material = new Material(currentMaterialId);
								}

								material.setDiffuseColor(diffuseColor);
							} else {
								if (type.equals(SPECULAR_COLOR)) {
									Color specularColor = new Color(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
											Float.parseFloat(parts[3]), 1f);

									if (material == null) {
										material = new Material(currentMaterialId);
									}

									material.setSpecularColor(specularColor);
								} else {
									if (type.equals(TRANSPARENCY_D_COLOR)) {
										float transparency = Float.parseFloat(parts[1]);

										if (material == null) {
											material = new Material(currentMaterialId);
										}

										material.setTransparency(transparency);
									} else {
										if (type.equals(TRANSPARENCY_TR_COLOR)) {
											float transparency = Float.parseFloat(parts[1]);

											if (material == null) {
												material = new Material(currentMaterialId);
											}

											material.setTransparency(transparency);
										} else {
											if (type.equals(DIFFUSE_TEX_MAP)) {
												if (parts.length > 1) {
													if (material == null) {
														material = new Material(currentMaterialId);
													}

													String textureFileName = getNormalizedFileName(parts[1]);

													material.setTexture(TextureFactory.getTextureFromAsset(textureFileName));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			//Added last material
			MaterialFactory.addMaterial(currentMaterialId, material);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getNormalizedFileName(String file) {
		int slashIndex = file.lastIndexOf('\\');

		String fileNor = null;

		if (slashIndex == -1)
			slashIndex = file.lastIndexOf('/');

		if (slashIndex != -1) {
			fileNor = file.substring(slashIndex + 1);
		} else {
			fileNor = file;
		}

		return fileNor;
	}
}