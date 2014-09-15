package com.yowenlove.mobile.glassm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.yowenlove.mobile.glassm.ar.graphics.LightingRenderer;
import com.yowenlove.mobile.glassm.ar.graphics.Model3D;
import com.yowenlove.mobile.glassm.ar.models.Model;
import com.yowenlove.mobile.glassm.ar.parser.ObjParser;
import com.yowenlove.mobile.glassm.ar.parser.ParseException;
import com.yowenlove.mobile.glassm.ar.util.AssetsFileUtil;
import com.yowenlove.mobile.glassm.ar.util.BaseFileUtil;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;

import java.io.BufferedReader;
import java.io.IOException;


public class ARPreviewActivity extends AndARActivity {

    private Model model;
    private Model3D model3d;
    private ARToolkit artoolkit;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        super.setNonARRenderer(new CustomRenderer());//optional, may be set to null
        super.setNonARRenderer(new LightingRenderer());//or might be omited

        //register a object for each marker type
        artoolkit = super.getArtoolkit();
        new ModelLoader().execute("rose");
    }

    /**
     * Inform the user about exceptions that occurred in background threads.
     * This exception is rather severe and can not be recovered from.
     * TODO Inform the user and shut down the application.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }


    private class ModelLoader extends AsyncTask<String, Object, Void> {

        @Override
        protected Void doInBackground(String... params) {
            BaseFileUtil fileUtil = new AssetsFileUtil(getResources().getAssets());
            fileUtil.setBaseFolder("models/");

            //read the model file:
            ObjParser parser = new ObjParser(fileUtil);
            try {
                if (fileUtil != null) {
                    BufferedReader fileReader = fileUtil.getReaderFromName(params[0] + ".obj");
                    if (fileReader != null) {
                        model = parser.parse("Model", fileReader);
                        model.setScale(10);
                        model3d = new Model3D(model);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //register model
            try {
                if (model3d != null)
                    artoolkit.registerARObject(model3d);

            } catch (AndARException e) {
                e.printStackTrace();
            }
            startPreview();
        }
    }

}
