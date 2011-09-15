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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import es.alvsanand.asaengine.graphics.objects.Mesh;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes.Usage;
import es.alvsanand.asaengine.graphics.objects.error.MeshNotFound;

public class ObjLoader {
	private final static String TAG = "ObjLoader";

	public static Mesh loadObj(InputStream in) throws MeshNotFound {
		return loadObj(in, true);
	}

	public static Mesh loadObj(InputStream in, boolean flipV) throws MeshNotFound {
		List<String> lines = new ArrayList<String>();

		Log.i(TAG, "Reading Mesh file");

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String l = reader.readLine();
			while (l != null) {
				lines.add(l);
				l = reader.readLine();
			}

			reader.close();
		} catch (Exception ex) {
			throw new MeshNotFound("The Mesh cannot be loaded", ex);
		}

		Log.i(TAG, "Readed Mesh file");

		return loadObjFromStrings(lines, flipV);
	}

	public static Mesh loadObjFromStrings(List<String> lines) throws MeshNotFound {
		return loadObjFromStrings(lines, true);
	}

	public static Mesh loadObjFromStrings(List<String> lines, boolean flipV) throws MeshNotFound {
		int linesLength = lines.size();

		float[] vertices = new float[linesLength * 3];
		float[] normals = new float[linesLength * 3];
		float[] uv = new float[linesLength * 3];

		int numVertices = 0;
		int numNormals = 0;
		int numUV = 0;
		int numFaces = 0;

		int[] facesVerts = new int[linesLength * 3];
		int[] facesNormals = new int[linesLength * 3];
		int[] facesUV = new int[linesLength * 3];
		int vertexIndex = 0;
		int normalIndex = 0;
		int uvIndex = 0;
		int faceIndex = 0;

		Log.i(TAG, "Parsing Mesh file lines");

		for (int i = 0; i < linesLength; i++) {
			String line = lines.get(i);

			if (line.startsWith("v ")) {
				int index1 = 1;
				int index2 = line.indexOf(' ', index1 + 1);
				int index3 = line.indexOf(' ', index2 + 1);
				int index4 = line.length();

				String token1 = line.substring(index1 + 1, index2);
				String token2 = line.substring(index2 + 1, index3);
				String token3 = line.substring(index3 + 1, index4);

				vertices[vertexIndex] = Float.parseFloat(token1);
				vertices[vertexIndex + 1] = Float.parseFloat(token2);
				vertices[vertexIndex + 2] = Float.parseFloat(token3);
				vertexIndex += 3;
				numVertices++;
				continue;
			}

			if (line.startsWith("vn ")) {
				int index1 = 2;
				int index2 = line.indexOf(' ', index1 + 1);
				int index3 = line.indexOf(' ', index2 + 1);
				int index4 = line.length();

				String token1 = line.substring(index1 + 1, index2);
				String token2 = line.substring(index2 + 1, index3);
				String token3 = line.substring(index3 + 1, index4);

				normals[normalIndex] = Float.parseFloat(token1);
				normals[normalIndex + 1] = Float.parseFloat(token2);
				normals[normalIndex + 2] = Float.parseFloat(token3);
				normalIndex += 3;
				numNormals++;
				continue;
			}

			if (line.startsWith("vt ")) {
				int index1 = 2;
				int index2 = line.indexOf(' ', index1 + 1);
				int index3 = line.length();

				String token1 = line.substring(index1 + 1, index2);
				String token2 = line.substring(index2 + 1, index3);

				uv[uvIndex] = Float.parseFloat(token1);
				uv[uvIndex + 1] = flipV ? 1 - Float.parseFloat(token2) : Float.parseFloat(token2);
				uvIndex += 2;
				numUV++;
				continue;
			}

			if (line.startsWith("f ")) {
				int index1 = -1;
				int index2 = -1;
				int index3 = -1;
				int index4 = -1;
				int index5 = -1;

				String token1 = null;
				String token2 = null;
				String token3 = null;
				String token4 = null;
				
				
				index1 = 1;
				
				index2 = line.indexOf(' ', index1 + 1);
				if(index2==-1){
					token1 = line.substring(index1 + 1);
				}
				else{
					token1 = line.substring(index1 + 1, index2);
					
					index3 = line.indexOf(' ', index2 + 1);					
	
					if (index3 == -1) {
						token2 = line.substring(index2 + 1);
					} else {
						token2 = line.substring(index2 + 1, index3);
						
						index4 = line.indexOf(' ', index3 + 1);						
						
						if (index4 == -1) {
							token3 = line.substring(index3 + 1);
						} else {
							token3 = line.substring(index3 + 1, index4);
							
							index5 = line.indexOf(' ', index4 + 1);						
							
							if (index5 != -1) {
								token4 = line.substring(index4 + 1);
							}
						}
					}
				}

				if(token1!=null){
					index1 = -1;
					index2 = -1;
					index3 = -1;
					index4 = -1;
					index5 = -1;
					
					String part1 = null;					
					String part2 = null;
					String part3 = null;
					
					index1 = 0;
					index2 = token1.indexOf('/');					
					
					if(index2==-1){
						part1 = token1.substring(index1);
					}
					else{
						part1 = token1.substring(index1, index2);
						
						index3 = token1.indexOf('/', index2 + 1);					
		
						if (index3 == -1) {
							part2 = token1.substring(index2 + 1);
						} else {
							part2 = token1.substring(index2 + 1, index3);
							
							index4 = token1.indexOf('/', index3 + 1);						
							
							if (index4 == -1) {
								part3 = token1.substring(index3 + 1);
							}
							else{
								part3 = token1.substring(index3 + 1, index4);								
							}
						}
					}
	
					facesVerts[faceIndex] = getIndex(part1, numVertices);
					if (part3 != null)
						facesNormals[faceIndex] = getIndex(part3, numNormals);
					if (part2 != null)
						facesUV[faceIndex] = getIndex(part2, numUV);
					faceIndex++;
				}
				
				if(token2!=null){
					index1 = -1;
					index2 = -1;
					index3 = -1;
					index4 = -1;
					
					String part1 = null;					
					String part2 = null;
					String part3 = null;
					
					index1 = 0;
					index2 = token2.indexOf('/');					
					
					if(index2==-1){
						part1 = token2.substring(index1);
					}
					else{
						part1 = token2.substring(index1, index2);
						
						index3 = token2.indexOf('/', index2 + 1);					
		
						if (index3 == -1) {
							part2 = token2.substring(index2 + 1);
						} else {
							part2 = token2.substring(index2 + 1, index3);
							
							index4 = token2.indexOf('/', index3 + 1);						
							
							if (index4 == -1) {
								part3 = token2.substring(index3 + 1);
							}
							else{
								part3 = token2.substring(index3 + 1, index4);								
							}
						}
					}
	
					facesVerts[faceIndex] = getIndex(part1, numVertices);
					if (part3 != null)
						facesNormals[faceIndex] = getIndex(part3, numNormals);
					if (part2 != null)
						facesUV[faceIndex] = getIndex(part2, numUV);
					
					faceIndex++;
				}

				if(token3!=null){				
					index1 = -1;
					index2 = -1;
					index3 = -1;
					index4 = -1;
					
					String part1 = null;					
					String part2 = null;
					String part3 = null;
					
					index1 = 0;
					index2 = token3.indexOf('/');					
					
					if(index2==-1){
						part1 = token3.substring(index1);
					}
					else{
						part1 = token3.substring(index1, index2);
						
						index3 = token3.indexOf('/', index2 + 1);					
		
						if (index3 == -1) {
							part2 = token3.substring(index2 + 1);
						} else {
							part2 = token3.substring(index2 + 1, index3);
							
							index4 = token3.indexOf('/', index3 + 1);						
							
							if (index4 == -1) {
								part3 = token3.substring(index3 + 1);
							}
							else{
								part3 = token3.substring(index3 + 1, index4);								
							}
						}
					}
	
					facesVerts[faceIndex] = getIndex(part1, numVertices);
					if (part3 != null)
						facesNormals[faceIndex] = getIndex(part3, numNormals);
					if (part2 != null)
						facesUV[faceIndex] = getIndex(part2, numUV);
					
					faceIndex++;
				}

				if(token4!=null){				
					index1 = -1;
					index2 = -1;
					index3 = -1;
					index4 = -1;
					
					String part1 = null;					
					String part2 = null;
					String part3 = null;
					
					index1 = 0;
					index2 = token4.indexOf('/');					
					
					if(index2==-1){
						part1 = token4.substring(index1 + 1);
					}
					else{
						part1 = token4.substring(index1 + 1, index2);
						
						index3 = token4.indexOf('/', index2 + 1);					
		
						if (index3 == -1) {
							part2 = token4.substring(index2 + 1);
						} else {
							part2 = token4.substring(index2 + 1, index3);
							
							index4 = token4.indexOf('/', index3 + 1);						
							
							if (index4 != -1) {
								part3 = token4.substring(index3 + 1);
							}
						}
					}
	
					facesVerts[faceIndex] = getIndex(part1, numVertices);
					if (part3 != null)
						facesNormals[faceIndex] = getIndex(part3, numNormals);
					if (part2 != null)
						facesUV[faceIndex] = getIndex(part2, numUV);
					
					faceIndex++;
				}
				
				numFaces++;
				continue;
			}
		}

		Log.i(TAG, "Parsed Mesh file lines");

		Log.i(TAG, "Creating Mesh vertices array");

		float[] verts = new float[(numFaces * 3) * (3 + (numNormals > 0 ? 3 : 0) + (numUV > 0 ? 2 : 0))];

		for (int i = 0, vi = 0; i < numFaces * 3; i++) {
			int vertexIdx = facesVerts[i] * 3;
			verts[vi++] = vertices[vertexIdx];
			verts[vi++] = vertices[vertexIdx + 1];
			verts[vi++] = vertices[vertexIdx + 2];

			if (numUV > 0) {
				int uvIdx = facesUV[i] * 2;
				verts[vi++] = uv[uvIdx];
				verts[vi++] = uv[uvIdx + 1];
			}
			if (numNormals > 0) {
				int normalIdx = facesNormals[i] * 3;
				verts[vi++] = normals[normalIdx];
				verts[vi++] = normals[normalIdx + 1];
				verts[vi++] = normals[normalIdx + 2];
			}
		}

		Log.i(TAG, "Created Mesh vertices array");

		Mesh mesh = null;

		ArrayList<VertexAttribute> attributes = new ArrayList<VertexAttribute>();
		attributes.add(new VertexAttribute(Usage.Position, 3, "a_Position"));
		if (numUV > 0)
			attributes.add(new VertexAttribute(Usage.TextureCoordinates, 2, "a_TexCoord"));
		if (numNormals > 0)
			attributes.add(new VertexAttribute(Usage.Normal, 3, "a_Normal"));

		Log.i(TAG, "Creating Mesh");

		mesh = new Mesh(true, numFaces * 3, 0, attributes.toArray(new VertexAttribute[attributes.size()]));
		mesh.setVertices(verts);

		Log.i(TAG, "Created Mesh");

		return mesh;
	}

	private static int getIndex(String index, int size) {
		if (index == null || index.length() == 0)
			return 0;
		int idx = Integer.parseInt(index);
		if (idx < 0)
			return size + idx;
		else
			return idx - 1;
	}
}