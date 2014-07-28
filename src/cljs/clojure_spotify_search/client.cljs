(ns clojure-spotify-search.cs
    (:require [clojure-spotify-search.templates :as t]
              [ajax.core :refer [GET]]
              [dommy.utils :as utils]
              [dommy.core :as dommy])
    (:use-macros [dommy.macros :only [sel sel1]]))

(defn album-image [track size]
    ((nth ((track "album")"images") size)"url"))

(defn format-track [track]
  {:name (track "name")
   :artist ((first( track "artists" )) "name")
   :preview (track "preview_url")
   :play ((track "external_urls") "spotify")
   :image ((nth ((track "album")"images") 1)"url")})

(defn append-track [track]
  (dommy/append! (sel1 :.search-results) (t/track-template (format-track track))))

(defn list-tracks [tracks]
  ((tracks "tracks") "items"))

(defn loop-tracks [response]
  (dommy/clear! (sel1 :.search-results))
  (dotimes [i (count (list-tracks response))]
    (append-track ((list-tracks response) i))))

(defn error-handler [{:keys [status status-text]}]
  (dommy/clear! (sel1 :.search-results))
  (dommy/append! (sel1 :.search-results) (t/alert-error)))

(defn search-spotify [term]
(GET "https://api.spotify.com/v1/search"
  {:params {:q term
            :type "track"
            :limit 10}
    :handler loop-tracks
    :error-handler error-handler}))

(defn start-search []
  (if (> (count (dommy/value (sel1 :.search-field))) 0 )
    (search-spotify (dommy/value (sel1 :.search-field)))
    (error-handler [])))

(defn check-key [event]
  (if (== (.-which event) 13 )
    (start-search)))

(defn add-class [event]
    (dommy/add-class! (.-selectedTarget event) "active"))

(defn remove-class [event]
    (dommy/remove-class! (.-selectedTarget event) "active"))

(dommy/listen! [(sel1 :.search-results) :.track] "mouseover" add-class "mouseout" remove-class)
(dommy/listen! (sel1 :.search) :click start-search)
(dommy/listen! (sel1 :.search-field) :keyup check-key)
(-> js/document (sel1 :.search-field) (.focus))




