(ns clojure-spotify-search.views
  (:use [hiccup core page]))

(defn view-layout [& content]
  (html
      [:head
           [:meta {:http-equiv "Content-type"
                        :content "text/html; charset=utf-8"}]
           [:title "Clojure Spotify Search"]
           (include-css "/css/main.css")]
      [:body content]))

(defn index-page []
  (view-layout
    [:main
      [:section.intro
        [:h1 "Search Spotify"]
        [:input.search-field {:placeholder "Search for a track"}]
        [:button.search "Search"]]
       [:section.tracks]
       [:footer]
       [:script {:src "/js/jquery-1.11.1.min.js"}]
       [:script {:src "/js/cljs.js"}]]))
