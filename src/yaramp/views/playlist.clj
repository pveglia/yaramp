(ns yaramp.views.playlist
  (:require [yaramp.views.common :as common]
            [yaramp.models.mpd :as mpd])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defn generate-playlist []
  (mpd/with-mpd mpd/default-host mpd/default-port mpd
    (let [player (mpd/get-player mpd)
          songs (mpd/get-playlist mpd)
          current (mpd/get-current-song player)
          ]
      (map #(list [:tr
                   [:td (when (and current (= current %)) "X")]
                   [:td (:position %)]
                   [:td (:title %)]
                   [:td (:artist %)]
                   [:td (:album %)]
                   [:td (format "%d:%d" (quot (:length %) 60)
                                (rem (:length %) 60))]])
           songs))))

(defn generate-controller []
  [:table
   [:tr
    [:td [:a#cmd_play {:href "#"} [:img {:src "/img/play.png"}]]]
    [:td [:a#cmd_pause {:href "#"} [:img {:src "/img/pause.png"}]]]
    [:td [:a#cmd_stop {:href "#"} [:img {:src "/img/stop.png"}]]]
    [:td [:a#cmd_voldown {:href "#"} [:img {:src "/img/voldown.png"}]]]
    [:td [:a#cmd_mute {:href "#"} [:img {:src "/img/mute.png"}]]]
    [:td [:a#cmd_volup {:href "#"} [:img {:src "/img/volup.png"}]]]
    ]])

(defpage "/playlist" []
  (common/layout
   [:div.container
    [:h1 "Current Playlist"]
    [:div.row
     [:div.span5]
     [:div.span2 {:id "controller"}
      (generate-controller)]
     [:div.span5]]
    [:div.row
     [:div.span12 {:id "playlist"}
      [:table.table
       [:tr
        [:th] [:th "Position"] [:th "Title"] [:th "Artist"] [:th "Album"]
        [:th "Length"]]
       (generate-playlist)]]]]))

(defpage "/" []
  (common/layout
   [:div.container
    [:h1 "YARAMP"]]))