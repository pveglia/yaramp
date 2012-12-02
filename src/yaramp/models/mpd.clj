(ns ^{:author "Paolo Veglia"}
  yaramp.models.mpd
  (:import [org.bff.javampd.MPD]))

(def default-connection '("192.168.144.3" 6600))

(defn create-mpd
  ([] (apply create-mpd default-connection))
  ([host port] (org.bff.javampd.MPD. host port)))

(defn get-player [mpd]
  (.getMPDPlayer mpd))

(defn volup [player]
  (let [volume (+ 2 (.getVolume player))]
    (.setVolume player volume)
    volume))

(defn voldown [player]
  (let [volume (- (.getVolume player) 2)]
    (.setVolume player volume)
    volume))

(defn build-song [song]
  (let [artist (-> (.getArtist song) .toString)
        album (-> (.getAlbum song) .toString)
        length (.getLength song)
        title (.getTitle song)
        ]
    {:artist artist :album album :length length :title title}))

(defn get-playlist [mpd]
  (let [list (.getSongList (.getMPDPlaylist mpd))]
    (println list)
    (map build-song list)))

(defn with-player
  ([f] (with-player f "192.168.144.3" 6600))
  ([f host port] (->
                  (create-mpd host port)
                  get-player
                  f)))

(defmacro with-player' [bind & body]
  `(let [~bind (get-player (create-mpd "192.168.144.3" 6600))]
     ~@body))