(ns yaramp.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "yaramp"]
               #_[:link {:href "/css/bootstrap.min.css" :rel "stylesheet"
                         :media "screen"}]
               [:link {:href "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css"
                       :rel "stylesheet"}]
               ; <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css" rel="stylesheet">
               ]
              [:body
               content
               [:script {:src "http://code.jquery.com/jquery-latest.js"}]
               #_[:script {:src "/js/bootstrap.min.js"}]
               [:script {:src "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/js/bootstrap.min.js"}]
               ; <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/js/bootstrap.min.js"></script>
               ]))
