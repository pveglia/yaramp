(ns ^{:author "Paolo Veglia"}
  yaramp.models.mpd
  (:import [org.bff.javampd.MPD])
  (:require clojure.string))

(def default-host "192.168.144.3")
;(def default-host "127.0.0.1")
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

(defn play [player]
  (println "called play")
  (.play player))

(defn pause [player]
  (println "called pause")
  (.pause player))

(defn stop [player]
  (.stop player))

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
     (catch Exception e# (println "Exception caught by me" e#))))

(defn listArtists [fltr]
  (with-mpd default-host default-port mpd
    (let [db (.getMPDDatabase mpd)
          all (.listAllArtists db)
          filtered (filter #(re-find (re-pattern (str "(?i)" fltr)) (.toString %))
                           all)]
      (println filtered))))

(defn listAlbumsByArtist [artist]
  (with-mpd default-host default-port mpd
    (let [db (.getMPDDatabase mpd)
          artistObj (org.bff.javampd.objects.MPDArtist. artist)]
      (map #(hash-map :name (.getName %) :artist (.getArtist %))
           (.listAlbumsByArtist db artistObj)))))

(defn songsInAlbum [album]
  (with-mpd default-host default-port mpd
    (let [db (.getMPDDatabase mpd)
          albumObj (org.bff.javampd.objects.MPDAlbum. album)]
      (map build-song (.searchAlbum db albumObj)))))

(defn addAlbum
  ([albumname] (addAlbum albumname false))
  ([albumname clear]
     (with-mpd default-host default-port mpd
       (let [db (.getMPDDatabase mpd)
             pl (.getMPDPlaylist mpd)
             alb (org.bff.javampd.objects.MPDAlbum. albumname)]
         (when clear
           (clearPlaylist. pl))
         (insertAlbum. pl alb)))))
