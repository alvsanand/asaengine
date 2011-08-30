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
package es.alvsanand.asaengine.error;

public class ASAException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7223785533664694248L;

	public ASAException() {
		super();
	}

	public ASAException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASAException(String message) {
		super(message);
	}

	public ASAException(Throwable cause) {
		super(cause);
	}
}