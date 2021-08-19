package com.hsm.java.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class MediaExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws MalformedURLException {
        File mediaFile = new File("F:\\video\\结婚\\黄森明&陈玲玲.mp4");
        Media media = new Media(mediaFile.toURI().toURL().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);

        MediaView mediaView = new MediaView(mediaPlayer);

        Scene scene = new Scene(new Pane(mediaView), 1024, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.play();

    }
}
