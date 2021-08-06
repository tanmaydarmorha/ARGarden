package com.hackySprinters.argardening;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArFragment arFragment;
    private ModelRenderable plant1Renderable, plant2Renderable;
    TextView plant1, plant2;

    View arrayView[];
    ViewRenderable name_plant;
    int selected = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        plant1 = findViewById(R.id.plant_1);
        plant2 = findViewById(R.id.plant_2);

        setArrayView();
        setClickListener();

        setupModel();
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (selected >= 1) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                createModel(anchorNode, selected);
            }
        });
    }

    private void setupModel() {
        ModelRenderable.builder().
                setSource(this, R.raw.flower_pot)
                .build()
                .thenAccept(renderable -> plant1Renderable = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load plant model", Toast.LENGTH_SHORT).show();
                    return null;
                });

        ModelRenderable.builder().
                setSource(this, R.raw.plant_2)
                .build()
                .thenAccept(renderable -> plant2Renderable = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load plant model", Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected == 1) {
            TransformableNode plant1Node = new TransformableNode(arFragment.getTransformationSystem());
            plant1Node.setParent(anchorNode);
            plant1Node.setRenderable(plant1Renderable);
            plant1Node.select();
        }

        if (selected == 2) {
            TransformableNode plant1Node = new TransformableNode(arFragment.getTransformationSystem());
            plant1Node.setParent(anchorNode);
            plant1Node.setRenderable(plant2Renderable);
            plant1Node.select();
        }
    }

    private void setClickListener() {
        for (View view : arrayView) {
            view.setOnClickListener(this);
        }
    }

    private void setArrayView() {
        arrayView = new View[]{
                plant1, plant2
        };
    }

    @Override
    public void onClick(View view) {

    }
}
