(ns yaramp.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "yaramp"]
               [:link {:href "/css/bootstrap.min.css" :rel "stylesheet"
                       :media "screen"}]]
              [:body
               content
               [:script {:src "http://code.jquery.com/jquery-latest.js"}]
               [:script {:src "/js/bootstrap.min.js"}]]))
