(ns clojure-spotify-search.cs
    (:require [ajax.core :refer [GET]]
              [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [node sel sel1 deftemplate]]))

(defn album-image [track size]
    ((nth ((track "album")"images") size)"url"))

(deftemplate simple-template [track]
  [:.track.animated.fadeIn
    [:span.name (track "name")]
    [:span.artist (track "artist")]
    [:a {:href ((track "external_urls") "spotify")} "Play"]
    [:img {:src (album-image track 1)} :alt "This is an image"]])

(defn track-data [track]
  {:name (track "name")
   :artist ((first( track "artists" )) "name")
   :preview ((track "album") "preview_url")
   :play ((track "external_urls") "spotify")
   :image ((nth ((track "album")"images") 1)"url")})

(defn append-track [track]
  (js/console.log (str (track-data track)))
  (dommy/append! (sel1 :.search-results) (simple-template track)))


(defn list-tracks [tracks]
  ((tracks "tracks") "items"))

(defn loop-tracks [response]
  (dommy/clear! (sel1 :.search-results))
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


(-> js/document (sel1 :.search-field) (.focus))

(defn log-track [track]
  (js/console.log (track "name")))



