(ns clojure-spotify-search.server
  (:require
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response])
  (:use [hiccup.core]
        [compojure.core]))

(defn view-layout [& content]
  (html
      [:head
           [:meta {:http-equiv "Content-type"
                        :content "text/html; charset=utf-8"}]
           [:title "Clojure Spotify Search"]]
      [:body content]))

(defn view-content []
  (view-layout
       [:h2 "Search Spotify"]
       [:p {:id "clickhere"} "click for console.log"]
       [:a {:id "google-link" :href "http://google.com"} "A link to google"]
       [:script {:src "/js/jquery-1.11.1.min.js"}]
       [:script {:src "/js/cljs.js"}]))


(defroutes main-routes
  (GET "/" []
      (view-content))
      (route/resources "/"))

(def app (handler/site main-routes))
