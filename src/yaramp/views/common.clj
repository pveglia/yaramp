(ns yaramp.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "yaramp"]
               [:link {:href "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css"
                       :rel "stylesheet"}]
               (include-css "/css/yaramp.css")
               ]
              [:body
               [:span#msg_ok {:class "label label-success"} ""]
               [:span#msg_ko {:class "label label-warning"} ""]
               [:div.container
               content
                ]
               [:script {:src "http://code.jquery.com/jquery-latest.js"}]
               [:script {:src "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/js/bootstrap.min.js"}]
               (include-js "/js/yaramp.js")
               ]))
