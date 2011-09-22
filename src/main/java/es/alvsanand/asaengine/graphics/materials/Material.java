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
package es.alvsanand.asaengine.graphics.materials;

import es.alvsanand.asaengine.graphics.color.Color;

public class Material {
	protected String materialId;
	protected Texture texture;
	protected Color diffuseColor;
	protected Color ambientColor;
	protected Color specularColor;
	protected float transparency;

	public Material(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Color getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Color diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Color getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Color specularColor) {
		this.specularColor = specularColor;
	}

	public float getTransparency() {
		return transparency;
	}

	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	@Override
	public String toString() {
		return "Material [materialId=" + materialId + ", texture=" + texture + ", diffuseColor=" + diffuseColor + ", ambientColor=" + ambientColor
				+ ", specularColor=" + specularColor + ", transparency=" + transparency + "]";
	}
}
