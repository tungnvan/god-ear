package com.tungnvan.godear.components;

import android.media.MediaRecorder;

import com.tungnvan.godear.constants.GlobalConstants;
import com.tungnvan.godear.utils.FileUtils;
import com.tungnvan.godear.utils.RecordNameUtils;

import java.util.HashMap;
import java.util.Map;

public class Recorder {

    private MediaRecorder recorder;
    private boolean recording = false;
    private HashMap<String, Runnable> observers = new HashMap<>();
    private String file_path;

    public void subscribe(String key, Runnable runnable) {
        observers.put(key, runnable);
    }

    public void unsubscribe(String key) {
        observers.remove(key);
    }

    public void broadcast() {
        for (Map.Entry<String, Runnable> entry: observers.entrySet()) {
            entry.getValue().run();
        }
    }

    public void setupRecorder() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            FileUtils.createDirectory(GlobalConstants.RECORD_DIRECTORY);
            file_path = RecordNameUtils.produceFilePathFromName(FileUtils.generateFileNameByTime("Sound at "));
            recorder.setOutputFile(file_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRecorder() {
        try {
            recorder.prepare();
            recorder.start();
            recording = true;
            broadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecorder() {
        try {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recording = false;
            broadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRecording() {
        return recording;
    }

    public String getFilePath() {
        return file_path;
    }

}
