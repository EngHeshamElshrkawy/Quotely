package com.example.quotely.utils;

import android.content.Context;
import android.util.Log;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PredictQuoteTopic {

    private Context context;
    private BertNLClassifier.BertNLClassifierOptions options;
    private BertNLClassifier classifier;
    public PredictQuoteTopic(Context context){
        this.context = context;
        options =
                BertNLClassifier.BertNLClassifierOptions.builder()
                        .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
                        .build();
        try {
            classifier =
                    BertNLClassifier.createFromFileAndOptions(context, "quotes_model.tflite", options);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getQuoteTopic(String quote){
            // Run inference
            List<Category> results = classifier.classify(quote);
            float maxScore = 0;
            String topic = null;
            for(int i = 0; i < results.toArray().length; i++){
                if(results.get(i).getScore() > maxScore){
                    topic = results.get(i).getLabel();
                    maxScore = results.get(i).getScore();
                }
            }
            return topic;
    }

}
