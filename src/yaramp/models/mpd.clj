(ns ^{:author "Paolo Veglia"}
  yaramp.models.mpd
  (:import [org.bff.javampd.MPD]))

(def default-host "192.168.144.3")
(def default-port 6600)

(defn create-mpd
  ([] (create-mpd default-host default-port))
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
        position (.getPosition song)
        ]
    {:artist artist :album album :length length :title title
     :position position}))

(defn get-current-song [player]
  (if-let [song (.getCurrentSong player)]
    (build-song song)))

(defn get-playlist [mpd]
  (let [list (.getSongList (.getMPDPlaylist mpd))]
    (map build-song list)))

(defn with-player
  ([f] (with-player f "192.168.144.3" 6600))
  ([f host port] (->
                  (create-mpd host port)
                  get-player
                  f)))

(defmacro with-mpd [host port bind & body]
  `(try
     (let [~bind (create-mpd ~host ~port)
           res# ~@body]
         (.close ~bind)
       res#)
     (catch Exception e# (println "uaaa" e#))))
