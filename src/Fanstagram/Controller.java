package Fanstagram;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
//import java.awt.Desktop;

//import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    public Label timer;
    public Button reset;
    public WebView webViewSongTitle = new WebView();
    public WebView webViewArtist;

    public Button wvStart;

    public Button btYouTubeArtist;
    public Button btFacebookArtist;
    public Button btTwitterArtist;

    public Button btYouTubeSongTitle;
    public Button btFacebookSongTitle;
    public Button btTwitterSongTitle;


    @FXML
    public ComboBox<SearchType> cbSearchType;
    public ObservableList<SearchType> comboItems;

    @FXML
    public ListView<String> songListView;
    public ListView<String> artistListView;


    ObservableList list_melon = FXCollections.observableArrayList();
    ObservableList list_atrist = FXCollections.observableArrayList();
    ObservableList list_time = FXCollections.observableArrayList();

    public List<String> list = new ArrayList(); //곡 노래
    public List<String> list2 = new ArrayList<>();  //가수
    public List<String> list3 = new ArrayList<>();
    public String selectSong;
    public String selectArtist;

    @FXML
    public void onClick(ActionEvent event) {

    }





    @FXML
    public void initialize() {


        SongDataCrawling();
        list_melon.clear();
        list_atrist.clear();
//        System.out.println("clear");
//        System.out.println(list_melon);
        list_melon.addAll(list);
//        System.out.println("add");
//        System.out.println(list_melon);
        list_atrist.addAll(list2);

        songListView.setItems(list_melon);  //add -> setItems
        artistListView.setItems(list_atrist);


        comboItems = FXCollections.observableArrayList(); //comboBox
        comboItems.add(new SearchType("SongTitle", "wvSongTitle"));
        comboItems.add(new SearchType("Artist", "wvArtist"));
//        comboItems.add(new SearchType("Facebook","wvFaceBook"));
        cbSearchType.setItems(comboItems);
//        cbSearchType.setValue(comboItems);
        System.out.println(cbSearchType.getItems());

        handleSearchAction();


        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    handleResetAction(actionEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });






            songListView.setOnMouseClicked((MouseEvent) -> { //list 선택시 해당 text 받아오기
                //public String selectSong;
                selectSong = songListView.getSelectionModel().getSelectedItem();
                System.out.println(selectSong);


            });


            artistListView.setOnMouseClicked((MouseEvent) -> {
                //public String selectArtist;
                selectArtist = artistListView.getSelectionModel().getSelectedItem();

                System.out.println(selectArtist);


            });



//            btYouTubeSongTitle.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    handleWebViewSongTitleYoutube();
//                }
//            });




            btYouTubeSongTitle.setOnAction(event -> handleWebViewSongTitleYoutube());
            btFacebookSongTitle.setOnAction(event -> handleWebViewSongTitleInsta());
            btTwitterSongTitle.setOnAction(event -> handleWebViewSongTitleTwitter());

            btYouTubeArtist.setOnAction(event -> handleWebViewArtistYoutube());
            btFacebookArtist.setOnAction(event -> handleWebViewArtistInsta());
            btTwitterArtist.setOnAction(event -> handleWebViewArtistTwitter());

//            wvStart.setOnAction(event -> wvStartClick());



    }






    @FXML
    public void handleSearchAction () { //콤보박스에서 선택된 걸로 웹뷰 뜸
        SearchType item = cbSearchType.getSelectionModel().getSelectedItem();
        if (item == null) return;
        String type = item.getValue();
        System.out.println(type);

        if ("wvSongTitle".equals(type)) {
            webViewArtist.setVisible(false);
            btYouTubeArtist.setVisible(false);
            btFacebookArtist.setVisible(false);
            btTwitterArtist.setVisible(false);

            webViewSongTitle.setVisible(true);
            btYouTubeSongTitle.setVisible(true);
            btFacebookSongTitle.setVisible(true);
            btTwitterSongTitle.setVisible(true);
            System.out.println("SongTitleWebViewStart");

        } else if ("wvArtist".equals(type)) {
            webViewSongTitle.setVisible(false);
            btYouTubeSongTitle.setVisible(false);
            btFacebookSongTitle.setVisible(false);
            btTwitterSongTitle.setVisible(false);
//            webViewArtist.setVisible(false);

            webViewSongTitle.setVisible(true);
            btYouTubeArtist.setVisible(true);
            btFacebookArtist.setVisible(true);
            btTwitterArtist.setVisible(true);

            System.out.println("ArtistWebViewStart");
        }
    }



    @FXML
    public void handleWebViewSongTitleYoutube(){
        WebEngine webEngineSongTitle = webViewSongTitle.getEngine();
        webEngineSongTitle.load("https://www.youtube.com/results?search_query="+selectSong);
//        System.out.println(selectSong);
        System.out.println("wvYoutubeSongTitle");
    }


    @FXML
    public void handleWebViewSongTitleInsta(){
        WebEngine webEngineSongTitle = webViewSongTitle.getEngine();
        webEngineSongTitle.load("https://www.instagram.com/explore/tags/"+selectSong+"/");
        System.out.println("wvInstagramSongTitle");
    }

    @FXML
    public void handleWebViewSongTitleTwitter(){
        WebEngine webEngineSongTitle = webViewSongTitle.getEngine();
        webEngineSongTitle.load("https://twitter.com/search?q="+selectSong);
        System.out.println("wvTwitterSongtitle");
    }



    //=================================================================================================



    @FXML
    public void handleWebViewArtistYoutube(){
        WebEngine webEngineArtist = webViewArtist.getEngine();
        webEngineArtist.load("https://www.youtube.com/results?search_query="+selectArtist);
        System.out.println("wvYoutubeArtist");
    }

    @FXML
    public void handleWebViewArtistInsta(){
        WebEngine webEngineSongTitle = webViewSongTitle.getEngine();
        webEngineSongTitle.load("https://www.instagram.com/explore/tags/"+selectArtist+"/");
        System.out.println("wvInstagramArtist");
    }

    @FXML
    public void handleWebViewArtistTwitter(){
        WebEngine webEngineSongTitle = webViewSongTitle.getEngine();
        webEngineSongTitle.load("https://twitter.com/search?q="+selectArtist);
        System.out.println("wvTwitterArtist");
    }







    private void handleResetAction(ActionEvent actionEvent) throws Exception {
        String str = list3.toString();
        timer.setText(str);
        list = new ArrayList<>();
        initialize();
    }



//============================================================================================
//
//
//    public void wvStartClick(){
//        System.out.println("startWvStartClick");
//        String addr = "https://www.melon.com/chart/index.htm";
//        Process pro = null;
//        String[] cmd = new String[]{"rundll32", "url.dll", "FileProtocolHandler", addr};
//        String str = null;
//        try {
//            pro = new ProcessBuilder(cmd).start();
////            System.out.println(process);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//



//============================================================================================





    public void SongDataCrawling() {

        try

        {
            Document doc = Jsoup.connect("https://www.melon.com/chart/index.htm").get();

            //System.out.println(doc);


            Elements songName = doc.select("[id=frm]").select("[id=lst50]").select("td").select("[class=ellipsis rank01]").select("span").select("a");
            Elements artist = doc.select("[id=frm]").select("[id=lst50]").select("td").select("[class=ellipsis rank02]").select("span").select("a");
            Elements calender = doc.select("[class=multi_row]").select("[class=hhmm]").select("span");

            list = songName.eachText();   //노래 제목
            list2 = artist.eachText();    //아티스트 이름
            list3 = calender.eachText();  //새로고침 한 시각


        } catch(Exception e)

        {
            e.printStackTrace();
        }

    }


}

