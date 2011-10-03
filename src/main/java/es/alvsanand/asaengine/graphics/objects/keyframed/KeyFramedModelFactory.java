package es.alvsanand.asaengine.graphics.objects.keyframed;

import android.util.Log;
import es.alvsanand.asaengine.graphics.objects.MeshFactory;
import es.alvsanand.asaengine.graphics.objects.MeshFactory.MeshType;
import es.alvsanand.asaengine.graphics.objects.error.MeshNotFoundException;
import es.alvsanand.asaengine.graphics.objects.keyframed.animation.Animation;
import es.alvsanand.asaengine.math.Vector3;

public class KeyFramedModelFactory {
	private final static String TAG = "KeyFramedModelFactory";

	public static KeyFramedModel getKeyFramedModelFromAsset(String keyFramedModel, String[] assets, MeshType type, Animation[] animations)
			throws MeshNotFoundException {
		Log.i(TAG, "Loading KeyFramedModel[" + keyFramedModel + "]");

		KeyFrame[] keyFrames = new KeyFrame[assets.length];

		for (int i = 0; i < assets.length; i++) {
			keyFrames[i] = MeshFactory.getKeyFrameFromAsset(assets[i], type, i + 1);
		}

		KeyFramedModel framedModel = new KeyFramedModel(new Vector3(), keyFrames, animations);

		return framedModel;
	}
}
