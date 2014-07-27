(ns clojure-spotify-search.cs
    (:require [ajax.core :refer [GET]]
              [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [node sel sel1 deftemplate]]))

(defn album-image [track size]
    ((nth ((track "album")"images") size)"url"))

(deftemplate simple-template [track]
  [:.track.animated.fadeIn
    [:h2 (track "name")]
    [:a {:href ((track "external_urls") "spotify")} "Play"]
    [:img {:src (album-image track 1)} :alt "This is an image"]])

(defn append-track [track]
  (dommy/append! (sel1 :.tracks) (simple-template track)))

(defn list-tracks [tracks]
  ((tracks "tracks") "items"))

(defn loop-tracks [response]
  (dommy/clear! (sel1 :.tracks))
  (dotimes [i (count (list-tracks response))]
    (append-track ((list-tracks response) i))))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn search-spotify [term]
(GET "https://api.spotify.com/v1/search"
  {:params {:q term
            :type "track"
            :limit 10}
    :handler loop-tracks
    :error-handler error-handler}))

(defn start-search [event]
    (search-spotify (dommy/value (sel1 :.search-field))))

(dommy/listen! (sel1 :.search) :click start-search)





(defn log-track [track]
  (js/console.log (track "name")))



