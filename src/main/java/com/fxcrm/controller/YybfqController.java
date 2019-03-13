package com.fxcrm.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class YybfqController {
    //音乐集
    private static List<String> list = new ArrayList<>();
    private static String dqbfgq = null;
    private static int dqbfindex = 0;
    private Media media1 = null;//new Media(dqbfgq);
    private MediaPlayer mp1 = null;//new MediaPlayer(media1);

    @FXML
    private TextField musicPath;

    @FXML
    private VBox gd;

    @FXML
    private Label dqbf;

    @FXML
    private HBox mus;

    @FXML
    private Label vol;

    @FXML
    private HBox pp;

    @FXML
    private Label musjd;

    public void init() {
        musicPath.setText("D:\\yy");
    }

    @FXML
    private void getData() {
        gd.getChildren().clear();
        Label label = new Label("正在生成歌单...");
        label.setStyle("-fx-text-fill: black;");
        gd.getChildren().add(label);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                getMusics();
                return null;
            }
        };
        new Thread(task).start();
    }

    private void getMusics() {
        list.clear();
        String text = musicPath.getText();
        if (!text.trim().isEmpty()) {
            File file = new File(text.trim());
            File[] tempList = file.listFiles();
            if (tempList != null && tempList.length > 0) {
                for (int i = 0; i < tempList.length; i++) {
                    String name = tempList[i].getName();
                    if (name.contains("mp3")) {
                        list.add(name);
                    }
                }
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gd.getChildren().clear();
                        Label label = new Label("未能成功生成歌单");
                        label.setStyle("-fx-text-fill: black;");
                        gd.getChildren().add(label);
                    }
                });
            }
        }
        List<HBox> hBoxList = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(k -> {
                HBox hBox = new HBox();
                hBox.setPrefWidth(500);
                hBox.setPrefHeight(30);
                Label label = new Label(k);
                label.setPrefHeight(30);
                label.setStyle("-fx-text-fill: red;");
                hBox.getChildren().add(label);
                hBoxList.add(hBox);
            });
            if (hBoxList.size() > 0) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gd.getChildren().clear();
                        gd.getChildren().addAll(hBoxList);
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gd.getChildren().clear();
                        Label label = new Label("未能成功生成歌单");
                        label.setStyle("-fx-text-fill: black;");
                        gd.getChildren().add(label);
                    }
                });
            }
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gd.getChildren().clear();
                    Label label = new Label("未找到.mp3歌曲");
                    label.setStyle("-fx-text-fill: black;");
                    gd.getChildren().add(label);
                }
            });
        }
    }

    @FXML
    private void play() {
//        if (mp1 != null) {
//            if (mp1.getStatus().equals(MediaPlayer.Status.PLAYING)) {
//                mp1.pause();
//            } else {
//                mp1.play();
//            }
//        }
        musjd.setPrefWidth(0);
        if (list.size() > 0) {
            dqbfindex = dqbfindex >= 0 ? dqbfindex : 0;
            dqbfgq = list.get(dqbfindex);
            try {
                URL url = new URL(new File("file://" + musicPath.getText() + "\\" + dqbfgq).getPath());
                media1 = new Media(url.toString());
                mp1 = new MediaPlayer(media1);
                dqbf.setText(dqbfgq);
                mp1.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
//                        循环
//                        mp1.seek(Duration.ZERO);
//                        自动下一首
                        dow();
                    }
                });
                vol.setText(mp1.getVolume() + "");
                mp1.play();
                mp1.setAudioSpectrumListener(new AudioSpectrumListener() {
                    @Override
                    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                Duration totalDuration = mp1.getTotalDuration();
                                double d = totalDuration.toSeconds();
//                                DecimalFormat df = new DecimalFormat("#.00");
                                double v = timestamp / d;
                                DecimalFormat df = new DecimalFormat("#.00");
                                DecimalFormat df2 = new DecimalFormat("#.00");
                                double v1 = Double.parseDouble(df.format(v));
                                double v2 = 1000 * v1;
//                                频谱
                                pinpu(phases, v2);
                                return null;
                            }
                        };
                        new Thread(task).start();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void pinpu(float[] phases, double v2) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(1000);
        hBox.setPrefHeight(1280);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        for (int j = 0; j < phases.length; j++) {
            Label label = new Label();
            label.setPrefWidth(100);
            label.setPrefHeight(phases[j] * 300);
            label.setStyle("-fx-background-color: #3393FF;-fx-border-color: #2F7FDA;");
            hBox.getChildren().add(label);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pp.getChildren().clear();
                pp.getChildren().add(hBox);
                musjd.setPrefWidth(v2);
            }
        });
    }

    @FXML
    private void pause() {
//        mp1.pause();
        if (mp1.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mp1.pause();
        } else {
            mp1.play();
        }
    }

    @FXML
    private void stops() {
        mp1.stop();
    }

    @FXML
    private void dow() {
        mp1.stop();
        dqbfindex += 1;
        if (dqbfindex >= list.size()) {
            dqbfindex = 0;
        }
        mp1.dispose();
        play();
    }

    @FXML
    private void up() {
        mp1.stop();
        dqbfindex -= 1;
        if (dqbfindex < 0) {
            dqbfindex = list.size() - 1;
        }
        play();
    }

    //    增大音量
    @FXML
    private void volUp() {
        DecimalFormat df2 = new DecimalFormat("#");
        if (mp1 != null) {
            mp1.setVolume(mp1.getVolume() + 0.1);
            vol.setText(df2.format(mp1.getVolume()));
            if (mp1.getVolume() > 1) {
                mp1.setVolume(1);
                vol.setText(df2.format(mp1.getVolume()));
            }
        }
    }

    //    减少音量
    @FXML
    private void volDow() {
        DecimalFormat df2 = new DecimalFormat("#");
        if (mp1 != null) {
            mp1.setVolume(mp1.getVolume() - 0.1);
            vol.setText(df2.format(mp1.getVolume()));
            if (mp1.getVolume() < 0) {
                mp1.setVolume(0);
                vol.setText(df2.format(mp1.getVolume()));
            }
        }
    }
}
